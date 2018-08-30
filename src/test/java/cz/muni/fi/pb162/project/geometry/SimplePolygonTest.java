package cz.muni.fi.pb162.project.geometry;

import cz.muni.fi.pb162.project.helper.BasicRulesTester;
import cz.muni.fi.pb162.project.utils.SimpleMath;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static cz.muni.fi.pb162.project.helper.BasicRulesTester.DELTA;
import static org.junit.Assert.assertEquals;

/**
 * Tests SimplePolygon class.
 *
 * @author Marek Sabo
 */
public class SimplePolygonTest {

    private SimplePolygon polygon;
    private static final Vertex2D[] vertices = {
            new Vertex2D(-3,-1),
            new Vertex2D(-2,-2),
            new Vertex2D(-4,-1)
    };


    @Before
    public void setUp() throws Exception {
        polygon = new MockPolygon();
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = IllegalArgumentException.class)
    public void nullInConstructor() {
        new SimplePolygon(null) {
            public Vertex2D getVertex(int index) throws IllegalArgumentException { return null; }
            public int getNumVertices() { return 0; }
            public Collection<Vertex2D> getVertices() { return null; }
        };
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = IllegalArgumentException.class)
    public void nullInConstructorArray() {
        new SimplePolygon(new Vertex2D[] { new Vertex2D(0,0), null, null}) {
            public Vertex2D getVertex(int index) throws IllegalArgumentException { return null; }
            public int getNumVertices() { return 0; }
            public Collection<Vertex2D> getVertices() { return null; }
        };
    }

    @Test
    public void classAttributes() {
        BasicRulesTester.attributesAmount(SimplePolygon.class, 0);
    }

    @Test
    public void getHeight() {
        assertEquals(SimpleMath.maxY(vertices).getY() - SimpleMath.minY(vertices).getY(),
                polygon.getHeight(), DELTA);
    }

    @Test
    public void getWidth() {
        assertEquals(SimpleMath.maxX(vertices).getX() - SimpleMath.minX(vertices).getX(),
                polygon.getWidth(), DELTA);
    }

    @Test
    public void toStringMessage() {
        assertEquals("Polygon: vertices = [-3.0, -1.0] [-2.0, -2.0] [-4.0, -1.0]", polygon.toString());
    }

    private class MockPolygon extends SimplePolygon {

        private MockPolygon() {
            super(vertices);
        }

        @Override
        public Vertex2D getVertex(int index) {
            return vertices[index% vertices.length];
        }

        @Override
        public int getNumVertices() {
            return vertices.length;
        }

        @Override
        public Collection<Vertex2D> getVertices() {
            return Arrays.asList(vertices);
        }
    }

}
