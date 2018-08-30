package cz.muni.fi.pb162.project.geometry;

/**
 * Interface for convex regular polygons, e.g. equilateral triangle, suqare, regular octagon, etc..
 *
 * @author Radek Oslejsek, Marek Sabo
 */
public interface RegularPolygon extends Measurable, Circumcircle {

    /**
     * Returns number of edges of the regular polygon.
     *
     * @return number of edges
     */
    int getNumEdges();

    /**
     * Returns length of single edge.
     *
     * @return edge length
     */
    double getEdgeLength();

}
