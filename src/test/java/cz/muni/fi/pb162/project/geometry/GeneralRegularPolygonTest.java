package cz.muni.fi.pb162.project.geometry;

import cz.muni.fi.pb162.project.helper.BasicRulesTester;
import org.junit.Before;
import org.junit.Test;

import static cz.muni.fi.pb162.project.helper.BasicRulesTester.DELTA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests regular n-gon.
 *
 * @author Marek Sabo
 */
public class GeneralRegularPolygonTest {

    public static final Vertex2D CENTER = new Vertex2D(1.0, -1.0);
    private static final double RADIUS = 2.0;
    private GeneralRegularPolygon polygon;

    @Before
    public void setUp() {
        polygon = new GeneralRegularPolygon(CENTER, 6, RADIUS);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void implementsInterface() {
        assertTrue(polygon instanceof RegularPolygon);
        assertTrue(polygon instanceof Colored);
    }

    @Test
    public void getCenter() {
        assertEquals(1.0, polygon.getCenter().getX(), DELTA);
        assertEquals(-1.0, polygon.getCenter().getY(), DELTA);
    }

    @Test
    public void getNumEdges() {
        assertEquals(6, polygon.getNumEdges());
    }

    @Test
    public void getRadius() {
        assertEquals(RADIUS, polygon.getRadius(), DELTA);
    }

    @Test
    public void getEdgeLength() {
        assertEquals(RADIUS, polygon.getEdgeLength(), DELTA);
    }

    @Test
    public void getWidthHeight() {
        assertEquals(4.0, polygon.getWidth(), DELTA);
        assertEquals(4.0, polygon.getHeight(), DELTA);
    }

    @Test
    public void toStringMessage() {
        assertEquals("6-gon: center=[1.0, -1.0], radius=2.0, color=black", polygon.toString());
    }

    @Test
    public void colorToStringLowerCase() {
        assertEquals("black", polygon.getColor().toString());
        polygon.setColor(Color.YELLOW);
        assertEquals("yellow", polygon.getColor().toString());
        polygon.setColor(Color.valueOf("RED")); // 100% coverage
        assertEquals("red", polygon.getColor().toString());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void regularOctagon() {
        RegularOctagon octagon = new RegularOctagon(CENTER, RADIUS);
        assertEquals(8, octagon.getNumEdges());
        assertTrue(octagon instanceof GeneralRegularPolygon);
        BasicRulesTester.attributesAmount(RegularOctagon.class, 0);
        BasicRulesTester.methodsAmount(RegularOctagon.class, 0);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void equalsSame() {
        assertTrue(polygon.equals(polygon));
        assertTrue(new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1).equals(
                new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1)
        ));

        assertTrue(new GeneralRegularPolygon(null, 6, 2.1).equals(
                new GeneralRegularPolygon(null, 6, 2.1))
        );
    }

    @Test
    public void hashesSame() {
        assertTrue(new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1).hashCode()
                == new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1).hashCode());
        assertTrue(new GeneralRegularPolygon(null, 6, 2.1).hashCode()
                == new GeneralRegularPolygon(null, 6, 2.1).hashCode());
        assertTrue(polygon.hashCode() == polygon.hashCode());
    }

    @SuppressWarnings({"ObjectEqualsNull", "EqualsBetweenInconvertibleTypes"})
    @Test
    public void equalsDifferent() {
        assertFalse(new GeneralRegularPolygon(new Vertex2D(42, -1), 6, 2.1).equals(
                new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1))
        );
        assertFalse(new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1).equals(
                new GeneralRegularPolygon(null, 6, 2.1))
        );
        assertFalse(new GeneralRegularPolygon(null, 6, 2.1).equals(
                new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1))
        );
        assertFalse(new GeneralRegularPolygon(new Vertex2D(1, 42), 6, 2.1).equals(
                new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1))
        );
        assertFalse(new GeneralRegularPolygon(new Vertex2D(1, -1), 8, 2.1).equals(
                new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1))
        );
        assertFalse(new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 42).equals(
                new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1))
        );
        assertFalse(new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1).equals(
                new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 42))
        );
        assertFalse(new GeneralRegularPolygon(new Vertex2D(1, -1), 8, 42).equals(
                new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1))
        );
        assertFalse(new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1).equals(
                new Vertex2D(0, 1))
        );
        GeneralRegularPolygon redPolygon = new GeneralRegularPolygon(null, 6, 2.1);
        redPolygon.setColor(Color.RED);
        assertFalse(new GeneralRegularPolygon(null, 6, 2.1).equals(
                redPolygon)
        );
        assertFalse(new GeneralRegularPolygon(new Vertex2D(1, -1), 6, 2.1).equals(null));
    }

}
