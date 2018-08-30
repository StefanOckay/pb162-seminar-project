package cz.muni.fi.pb162.project.geometry;

import cz.muni.fi.pb162.project.exceptions.EmptyDrawableException;
import cz.muni.fi.pb162.project.exceptions.TransparentColorException;

import java.util.Collection;

/**
 * Interface representing ability to be able to draw on that object.
 *
 * @author Marek Sabo
 */
public interface Drawable {

    /**
     * Changes pencil color.
     *
     * @param color the color which the drawn objects are going to have
     */
    void changeColor(Color color);

    /**
     * Draws the polygon on drawable thing.
     *
     * @param polygon polygon to be drawn
     */
    void drawPolygon(Polygon polygon) throws TransparentColorException;

    /**
     * Erases the polygon.
     *
     * @param polygon polygon to be erased
     */
    void erasePolygon(ColoredPolygon polygon);

    /**
     * Erases the whole paper. The color is not changed.
     */
    void eraseAll() throws EmptyDrawableException;

    /**
     * Returns unmodifiable collection of drawn polygons.
     *
     * @return unmodifiable collection of drawn polygons
     */
    Collection<ColoredPolygon> getAllDrawnPolygons();

    /**
     * Finds and returns polygons which contain input vertex.
     * If vertex is null, it returns empty collection.
     *
     * @param vertex vertex to be searched for
     * @return every polygon containing the vertex
     */
    Collection<ColoredPolygon> findPolygonsWithVertex(Vertex2D vertex);


}
