package cz.muni.fi.pb162.project.utils;

import cz.muni.fi.pb162.project.geometry.Vertex2D;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

/**
 * Tests math utils class.
 * @author Marek Sabo
 */
public class SimpleMathTest {

    private Vertex2D[] array1 = new Vertex2D[] {
            new Vertex2D(50, 50),
            new Vertex2D(70, 70),
            new Vertex2D(90, 90),
    };


    private Vertex2D[] array2 = new Vertex2D[] {
            new Vertex2D(-50, -50),
            new Vertex2D(-70, -70),
            new Vertex2D(-90, -90),
    };

    @Test
    public void checksLowestX() {
        assertEquals(minX(array1).toString(), SimpleMath.minX(array1).toString());
        assertEquals(minX(array2).toString(), SimpleMath.minX(array2).toString());
    }

    @Test
    public void checksHighestX() {
        assertEquals(maxX(array1).toString(), SimpleMath.maxX(array1).toString());
        assertEquals(maxX(array2).toString(), SimpleMath.maxX(array2).toString());
    }

    @Test
    public void checksLowestY() {
        assertEquals(minY(array1).toString(), SimpleMath.minY(array1).toString());
        assertEquals(minY(array2).toString(), SimpleMath.minY(array2).toString());
    }

    @Test
    public void checksHighestY() {
        assertEquals(maxY(array1).toString(), SimpleMath.maxY(array1).toString());
        assertEquals(maxY(array2).toString(), SimpleMath.maxY(array2).toString());
    }

    private Vertex2D minX(Vertex2D[] array) {
        return Arrays.stream(array).min(Comparator.comparingDouble(Vertex2D::getX)).orElse(null);
    }

    private Vertex2D maxX(Vertex2D[] array) {
        return Arrays.stream(array).max(Comparator.comparingDouble(Vertex2D::getX)).orElse(null);
    }

    private Vertex2D minY(Vertex2D[] array) {
        return Arrays.stream(array).min(Comparator.comparingDouble(Vertex2D::getY)).orElse(null);
    }

    private Vertex2D maxY(Vertex2D[] array) {
        return Arrays.stream(array).max(Comparator.comparingDouble(Vertex2D::getY)).orElse(null);
    }

    @Test
    @SuppressWarnings("AccessStaticViaInstance")
    public void testClass() {
        new SimpleMath();
    }
}
