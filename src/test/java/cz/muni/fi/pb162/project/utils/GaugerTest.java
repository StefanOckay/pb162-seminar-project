package cz.muni.fi.pb162.project.utils;

import cz.muni.fi.pb162.project.geometry.Circle;
import cz.muni.fi.pb162.project.geometry.Measurable;
import cz.muni.fi.pb162.project.geometry.Triangle;
import cz.muni.fi.pb162.project.geometry.Vertex2D;
import cz.muni.fi.pb162.project.helper.OutputTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing print output.
 *
 * @author Marek Sabo
 */
public class GaugerTest {

    private final Vertex2D vertex1 = new Vertex2D(-100, -100);
    private final Vertex2D vertex2 = new Vertex2D(100, 100);
    private final Vertex2D vertex3 = new Vertex2D(0, 0);

    private final Triangle triangle = new Triangle(vertex1, vertex2, vertex3);

    @Test
    public void printsTriangle() {
        expectedMeasurable(triangle, true);
    }

    @Test
    public void printsMeasurable() {
        expectedMeasurable(triangle, false);
        expectedMeasurable(new Circle(vertex1, 50), false);
    }


    private void expectedMeasurable(Measurable measurable, boolean isTriangle) {
        String triangleString = "";
        if (isTriangle) {
            triangleString = ((Triangle) measurable).toString() + System.lineSeparator();
        }
        String measurableString = "Width: " + measurable.getWidth() + System.lineSeparator() +
                "Height: " + measurable.getHeight() + System.lineSeparator();
        assertEquals(triangleString + measurableString, printOutput(measurable, isTriangle));
    }

    private String printOutput(Measurable measurable, boolean isTriangle) {
        OutputTester ot = new OutputTester();
        ot.captureOutput();
        if (isTriangle) {
            Gauger.printMeasurement((Triangle) measurable);
        } else {
            Gauger.printMeasurement(measurable);
        }
        ot.releaseOutput();
        return ot.getOutput();
    }

    @Test
    @SuppressWarnings("AccessStaticViaInstance")
    public void testClass() {
        new Gauger();
    }
}
