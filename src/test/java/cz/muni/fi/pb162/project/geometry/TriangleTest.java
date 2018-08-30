package cz.muni.fi.pb162.project.geometry;

import cz.muni.fi.pb162.project.helper.BasicRulesTester;
import cz.muni.fi.pb162.project.utils.SimpleMath;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static cz.muni.fi.pb162.project.helper.BasicRulesTester.DELTA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Class testing Triangle implementation.
 *
 * @author Marek Sabo
 */
public class TriangleTest {

    private Triangle triangle;

    private final Vertex2D vertex1 = new Vertex2D(-100, -100);
    private final Vertex2D vertex2 = new Vertex2D(100, 100);
    private final Vertex2D vertex3 = new Vertex2D(0, 0);

    @Before
    public void setUp() {
        triangle = new Triangle(vertex1, vertex2, vertex3);
    }

    @Test
    public void attributesFinal() {
        BasicRulesTester.attributesFinal(Triangle.class);
    }

    @Test
    public void testAllWidths() {
        testWidth(new Vertex2D[] {vertex1, vertex2, vertex3});
        testWidth(new Vertex2D[] {new Vertex2D(-3,-1), new Vertex2D(-2,-2), new Vertex2D(-1,-1)});
    }


    public void testWidth(Vertex2D[] vertices) {
        Triangle triangle = new Triangle(vertices[0], vertices[1], vertices[2]);
        assertEquals(SimpleMath.maxX(vertices).getX() - SimpleMath.minX(vertices).getX(), triangle.getWidth(), DELTA);
    }

    @Test
    public void testAllHeights() {
        testHeight(new Vertex2D[] {vertex1, vertex2, vertex3});
        testHeight(new Vertex2D[] {new Vertex2D(-3,-1), new Vertex2D(-2,-2), new Vertex2D(-1,-1)});
    }

    public void testHeight(Vertex2D[] vertices) {
        Triangle triangle = new Triangle(vertices[0], vertices[1], vertices[2]);
        assertEquals(SimpleMath.maxY(vertices).getY() - SimpleMath.minY(vertices).getY(), triangle.getHeight(), DELTA);
    }

    @Test
    public void gettersInRange() {
        assertTrue(areTrianglesSame(triangle, new Triangle(vertex1, vertex2, vertex3)));
    }

    @Test
    public void gettersOutOfRangePositive() {
        assertSame(triangle.getVertex(0), triangle.getVertex(3));
        assertSame(triangle.getVertex(1), triangle.getVertex(4));
        assertSame(triangle.getVertex(2), triangle.getVertex(5));
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void gettersOutOfRangeNegative() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("index");
        triangle.getVertex(-1);
    }

    @Test
    public void toStringMessage() {
        assertEquals("Polygon: vertices = [-100.0, -100.0] [100.0, 100.0] [0.0, 0.0]", triangle.toString());

        Triangle t = new Triangle(
                new Vertex2D(-1.2, 0.0),
                new Vertex2D(1.2, 0.0),
                new Vertex2D(0.0, 2.07846097)
        );
        assertEquals("Polygon: vertices = [-1.2, 0.0] [1.2, 0.0] [0.0, 2.07846097]", t.toString());
    }

    @Test
    public void checkIfDivided() {
        assertFalse(triangle.isDivided());
        assertTrue(triangle.divide());
        assertTrue(triangle.isDivided());
        assertFalse(triangle.divide());
    }

    @Test
    public void subTriangleGettersNull() {
        assertNull(triangle.getSubTriangle(-1));
        assertNull(triangle.getSubTriangle(0));
        assertNull(triangle.getSubTriangle(1));
        assertNull(triangle.getSubTriangle(2));
        assertNull(triangle.getSubTriangle(3));
    }

    @Test
    public void subTriangleGettersInRangeNotNull() {
        triangle.divide();
        assertNotNull(triangle.getSubTriangle(0));
        assertNotNull(triangle.getSubTriangle(1));
        assertNotNull(triangle.getSubTriangle(2));
    }

    @Test
    public void subTriangleGettersOutOfRangeNull() {
        triangle.divide();
        assertNull(triangle.getSubTriangle(-1));
    }

    @Test
    public void equilateralTriangle() {
        Triangle t = new Triangle(
                new Vertex2D(-1.2, 0),
                new Vertex2D(1.2, 0),
                new Vertex2D(0, 2.07846097)
        );
        assertTrue(t.isEquilateral());
    }

    @Test
    public void nonEquilateralTriangle1() {
        Triangle t = new Triangle(
                new Vertex2D(-10, 0),
                new Vertex2D(0, 0),
                new Vertex2D(0, 10)
        );
        assertFalse(t.isEquilateral());
    }

