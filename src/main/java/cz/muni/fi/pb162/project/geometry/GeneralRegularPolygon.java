package cz.muni.fi.pb162.project.geometry;

/**
 * @author Stefan Ockay
 */
public class GeneralRegularPolygon implements RegularPolygon, Colored {
    private final Vertex2D center;
    private final int nEdges;
    private final double radius;
    private Color color = Color.BLACK;

    public GeneralRegularPolygon(Vertex2D center, int nEdges, double radius) {
        this.center = center;
        this.nEdges = nEdges;
        this.radius = radius;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getNumEdges() {
        return nEdges;
    }

    @Override
    public double getEdgeLength() {
        return 2 * radius * Math.sin(Math.PI / nEdges);
    }

    @Override
    public Vertex2D getCenter() {
        return center;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public double getWidth() {
        return 2 * radius;
    }

    @Override
    public double getHeight() {
        return 2 * radius;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof GeneralRegularPolygon)) {
            return false;
        }
        GeneralRegularPolygon polygon = (GeneralRegularPolygon)object;
        boolean result = nEdges == polygon.getNumEdges() && radius == polygon.getRadius() && color == polygon.getColor();
        if (center == null && polygon.getCenter() == null) {
            return result;
        }
        if (center != null && polygon.getCenter() != null) {
            return result && center.equals(polygon.getCenter());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Double.hashCode(radius);
        result = 31 * result + nEdges;
        result = 31 * result + color.hashCode();
        if (center == null) {
            return result;
        }
        result = 31 * result + center.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String s = "%d-gon: center=%s, radius=" + radius + ", color=%s";
        return String.format(s, nEdges, center.toString(), color.toString());
    }
}
