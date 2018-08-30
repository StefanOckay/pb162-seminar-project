package cz.muni.fi.pb162.project.geometry;

import java.util.Collection;
import java.util.Arrays;
import java.util.Collections;

import static cz.muni.fi.pb162.project.geometry.Vertex2D.getRevertedArray;

/**
 * @author Stefan Ockay
 */

public class ArrayPolygon extends SimplePolygon implements Inverter {
    private final Vertex2D[] vertices;

    public ArrayPolygon(Vertex2D[] vertices) {
        super(vertices);
        this.vertices = vertices.clone();
    }

    private boolean compareVertices(ArrayPolygon polygon) {
        if (getNumVertices() != polygon.getNumVertices()) {
            return false;
        }
        int i = 0;
        int iOffset = 0;
        int j = 0;
        while (i < getNumVertices() && iOffset < getNumVertices()) {
            if (getVertex(j).equals(polygon.getVertex(iOffset))) {
                iOffset++;
                j = (i + iOffset) % getNumVertices();
            } else {
                i++;
                iOffset = 0;
                j = i;
            }
        }
        if (iOffset == getNumVertices()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ArrayPolygon)) {
            return false;
        }
        ArrayPolygon polygon = (ArrayPolygon)object;
        if (compareVertices(polygon)) {
            return true;
        }
        ArrayPolygon invPolygon = (ArrayPolygon)polygon.invert();
        return compareVertices(invPolygon);
    }

    @Override
    public int hashCode() {
        int result = 17;
        int verticesHash = 0;
        for (Vertex2D vertex: vertices) {
            verticesHash += vertex.hashCode();
            verticesHash %= Integer.MAX_VALUE / 2;
        }
        result = 31 * result + verticesHash;
        result = 31 * result + getNumVertices();
        return result;
    }

    @Override
    public Vertex2D getVertex(int index) throws IllegalArgumentException {
        if (index < 0) {
            throw new IllegalArgumentException("Negative index.");
        }
        return vertices[index % getNumVertices()];
    }

    @Override
    public int getNumVertices() {
        return vertices.length;
    }

    @Override
    public Collection<Vertex2D> getVertices() {
        return Collections.unmodifiableList(Arrays.asList(vertices));
    }

    @Override
    public Polygon invert() {
        return new ArrayPolygon(getRevertedArray(vertices));
    }
}
