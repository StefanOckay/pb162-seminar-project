package cz.muni.fi.pb162.project.geometry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests polygon implemented by collection.
 *
 * @author Marek Sabo
 */
public class CollectionPolygonTest {

    private Vertex2D[] vertices = {
            new Vertex2D(-3, -1),
            new Vertex2D(2, 2),
            new Vertex2D(3, 4),
            new Vertex2D(-3, -1),
            new Vertex2D(-5, -3),
            new Vertex2D(-1, 1)

    };

    private CollectionPolygon collectionPolygon;

    @Before
    public void setUp() throws Exception {
        collectionPolygon = new CollectionPolygon(vertices);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullInConstructor() {
        new CollectionPolygon(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullInConstructorArray() {
        new CollectionPolygon(new Vertex2D[]{
                new Vertex2D(-3, -1),
                new Vertex2D(-2, -2),
                null
        });
    }

    @Test
    public void constructorCopiesArray() {
        vertices[0] = new Vertex2D(42, 42);
        assertEquals(new Vertex2D(-3, -1), collectionPolygon.getVertex(0));
    }

    @Test
    public void getVerticesReturnsCorrectVertices() {
        Vertex2D[] getterArray = collectionPolygon.getVertices().toArray(new Vertex2D[collectionPolygon.getNumVertices()]);
        assertTrue(Arrays.equals(vertices, getterArray));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getVerticesIsUnmodifiable() {
        collectionPolygon.getVertices().clear();
    }

    @Test
    public void gettersPositive() {
        for (int i = 0; i < 7; i++) {
            assertEquals(vertices[i % vertices.length], collectionPolygon.getVertex(i));
        }
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void gettersOutOfRangeNegative() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("index");
        collectionPolygon.getVertex(-5);
    }

    @Test
    public void invert() {
        Vertex2D[] inverted = new Vertex2D[]{
                vertices[5],
                vertices[4],
                vertices[3],
                vertices[2],
                vertices[1],
                vertices[0]
        };
        for (int i = 0; i < collectionPolygon.getNumVertices(); i++) {
            assertEquals(collectionPolygon.invert().getVertex(i), inverted[i]);
        }
    }

    @Test
    public void removeLeftMostVertices() {
        Collection<Vertex2D> leftMost = collectionPolygon.removeLeftmostVertices();
        assertEquals(Collections.singletonList(new Vertex2D(-5, -3)), leftMost);

        leftMost = collectionPolygon.removeLeftmostVertices();
        assertEquals(Arrays.asList(
                new Vertex2D(-3, -1),
                new Vertex2D(-3, -1)), leftMost);
        assertArrayEquals(Arrays.asList(vertices[1], vertices[2], vertices[5]).toArray(),
                collectionPolygon.getVertices().toArray());

        leftMost = collectionPolygon.removeLeftmostVertices();
        assertEquals(Collections.singletonList(new Vertex2D(-1, 1)), leftMost);

        leftMost = collectionPolygon.removeLeftmostVertices();
        assertEquals(Collections.singletonList(new Vertex2D(2, 2)), leftMost);

        leftMost = collectionPolygon.removeLeftmostVertices();
        assertEquals(Collections.singletonList(new Vertex2D(3, 4)), leftMost);

        leftMost = collectionPolygon.removeLeftmostVertices();
        assertEquals(0, leftMost.size());
        assertEquals(0, collectionPolygon.getVertices().size());
    }

}
