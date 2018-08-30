package cz.muni.fi.pb162.project.comparator;

import cz.muni.fi.pb162.project.geometry.Vertex2D;

import java.util.Comparator;

public class VertexInverseComparator implements Comparator<Vertex2D> {

    @Override
    public int compare(Vertex2D A, Vertex2D B) {
        double diffX = B.getX() - A.getX();
        if (diffX == 0) {
            double diffY = B.getY() - A.getY();
            if (diffY > 0) {
                return 1;
            } else if (diffY < 0) {
                return -1;
            } else {
                return 0;
            }
        }
        if (diffX > 0) {
            return 1;
        } else if (diffX < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
