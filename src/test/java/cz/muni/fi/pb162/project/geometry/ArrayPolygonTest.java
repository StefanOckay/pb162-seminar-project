package cz.muni.fi.pb162.project.geometry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests polygon implemented by array.
 *
 * @author Marek Sabo
 */
public class ArrayPolygonTest {

    private static final Vertex2D vertex1 = new Vertex2D(-100, -100);
    private static final Vertex2D vertex2 = new Vertex2D(-40, 10);
    private static final Vertex2D vertex3 = new Vertex2D(50, 20);
    private static final Vertex2D vertex4 = new Vertex2D(10, -20);
    private static final Vertex2D vertex5 = new Vertex2D(60, -40);

    private static final Vertex2D[] ARRAY1 = {
            vertex1,
            vertex2,
            vertex3,
            vertex4,
            vertex5
    };

    private static final Vertex2D[] ARRAY2 = {
            vertex3,
            vertex4,
            vertex5,
            vertex1,
            vertex2
    };

    private static final Vertex2D[] ARRAY3 = {
            vertex2,
            vertex1,
            vertex5,
            vertex4,
            vertex3
    };

    private static final Vertex2D[] ARRAY4 = {
            vertex2,
            vertex1,
            vertex3,
            vertex4,
            vertex5
    };


    private Vertex2D[] vertices = {
            new Vertex2D(-3, -1),
            new Vertex2D(-2, -2),
            new Vertex2D(-1, -1)
    };

    private ArrayPolygon arrayPolygon;

    @Before
    public void setUp() throws Exception {
        arrayPolygon = new ArrayPolygon(vertices);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullInConstructor() {
        new ArrayPolygon(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullInConstructorArray() {
        new ArrayPolygon(new Vertex2D[]{
                new Vertex2D(-3, -1),
                new Vertex2D(-2, -2),
                null
        });
    }

    @Test
    public void constructorCopiesArray() {
        vertices[0] = new Vertex2D(42, 42);
        assertEquals(new Vertex2D(-3, -1), arrayPolygon.getVertex(0));
    }

    @Test
    public void getVerticesReturnsCorrectVertices() {
        Vertex2D[] getterArray = arrayPolygon.getVertices().toArray(new Vertex2D[arrayPolygon.getNumVertices()]);
        assertTrue(Arrays.equals(vertices, getterArray));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getVerticesIsUnmodifiable() {
        arrayPolygon.getVertices().clear();
    }

    @Test
    public void gettersPositive() {
        for (int i = 0; i < 7; i++) {
            assertEquals(vertices[i % vertices.length], arrayPolygon.getVertex(i));
        }
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void gettersOutOfRangeNegative() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("index");
        arrayPolygon.getVertex(-5);
    }

    @Test
    public void invert() {
        Vertex2D[] inverted = new Vertex2D[]{
                vertices[2],
                vertices[1],
                vertices[0]
        };
        for (int i = 0; i < arrayPolygon.getNumVertices(); i++) {
            assertEquals(arrayPolygon.invert().getVertex(i), inverted[i]);
        }
    }

    @Test
    public void equalsSame() {
        assertTrue("Same polygons should be equal",
                new ArrayPolygon(ARRAY1).equals(new ArrayPolygon(ARRAY1)));

        assertTrue("Shifted polygons should be equal",
                new ArrayPolygon(ARRAY1).equals(new ArrayPolygon(ARRAY2)));

        assertTrue("Inverted and shifted polygons should be equal",
                new ArrayPolygon(ARRAY1).equals(new ArrayPolygon(ARRAY3)));
    }

    @Test
    public void hashesSame() {
        assertEquals(new ArrayPolygon(ARRAY1).hashCode(),
                new ArrayPolygon(ARRAY1).hashCode()
        );

        assertEquals(new ArrayPolygon(ARRAY1).hashCode(),
                new ArrayPolygon(ARRAY2).hashCode()
        );

        assertEquals(new ArrayPolygon(ARRAY1).hashCode(),
                new ArrayPolygon(ARRAY2).hashCode()
        );
    }

    @SuppressWarnings({"ObjectEqualsNull", "EqualsBetweenInconvertibleTypes"})
    @Test
    public void equalsDifferent() {

        assertFalse(arrayPolygon.equals(null));
        assertFalse(arrayPolygon.equals(new Vertex2D(0, 0)));

        assertFalse("Different polygons marked sa same",
                new ArrayPolygon(ARRAY1).equals(new ArrayPolygon(new Vertex2D[]{new Vertex2D(0, 0)})));

        assertFalse("Different polygons marked sa same",
                new ArrayPolygon(ARRAY1).equals(new ArrayPolygon(ARRAY4)));
    }
}
