package cz.muni.fi.pb162.project.geometry;


/**
 * @author Stefan Ockay
 */
public class Circle extends GeneralRegularPolygon implements Measurable, Circumcircle {

    public Circle(Vertex2D center, double radius) {
        super(center, Integer.MAX_VALUE, radius);
        super.setColor(Color.RED);
    }

    public Circle() {
        this(new Vertex2D(0, 0), 1);
    }

    @Override
    public double getEdgeLength() {
        return 0;
    }

    @Override
    public String toString() {
        return "Circle: center=" + super.getCenter().toString() + ", radius=" + super.getRadius();
    }

}
