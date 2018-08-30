package cz.muni.fi.pb162.project.geometry;

import cz.muni.fi.pb162.project.comparator.VertexInverseComparator;
import cz.muni.fi.pb162.project.helper.BasicRulesTester;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import static cz.muni.fi.pb162.project.helper.BasicRulesTester.DELTA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Simple Vertex2D tests.
 *
 * @author Marek Sabo
 */
public class Vertex2DTest {

    private Vertex2D vertex2D;

    private static final double X = -1.2;
    private static final double Y = 2.4;

    @Before
    public void setUp() {
        vertex2D = new Vertex2D(X, Y);
    }

    @Test
    public void attributesFinal() {
        BasicRulesTester.attributesFinal(Vertex2D.class);
    }

    @Test
    public void testDistancePositiveInput() {
        double distance = new Vertex2D(-1.2, 1.2).distance(new Vertex2D(1.3, 1.3));
        assertTrue(distance > 2.5 && distance < 2.503);
    }

    @Test
    public void testDistanceNegativeInput() {
        try {
            assertTrue(vertex2D.distance(null) == -1.0);
        } catch (Exception e) {
            fail("Should return -1 as indicator of wrong input");
        }
    }

    @Test
    public void testSumCoordinates() {
        assertEquals(X + Y, vertex2D.sumCoordinates(), DELTA);
    }

    @Test
    public void testMoveVertex() {
        final double XX = -3.3;
        final double YY = -5.5;
        Vertex2D negativeVertex = new Vertex2D(XX, YY);

        Vertex2D resultVertex = vertex2D.move(negativeVertex);

        assertEquals(XX + X, resultVertex.getX(), DELTA);
        assertEquals(YY + Y, resultVertex.getY(), DELTA);

        assertEquals(XX, negativeVertex.getX(), DELTA);
        assertEquals(YY, negativeVertex.getY(), DELTA);
    }

    @Test
    public void toStringMessage() {
        assertEquals("[" + X + ", " + Y + "]", vertex2D.toString());
    }

    @Test
    public void testGetters() {
        Vertex2D v = new Vertex2D(-1.234, 1.234);
        assertTrue(v.getX() == -1.234);
        assertTrue(v.getY() == 1.234);
    }

    @Test
    public void equalsSame() {
        assertTrue(new Vertex2D(0,0).equals(new Vertex2D(0,0)));
        assertTrue(new Vertex2D(10.8,-11.2).equals(new Vertex2D(10.8,-11.2)));
    }

    @Test
    public void hashesSame() {
        assertTrue(new Vertex2D(0,0).hashCode() == new Vertex2D(0,0).hashCode());
        assertTrue(new Vertex2D(10.8,-11.2).hashCode() == new Vertex2D(10.8,-11.2).hashCode());
    }

    @SuppressWarnings({"ObjectEqualsNull", "EqualsBetweenInconvertibleTypes"})
    @Test
    public void equalsDifferent() {
        assertFalse(new Vertex2D(0,0).equals(new Vertex2D(0,1)));
        assertFalse(new Vertex2D(1,0).equals(new Vertex2D(0,0)));
        assertFalse(new Vertex2D(1,0).equals(new Circle(vertex2D,0)));
        assertFalse(new Vertex2D(0,0).equals(null));
    }

    @Test
    public void naturalOrderAscending() {
        // given
        Vertex2D v1a1 = new Vertex2D(1, 1);
        Vertex2D v1a2 = new Vertex2D(1, 2);
        Vertex2D v2a2 = new Vertex2D(2, 2);
        // then
        assertTrue(v1a1.compareTo(v1a2) < 0);
        assertTrue(v1a1.compareTo(v2a2) < 0);
        assertTrue(v1a2.compareTo(v2a2) < 0);
    }

    @Test
    public void inverseComparator() {
        // given
        SortedSet<Vertex2D> sortedSet = new TreeSet<>(new VertexInverseComparator());
        sortedSet.add(new Vertex2D(1, 1));
        sortedSet.add(new Vertex2D(0, 0));
        sortedSet.add(new Vertex2D(-1, 1));
        sortedSet.add(new Vertex2D(0, 1));
        sortedSet.add(new Vertex2D(1, 0));
        sortedSet.add(new Vertex2D(0.99, 23));
        // then
        Iterator it = sortedSet.iterator();
        assertEquals(new Vertex2D(1, 1), it.next());
        assertEquals(new Vertex2D(1, 0), it.next());
        assertEquals(new Vertex2D(0.99, 23), it.next());
        assertEquals(new Vertex2D(0, 1), it.next());
        assertEquals(new Vertex2D(0, 0), it.next());
        assertEquals(new Vertex2D(-1, 1), it.next());
        assertFalse(it.hasNext());
    }

}
