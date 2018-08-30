package cz.muni.fi.pb162.project.geometry;

import java.util.Collection;

/**
 * Interface representing basic methods for labeling vertices.
 *
 * @author Marek Sabo
 */
public interface Labelable {


    /**
     * Add labeled vertex to polygon. If label already exists, it is replaced.
     * If any of the input parameters are null, method should throw an exception.
     *
     * @param label  label of the vertex
     * @param vertex the actual vertex object
     */
    void addVertex(String label, Vertex2D vertex);

    /**
     * Get vertex stored under given label in a polygon.
     * If label does not exists, IllegalArgumentException is thrown.
     *
     * @param label label under which the vertex is stored
     * @return vertex with given label
     */
    Vertex2D getVertex(String label);

    /**
     * Method returns the labels of vertices in a polygon.
     * The labels are sorted in the ascending order lexicographically.
     *
     * @return collection of labels in ascending order
     */
    Collection<String> getLabels();

    /**
     * Finds all labels corresponding to given vertex.
     *
     * @param vertex vertex which labels are searched
     * @return collection of corresponding labels
     */
    Collection<String> getLabels(Vertex2D vertex);
}