    @Test
    public void nonEquilateralTriangle2() {
        Triangle t = new Triangle(
                new Vertex2D(-10, 0),
                new Vertex2D(3, 2),
                new Vertex2D(1, 10)
        );
        assertFalse(t.isEquilateral());
    }

    private boolean areTrianglesSame(Triangle expected, Triangle actual) {
        return verticesAreSame(expected.getVertex(0), actual.getVertex(0))
                && verticesAreSame(expected.getVertex(1), actual.getVertex(1))
                && verticesAreSame(expected.getVertex(2), actual.getVertex(2));
    }

    private boolean containsTriangle(Triangle actual, Triangle[] expected) {
        for (Triangle t : expected) {
            if (trianglesAreSameExcludingOrder(t, actual)) return true;
        }
        return false;
    }

    private boolean trianglesAreSameExcludingOrder(Triangle t1, Triangle t2) {
        Vertex2D[] t2vertices = new Vertex2D[] {t2.getVertex(0), t2.getVertex(1), t2.getVertex(2) };

        for (int i = 0; i < 3; i++) {
            if (!containsVertex(t1.getVertex(i), t2vertices)) return false;
        }
        return true;
    }

    private boolean containsVertex(Vertex2D expected, Vertex2D[] actual) {
        for (Vertex2D v : actual) {
            if (verticesAreSame(expected, v)) return true;
        }
        return false;
    }

    private boolean verticesAreSame(Vertex2D v1, Vertex2D v2) {
        return v1.getX() == v2.getX() && v1.getY() == v2.getY();
    }

    @Test
    public void nestedDivisionNegativeInput() {
        assertFalse(triangle.divide(-5));
        assertFalse(triangle.divide(-1));
    }

    @Test
    public void nestedDivisionZeroInput() {
        assertFalse(triangle.divide(0));
    }

    @Test
    public void nestedDivisionDepth1() {
        assertTrue(triangle.divide(1));
        assertSubTrianglesDivided(triangle);
    }

    @Test
    public void nestedDivisionDepth2() {
        assertTrue(triangle.divide(1));
        assertSubTrianglesDivided(triangle);

        // divide second depth manually
        triangle.getSubTriangle(0).divide();
        triangle.getSubTriangle(1).divide();
        triangle.getSubTriangle(2).divide();

        Triangle second = copyTriangle(triangle);
        assertTrue(second.divide(2));
        assertSubTrianglesDivided(second);

        assertSubTriangles(triangle.getSubTriangle(0), second.getSubTriangle(0));
        assertSubTriangles(triangle.getSubTriangle(1), second.getSubTriangle(1));
        assertSubTriangles(triangle.getSubTriangle(2), second.getSubTriangle(2));
    }

    private Triangle copyTriangle(Triangle triangle) {
        return new Triangle(triangle.getVertex(0), triangle.getVertex(1), triangle.getVertex(2));
    }

    private void assertSubTriangles(Triangle expected, Triangle actual) {
        Triangle[] actualTriangles = new Triangle[] {actual.getSubTriangle(0),
                actual.getSubTriangle(1), actual.getSubTriangle(2)};
        assertTrue(containsTriangle(expected.getSubTriangle(0), actualTriangles));
        assertTrue(containsTriangle(expected.getSubTriangle(1), actualTriangles));
        assertTrue(containsTriangle(expected.getSubTriangle(2), actualTriangles));
    }


    @Test
    public void division() {
        triangle.divide();
        assertSubTrianglesDivided(triangle);
    }

    private void assertSubTrianglesDivided(Triangle t) {
        Triangle[] expectedTriangles = new Triangle[]{
                new Triangle(
                        new Vertex2D(-100.0, -100.0),
                        new Vertex2D(0.0, 0.0),
                        new Vertex2D(-50.0, -50.0)
                ),
                new Triangle(
                        new Vertex2D(100.0, 100.0),
                        new Vertex2D(0.0, 0.0),
                        new Vertex2D(50.0, 50.0)
                ),
                new Triangle(
                        new Vertex2D(0.0, 0.0),
                        new Vertex2D(-50.0, -50.0),
                        new Vertex2D(50.0, 50.0)
                )
        };
        assertTrue(containsTriangle(t.getSubTriangle(0), expectedTriangles));
        assertTrue(containsTriangle(t.getSubTriangle(1), expectedTriangles));
        assertTrue(containsTriangle(t.getSubTriangle(2), expectedTriangles));
    }

}
