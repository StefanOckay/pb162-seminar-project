package cz.muni.fi.pb162.project.geometry;

/**
 * @author Stefan Ockay
 */

public class ColoredPolygon {
    private Polygon polygon = null;
    private Color color;

    public ColoredPolygon(Polygon polygon, Color color) {
        this.color = color;
        if (polygon == null) {
            return;
        }
        this.polygon = polygon;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ColoredPolygon)) {
            return false;
        }
        ColoredPolygon coloredPolygon = (ColoredPolygon)object;
        if (coloredPolygon.getPolygon() == null && polygon == null) {
            return true;
        }
        return coloredPolygon.getPolygon() == polygon;
    }

    @Override
    public int hashCode() {
        if (polygon == null) {
            return 0;
        }
        return polygon.hashCode();
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public Color getColor() {
        return color;
    }
}
