package cz.muni.fi.pb162.project.geometry;

/**
 * Interface representing colors in the real world.
 *
 * @author Marek Sabo
 */
public interface Colored {

    /**
     * Sets the color of the object.
     * @param color color to be set
     */
    void setColor(Color color);

    /**
     * Retrieves the object's color.
     * @return color of the object
     */
    Color getColor();

}
