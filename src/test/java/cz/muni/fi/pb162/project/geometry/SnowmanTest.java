package cz.muni.fi.pb162.project.geometry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests snowman class.
 *
 * @author Marek Sabo
 */
public class SnowmanTest {

    RegularPolygon bottomBall = new Circle(new Vertex2D(0, 0), 100);

    @Test
    public void snowmanHas3Balls() {
        assertEquals(3, new Snowman(bottomBall, 0.3).getBalls().length);
    }

    @Test
    public void testDefaultDecreasingFactor() {
        testSnowman(new Snowman(bottomBall, 0.8));
        testSnowman(new Snowman(bottomBall, 0));
        testSnowman(new Snowman(bottomBall, 1.001));
        testSnowman(new Snowman(bottomBall, -1));

    }

    private void testSnowman(Snowman snowman) {
        CircleTest.assertCircle(bottomBall, snowman.getBalls()[0]);
        CircleTest.assertCircle(new Circle(new Vertex2D(0, 180), 80), snowman.getBalls()[1]);
        CircleTest.assertCircle(new Circle(new Vertex2D(0, 324), 64), snowman.getBalls()[2]);
    }

}
