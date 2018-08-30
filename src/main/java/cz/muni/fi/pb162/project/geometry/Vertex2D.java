package cz.muni.fi.pb162.project.geometry;


/**
 * @author Stefan Ockay
 */
public class Vertex2D implements Comparable<Vertex2D> {
    private final double x, y;

    public Vertex2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        String s = "[" + x + ", " + y + "]";
        return s;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Vertex2D)) {
            return false;
        }
        Vertex2D vertex = (Vertex2D)object;
        return x == vertex.getX() && y == vertex.getY();
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + Double.hashCode(x);
        result = 31 * result + Double.hashCode(y);
        return result;
    }

    public int compareTo(Vertex2D vertex) {
        double diffX = x - vertex.getX();
        if (diffX == 0) {
            double diffY = getY() - vertex.getY();
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

    public double sumCoordinates() {
        return x + y;
    }

    public Vertex2D move(Vertex2D vertex) {
        if (vertex == null) {
            return null;
        }
        return new Vertex2D(vertex.getX() + x, vertex.getY() + y);
    }

    public double distance(Vertex2D vertex) {
        if (vertex == null) {
            return -1;
        }
        double distSqr = Math.pow((vertex.getX() - getX()), 2) + Math.pow((vertex.getY() - getY()), 2);
        return Math.sqrt(distSqr);
    }

    public static Vertex2D[] getRevertedArray(Vertex2D[] array) {
        Vertex2D[] revArray = new Vertex2D[array.length];
        int endIndex = array.length - 1;
        for (int i = 0; i < array.length / 2; i++) {
            revArray[i] = new Vertex2D(array[endIndex - i].getX(), array[endIndex - i].getY());
            revArray[endIndex - i] = new Vertex2D(array[i].getX(), array[i].getY());
        }
        if (array.length % 2 == 1) {
            revArray[array.length / 2] = array[array.length / 2];
        }
        return revArray;
    }

}
