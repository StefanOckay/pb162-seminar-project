package cz.muni.fi.pb162.project.utils;

import cz.muni.fi.pb162.project.geometry.Vertex2D;

/**
 * @author Stefan Ockay
 */

public class SimpleMath {

    public static Vertex2D minX(Vertex2D[] vertices) {
        if (vertices == null) {
            return null;
        }
        Vertex2D minXVertex = vertices[0];
        for (Vertex2D vertex:vertices) {
            if (Math.min(minXVertex.getX(), vertex.getX()) < minXVertex.getX()) {
                minXVertex = vertex;
            }
        }
        return minXVertex;
    }

    public static Vertex2D minY(Vertex2D[] vertices) {
        if (vertices == null) {
            return null;
        }
        Vertex2D minYVertex = vertices[0];
        for (Vertex2D vertex:vertices) {
            if (Math.min(minYVertex.getY(), vertex.getY()) < minYVertex.getY()) {
                minYVertex = vertex;
            }
        }
        return minYVertex;
    }

    public static Vertex2D maxX(Vertex2D[] vertices) {
        if (vertices == null) {
            return null;
        }
        Vertex2D maxXVertex = vertices[0];
        for (Vertex2D vertex:vertices) {
            if (Math.max(maxXVertex.getX(), vertex.getX()) > maxXVertex.getX()) {
                maxXVertex = vertex;
            }
        }
        return maxXVertex;
    }

    public static Vertex2D maxY(Vertex2D[] vertices) {
        if (vertices == null) {
            return null;
        }
        Vertex2D maxYVertex = vertices[0];
        for (Vertex2D vertex:vertices) {
            if (Math.max(maxYVertex.getY(), vertex.getY()) > maxYVertex.getY()) {
                maxYVertex = vertex;
            }
        }
        return maxYVertex;
    }

}
