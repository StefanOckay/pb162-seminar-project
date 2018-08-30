package cz.muni.fi.pb162.project.demo;

import cz.muni.fi.pb162.project.geometry.RegularOctagon;
import cz.muni.fi.pb162.project.geometry.Vertex2D;


/**
 * @author Stefan Ockay
 */
public class Demo {

    public static void main(String[] args) {
        RegularOctagon regularOctagon = new RegularOctagon(new Vertex2D(0, 0), 1);
        System.out.println(regularOctagon.toString());
    }
}
