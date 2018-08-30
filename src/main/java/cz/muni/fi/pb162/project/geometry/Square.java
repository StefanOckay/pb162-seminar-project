package cz.muni.fi.pb162.project.geometry;


/**
 * @author Stefan Ockay
 */
public class Square extends GeneralRegularPolygon {

    public Square(Circle circle) {
        super(circle.getCenter(), 4, circle.getRadius());
    }

    public Vertex2D getVertex(int index) {
        if (index < 0 || index > 3) {
            return null;
        }
        double halfEdge = getRadius() * Math.sqrt(2) / 2.0;
        Vertex2D vector = null;
        if (index == 0) {
            vector = new Vertex2D(-halfEdge, -halfEdge);
        }
        if (index == 1) {
            vector = new Vertex2D(halfEdge, -halfEdge);
        }
        if (index == 2) {
            vector = new Vertex2D(halfEdge, halfEdge);
        }
        if (index == 3) {
            vector = new Vertex2D(-halfEdge, +halfEdge);
        }
        return getCenter().move(vector);
    }

    @Override
    public String toString() {
        String vS0 = getVertex(0).toString();
        String vS1 = getVertex(1).toString();
        String vS2 = getVertex(2).toString();
        String vS3 = getVertex(3).toString();
        return String.format("Square: vertices=%s %s %s %s", vS0, vS1, vS2, vS3);
    }

}
