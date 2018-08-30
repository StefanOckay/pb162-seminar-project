package cz.muni.fi.pb162.project.geometry;


import static cz.muni.fi.pb162.project.utils.SimpleMath.*;

/**
 * @author Stefan Ockay
 */

public abstract class SimplePolygon implements Polygon {

    public SimplePolygon(Vertex2D[] vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("Null argument array.");
        }
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] == null) {
                throw new IllegalArgumentException("Null element in array.");
            }
        }
    }

    @Override
    public String toString() {
        String s = "Polygon: vertices =";
        for (Vertex2D vertex : getVertices()) {
            s += " " + vertex.toString();
        }
        return s;
    }

    @Override
    public double getWidth() {
        Vertex2D[] verticesArray = getVertices().toArray(new Vertex2D[0]);
        Vertex2D maxXVertex = maxX(verticesArray);
        Vertex2D minXVertex = minX(verticesArray);
        return maxXVertex.getX() - minXVertex.getX();
    }

    @Override
    public double getHeight() {
        Vertex2D[] verticesArray = getVertices().toArray(new Vertex2D[0]);
        Vertex2D maxYVertex = maxY(verticesArray);
        Vertex2D minYVertex = minY(verticesArray);
        return maxYVertex.getY() - minYVertex.getY();
    }
}
