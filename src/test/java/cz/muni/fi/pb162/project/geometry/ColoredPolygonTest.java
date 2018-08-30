package cz.muni.fi.pb162.project.geometry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;

/**
 * Tests colored polygon.
 *
 * @author Marek Sabo
 */
public class ColoredPolygonTest {


    private Vertex2D[] vertices = {
            new Vertex2D(-3, -1),
            new Vertex2D(-2, -2),
            new Vertex2D(-1, -1)
    };

    private Polygon polygon = new ArrayPolygon(vertices);

    private Vertex2D[] randomVertices = {
            new Vertex2D(0, 0),
            new Vertex2D(-2, -3),
            new Vertex2D(5, 1)
    };

    @Test
    public void coloredPolygon() {
        ColoredPolygon coloredPolygon = new ColoredPolygon(polygon, Color.BLUE);
        assertSame(polygon, coloredPolygon.getPolygon());
        assertEquals(Color.BLUE, coloredPolygon.getColor());
    }


    @Test
    public void equalsSame() {
        ColoredPolygon coloredPolygon = new ColoredPolygon(polygon, Color.BLUE);
        assertEquals(new ColoredPolygon(polygon, Color.BLACK), coloredPolygon);
        assertEquals(new ColoredPolygon(null, Color.BLACK), new ColoredPolygon(null, Color.RED));
    }

    @Test
    public void equalsDifferent() {
        ColoredPolygon coloredPolygon = new ColoredPolygon(polygon, Color.BLUE);
        assertNotEquals(new ColoredPolygon(new CollectionPolygon(randomVertices), Color.BLUE), coloredPolygon);
        assertNotEquals(new ColoredPolygon(null, Color.BLUE), coloredPolygon);
        assertNotEquals(coloredPolygon, null);
        assertNotEquals(coloredPolygon, new Circle());
    }

    @Test
    public void hashesSame() {
        ColoredPolygon coloredPolygon = new ColoredPolygon(polygon, Color.BLUE);
        assertEquals(new ColoredPolygon(polygon, Color.BLACK).hashCode(), coloredPolygon.hashCode());
        assertEquals(0, new ColoredPolygon(null, Color.BLACK).hashCode());
    }
}
