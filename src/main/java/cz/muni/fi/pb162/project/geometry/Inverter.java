package cz.muni.fi.pb162.project.geometry;

/**
 * Interface for inverting vertices general polygons.
 *
 * @author Marek Sabo
 */
public interface Inverter {

    /**
     * Inverts the ordering of the vertices.
     * For example, if polygon has vertices A-B-C-D, it returns polygon with vertices D-C-B-A.
     *
     * @return inverted polygon
     */
    Polygon invert();

}
