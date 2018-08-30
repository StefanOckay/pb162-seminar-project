package cz.muni.fi.pb162.project.geometry;

import cz.muni.fi.pb162.project.exceptions.EmptyDrawableException;
import cz.muni.fi.pb162.project.exceptions.MissingVerticesException;
import cz.muni.fi.pb162.project.exceptions.TransparentColorException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Stefan Ockay
 */

public class Paper implements Drawable, PolygonFactory {
    private ArrayList<ColoredPolygon> polygons;
    private Color currentColor = Color.BLACK;

    public Paper() {
        polygons = new ArrayList<>();
    }

    public Paper(Paper paper) {
        polygons = new ArrayList<>(paper.getAllDrawnPolygons());
    }

    @Override
    public void changeColor(Color color) {
        if (color == null) {
            return;
        }
        currentColor = color;
    }

    @Override
    public void drawPolygon(Polygon polygon) throws TransparentColorException {
        if (currentColor == Color.WHITE) {
            String s = String.format("Cannot draw %s polygon on a paper.", currentColor.toString());
            throw new TransparentColorException(s);
        }
        for (ColoredPolygon coloredPolygon: polygons) {
            if (coloredPolygon.getPolygon().equals(polygon)) {
                return;
            }
        }
        polygons.add(new ColoredPolygon(polygon, currentColor));
    }

    @Override
    public void erasePolygon(ColoredPolygon polygon) {
        polygons.remove(polygon);
    }

    @Override
    public void eraseAll() throws EmptyDrawableException {
        if (polygons.isEmpty()) {
            throw new EmptyDrawableException("The paper is blank. Nothing to erase.");
        }
        polygons.clear();
    }

    @Override
    public Collection<ColoredPolygon> getAllDrawnPolygons() {
        return Collections.unmodifiableList(polygons);
    }

    @Override
    public Collection<ColoredPolygon> findPolygonsWithVertex(Vertex2D vertex) {
        ArrayList<Vertex2D> polygonVertices;
        ArrayList<ColoredPolygon> withVertex = new ArrayList<>();
        for (ColoredPolygon coloredPolygon: polygons) {
            polygonVertices = new ArrayList<>(coloredPolygon.getPolygon().getVertices());
            if (polygonVertices.contains(vertex)) {
                withVertex.add(coloredPolygon);
            }
        }
        return withVertex;
    }

    @Override
    public Polygon tryToCreatePolygon(List<Vertex2D> vertices) throws MissingVerticesException {
        if (vertices == null) {
            throw new NullPointerException();
        }
        if (vertices.isEmpty() || vertices.size() < 3) {
            throw new MissingVerticesException("At least 3 vertices needed.");
        }
        LinkedList<Vertex2D> dynVertices = new LinkedList<>(vertices);
        Polygon polygon = null;
        Exception exception = null;
        while (polygon == null) {
            try {
                if (dynVertices.size() < 3) {
                    throw new MissingVerticesException("At least 3 vertices needed.", exception);
                }
                polygon = new CollectionPolygon(dynVertices.toArray(new Vertex2D[0]));
            } catch (IllegalArgumentException ex) {
                exception = ex;
                dynVertices.remove(null);
            }
        }
        return polygon;
    }

    @Override
    public void tryToDrawPolygons(List<List<Vertex2D>> collectionPolygons) throws EmptyDrawableException {
        Polygon polygon;
        Exception exception = null;
        boolean drawn = false;
        for (List<Vertex2D> vertices: collectionPolygons) {
            try {
                polygon = tryToCreatePolygon(vertices);
                drawPolygon(polygon);
                drawn = true;
            } catch (NullPointerException | MissingVerticesException | TransparentColorException ex) {
                exception = ex;
            }
        }
        if (!drawn) {
            throw new EmptyDrawableException("No polygons drawn.", exception);
        }
    }

    public Collection<Polygon> getPolygonsWithColor(Color color) {
        ArrayList<Polygon> result = new ArrayList<>();
        for (ColoredPolygon coloredPolygon: polygons) {
            if (coloredPolygon.getColor() == color) {
                result.add(coloredPolygon.getPolygon());
            }
        }
        return Collections.unmodifiableList(result);
    }
}
