package cz.muni.fi.pb162.project.geometry;

/**
 * @author Stefan Ockay
 */
public enum Color {
    YELLOW,
    WHITE,
    ORANGE,
    RED,
    GREEN,
    BLUE,
    BLACK;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
