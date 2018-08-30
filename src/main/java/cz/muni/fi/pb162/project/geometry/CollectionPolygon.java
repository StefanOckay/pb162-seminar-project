package cz.muni.fi.pb162.project.geometry;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

import static cz.muni.fi.pb162.project.geometry.Vertex2D.getRevertedArray;

/**
 * @author Stefan Ockay
 */

public class CollectionPolygon extends SimplePolygon implements Inverter {
    private final LinkedList<Vertex2D> vertices;

    public CollectionPolygon(Vertex2D[] vertices) {
        super(vertices);
        this.vertices = new LinkedList<>(Arrays.asList(vertices));
    }

    @Override
    public Vertex2D getVertex(int index) throws IllegalArgumentException {
        if (index < 0) {
            throw new IllegalArgumentException("Negative index.");
        }
        return vertices.get(index % getNumVertices());
    }

    @Override
    public int getNumVertices() {
        return vertices.size();
    }

    @Override
    public Collection<Vertex2D> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    @Override
    public Polygon invert() {
        Vertex2D[] revArray = vertices.toArray(new Vertex2D[0]);
        revArray = getRevertedArray(revArray);
        return new CollectionPolygon(revArray);
    }

    private double getMinX() {
        if (vertices.size() == 0) {
            return Double.NaN;
        }
        double minX = vertices.get(0).getX();
        for (Vertex2D vertex: vertices) {
            if (Math.min(minX, vertex.getX()) < minX) {
                minX = vertex.getX();
            }
        }
        return minX;
    }

    public Collection<Vertex2D> removeLeftmostVertices() {
        LinkedList<Vertex2D> leftMost = new LinkedList<>();
        ListIterator<Vertex2D> iterator = vertices.listIterator();
        double minX = getMinX();
        Vertex2D vertex;
        while (iterator.hasNext()) {
            vertex = iterator.next();
            if (vertex.getX() == minX) {
                leftMost.add(new Vertex2D(vertex.getX(), vertex.getY()));
                iterator.remove();
            }
        }
        return leftMost;
    }
}
