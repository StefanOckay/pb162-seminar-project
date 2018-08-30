package cz.muni.fi.pb162.project.geometry;

import cz.muni.fi.pb162.project.helper.BasicRulesTester;
import org.junit.Test;

import static cz.muni.fi.pb162.project.helper.BasicRulesTester.DELTA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests 2D square class.
 *
 * @author Marek Sabo
 */
public class SquareTest {

    private static final Vertex2D LEFT_BOTTOM = new Vertex2D(16, 12);
    private static final double EDGE_LEGTH = 10;
    private static final Vertex2D HALF_EDGE = new Vertex2D(EDGE_LEGTH / 2.0, EDGE_LEGTH / 2.0);
    private static final Vertex2D CENTER = LEFT_BOTTOM.move(HALF_EDGE);

    private final Square square2 = new Square(new Circle(CENTER, EDGE_LEGTH / Math.sqrt(2)));


    @SuppressWarnings("ConstantConditions")
    @Test
    public void classAttributesMethods() {
        assertTrue(square2 instanceof GeneralRegularPolygon);
        BasicRulesTester.attributesAmount(Square.class, 1);
        BasicRulesTester.methodsAmount(Square.class, 2);
    }

    @Test
    public void constructor2() {
        testGetterNull(square2);
        testGetters(square2);
    }

    public void testGetterNull(Square square) {
        assertNull(square.getVertex(-1));
        assertNull(square.getVertex(4));
        assertNull(square.getVertex(20));
    }


    private void testGetters(Square square) {
        leftBottom(square);
        rightBottom(square);
        rightTop(square);
        leftTop(square);
    }

    private void leftBottom(Square square) {
        assertEquals(LEFT_BOTTOM.toString(), square.getVertex(0).toString());
    }

    private void rightBottom(Square square) {
        Vertex2D rightBottom = LEFT_BOTTOM.move(new Vertex2D(EDGE_LEGTH, 0));
        assertEquals(rightBottom.toString(), square.getVertex(1).toString());
    }

    private void rightTop(Square square) {
        Vertex2D rightTop = LEFT_BOTTOM.move(new Vertex2D(EDGE_LEGTH, EDGE_LEGTH));
        assertEquals(rightTop.toString(), square.getVertex(2).toString());
    }

    private void leftTop(Square square) {
        Vertex2D leftTop = LEFT_BOTTOM.move(new Vertex2D(0, EDGE_LEGTH));
        assertEquals(leftTop.toString(), square.getVertex(3).toString());
    }

    @Test
    public void testCenter() {
        assertEquals(CENTER.toString(), square2.getCenter().toString());
    }


    @Test
    public void testRadius() {
        assertEquals(EDGE_LEGTH / Math.sqrt(2), square2.getRadius(), DELTA);
    }

    @Test
    public void testGetEdgeLength() {
        assertEquals(EDGE_LEGTH , square2.getEdgeLength(), DELTA);
    }

    @Test
    public void toStringMessage() {
        assertEquals("Square: vertices=[0.0, 0.0] [0.0, 0.0] [0.0, 0.0] [0.0, 0.0]",
                new Square(new Circle(new Vertex2D(0, 0), 0)).toString());
    }

}
