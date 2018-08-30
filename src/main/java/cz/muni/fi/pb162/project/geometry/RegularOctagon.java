package cz.muni.fi.pb162.project.geometry;

/**
 * @author Stefan Ockay
 */
public class RegularOctagon extends GeneralRegularPolygon {

    public RegularOctagon(Vertex2D center, double radius) {
        super(center, 8, radius);
    }
}
