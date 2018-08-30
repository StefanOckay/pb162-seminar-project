package cz.muni.fi.pb162.project.geometry;

import java.util.ArrayList;


/**
 * @author Stefan Ockay
 */
public class Triangle extends ArrayPolygon implements Measurable {
    private final Triangle[] subTriangles = new Triangle[3];
    public static final double EPSILON = 0.001;

    public Triangle(Vertex2D A, Vertex2D B, Vertex2D C) {
        super(new Vertex2D[]{A, B, C});
    }

    public Triangle getSubTriangle(int index) {
        if (!isDivided()) {
            return null;
        }
        if (index < 0 || index > 2) {
            return null;
        }
        return subTriangles[index];
    }

    public boolean isEquilateral() {
        ArrayList<Vertex2D> verts = new ArrayList<>(getVertices());
        double aLen = verts.get(0).distance(verts.get(1));
        double bLen = verts.get(1).distance(verts.get(2));
        double cLen = verts.get(2).distance(verts.get(0));
        return Math.abs(aLen - bLen) < EPSILON && Math.abs(bLen - cLen) < EPSILON && Math.abs(cLen - aLen) < EPSILON;
    }

    private static Vertex2D getMidVertex(Vertex2D A, Vertex2D B) {
        return new Vertex2D((A.getX() + B.getX()) / 2.0, (A.getY() + B.getY()) / 2.0);
    }

    private static Vertex2D copyVertex(Vertex2D vertex) {
        return new Vertex2D(vertex.getX(), vertex.getY());
    }

    public boolean isDivided() {
        return subTriangles[0] != null && subTriangles[1] != null && subTriangles[2] != null;
    }

    public boolean divide() {
        if (isDivided()) {
            return false;
        }
        ArrayList<Vertex2D> verts = new ArrayList<>(getVertices());
        Vertex2D t1A = copyVertex(verts.get(0));
        Vertex2D t1B = getMidVertex(verts.get(0), verts.get(1));
        Vertex2D t1C = getMidVertex(verts.get(0), verts.get(2));
        Triangle t1 = new Triangle(t1A, t1B, t1C);
        Vertex2D t2A = getMidVertex(verts.get(0), verts.get(1));
        Vertex2D t2B = copyVertex(verts.get(1));
        Vertex2D t2C = getMidVertex(verts.get(1), verts.get(2));
        Triangle t2 = new Triangle(t2A, t2B, t2C);
        Vertex2D t3A = getMidVertex(verts.get(0), verts.get(2));
        Vertex2D t3B = getMidVertex(verts.get(1), verts.get(2));
        Vertex2D t3C = copyVertex(verts.get(2));
        Triangle t3 = new Triangle(t3A, t3B, t3C);
        subTriangles[0] = t1;
        subTriangles[1] = t2;
        subTriangles[2] = t3;
        return true;
    }

    public boolean divide(int depth) {
        if (depth <= 0) {
            return false;
        }
        divide();
        subTriangles[0].divide(depth - 1);
        subTriangles[1].divide(depth - 1);
        subTriangles[2].divide(depth - 1);
        return true;
    }
}
