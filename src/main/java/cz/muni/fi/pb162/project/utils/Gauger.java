package cz.muni.fi.pb162.project.utils;

import cz.muni.fi.pb162.project.geometry.Measurable;
import cz.muni.fi.pb162.project.geometry.Triangle;

/**
 * @author Stefan Ockay
 */
public class Gauger {

    public static void printMeasurement(Measurable obj) {
        String s = String.format("Width: %s", obj.getWidth());
        System.out.println(s);
        s = String.format("Height: %s", obj.getHeight());
        System.out.println(s);
    }

    public static void printMeasurement(Triangle triangle) {
        System.out.println(triangle.toString());
        printMeasurement((Measurable) triangle);
    }
}
