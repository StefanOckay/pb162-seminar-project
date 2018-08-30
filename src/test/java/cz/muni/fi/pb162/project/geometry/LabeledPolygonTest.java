package cz.muni.fi.pb162.project.geometry;

import cz.muni.fi.pb162.project.comparator.VertexInverseComparator;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Marek Sabo
 */
public class LabeledPolygonTest {

    private LabeledPolygon polygon;

    @Before
    public void setUp() throws Exception {
        polygon = new LabeledPolygon();
    }

    @Test
    public void checkNumVertices() {
        polygon.addVertex("B", new Vertex2D(1, 5));
        assertTrue(polygon.getNumVertices() == 1);
        polygon.addVertex("A", new Vertex2D(0, 0));
        assertTrue(polygon.getNumVertices() == 2);
    }

    @Test
    public void puttingSamePolygonTwice() {
        // when
        polygon.addVertex("B", new Vertex2D(1, 5));
        polygon.addVertex("B", new Vertex2D(1, 5));
        // then
        assertTrue(polygon.getNumVertices() == 1);
    }


    @Test
    public void getVertexIndexOutOfRangeNegative() {
        // when
        polygon.addVertex("A", new Vertex2D(4, 2));
        // then
        try {
            polygon.getVertex(-1);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
        try {
            polygon.getVertex(-523);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
    }

    @Test
    public void getVertexIndexOutOfRangePositive() {
        // when
        polygon.addVertex("A", new Vertex2D(4, 2));
        polygon.addVertex("B", new Vertex2D(5.2, 1));
        // then
        assertEquals(new Vertex2D(4, 2), polygon.getVertex(0));
        assertEquals(new Vertex2D(5.2, 1), polygon.getVertex(1));
        assertEquals(new Vertex2D(4, 2), polygon.getVertex(2));
        assertEquals(new Vertex2D(5.2, 1), polygon.getVertex(3));
        assertEquals(new Vertex2D(4, 2), polygon.getVertex(4));
    }

    @Test
    public void getVertexIndexInRange() {
        // when
        polygon.addVertex("A", new Vertex2D(0, 0));
        // then
        assertEquals(new Vertex2D(0, 0), polygon.getVertex(0));
    }

    @Test
    public void getVertexIndexInRangeMultiple() {
        // when
        polygon.addVertex("B", new Vertex2D(0, 2));
        polygon.addVertex("A", new Vertex2D(1, 0));
        // then
        assertEquals(new Vertex2D(1, 0), polygon.getVertex(0));
        assertEquals(new Vertex2D(0, 2), polygon.getVertex(1));
        assertEquals(new Vertex2D(1, 0), polygon.getVertex(2));
        assertEquals(new Vertex2D(0, 2), polygon.getVertex(3));
    }

    @Test
    public void getVertices() {
        // when
        polygon.addVertex("A", new Vertex2D(0, 0));
        polygon.addVertex("C", new Vertex2D(1, 4));
        polygon.addVertex("B", new Vertex2D(7, 3));
        // then
        Set<Vertex2D> auxSet = new HashSet<>();
        auxSet.add(new Vertex2D(0, 0));
        auxSet.add(new Vertex2D(1, 4));
        auxSet.add(new Vertex2D(7, 3));
        polygon.getVertices().containsAll(auxSet);
        Collection<Vertex2D> vertices = polygon.getVertices();
        vertices.containsAll(auxSet);
        assertTrue(vertices.size() == 3);
        assertNewOrUnmodifiableCollection(polygon, vertices);
    }

    @Test
    public void addVertexWithNull() {
        try {
            polygon.addVertex(null, null);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }
        try {
            polygon.addVertex("A", null);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals("vertex", ex.getMessage());
        }
        try {
            polygon.addVertex(null, new Vertex2D(1, 4));
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals("label", ex.getMessage());
        }
    }

    @Test
    public void getVertexLabelNull() {
        try {
            polygon.getVertex(null);
        } catch (Exception ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    public void getVertexLabel() {
        // when
        polygon.addVertex("A", new Vertex2D(0, 0));
        // then
        assertEquals(new Vertex2D(0, 0), polygon.getVertex("A"));
    }

    @Test
    public void checkGetAllLabels() {
        // when
        polygon.addVertex("A", new Vertex2D(0, 0));
        polygon.addVertex("C", new Vertex2D(1, 4));
        polygon.addVertex("A", new Vertex2D(0, 2));
        polygon.addVertex("C", new Vertex2D(1, 4));
        polygon.addVertex("B", new Vertex2D(7, 3));
        // then
        Set<String> auxSet = new HashSet<>();
        auxSet.add("A");
        auxSet.add("B");
        auxSet.add("C");
        Collection<String> labels = polygon.getLabels();
        labels.containsAll(auxSet);
        assertTrue(labels.size() == 3);
        assertNewOrUnmodifiableCollection(polygon, labels);
    }

    @Test
    public void checkGetLabelsWithNonExistingVertex() {
        // when
        polygon.addVertex("A", new Vertex2D(0, 0));
        // then
        assertTrue(polygon.getLabels(new Vertex2D(0, 42)).isEmpty());
        assertTrue(polygon.getLabels(new Vertex2D(1, 0)).isEmpty());
    }

    @Test
    public void checkGetLabelsWithExistingVertex() {
        // when
        polygon.addVertex("B", new Vertex2D(1, 1));
        polygon.addVertex("B", new Vertex2D(0, 0));
        polygon.addVertex("C", new Vertex2D(1, 4));
        polygon.addVertex("A", new Vertex2D(0, 0));
        // then
        Collection<String> labels = polygon.getLabels(new Vertex2D(0, 0));
        Iterator it1 = labels.iterator();
        assertEquals("A", it1.next());
        assertEquals("B", it1.next());
        assertFalse(it1.hasNext());
        Iterator it2 = polygon.getLabels(new Vertex2D(1, 4)).iterator();
        assertEquals("C", it2.next());
        assertFalse(it2.hasNext());
        assertNewOrUnmodifiableCollection(polygon, labels);
    }

    private void assertNewOrUnmodifiableCollection(Polygon polygon, Collection<?> collection) {
        int expectedSize = polygon.getNumVertices();
        try {
            collection.clear();
            String message = "Method returns modifiable collection - return new or unmodifiable.";
            assertTrue(message,polygon.getNumVertices() == expectedSize);
        } catch (UnsupportedOperationException e) {
            // ok
        }
    }

    @Test
    public void getSortedVertices() {
        // when
        polygon.addVertex("B", new Vertex2D(1, 0));
        polygon.addVertex("C", new Vertex2D(1, 4));
        polygon.addVertex("A", new Vertex2D(0, 0));
        // then
        Iterator it = polygon.getSortedVertices().iterator();
        assertEquals(new Vertex2D(0, 0), it.next());
        assertEquals(new Vertex2D(1, 0), it.next());
        assertEquals(new Vertex2D(1, 4), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void getSortedVerticesInverseComparator() {
        // given
        Comparator<Vertex2D> comparator = new VertexInverseComparator();
        // when
        polygon.addVertex("B", new Vertex2D(1, 0));
        polygon.addVertex("C", new Vertex2D(1, 4));
        polygon.addVertex("A", new Vertex2D(0, 0));
        // then
        Iterator it = polygon.getSortedVertices(comparator).iterator();
        assertEquals(new Vertex2D(1, 4), it.next());
        assertEquals(new Vertex2D(1, 0), it.next());
        assertEquals(new Vertex2D(0, 0), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void duplicateVertices() {
        // when
        polygon.addVertex("A", new Vertex2D(-20, 20));
        polygon.addVertex("B", new Vertex2D(100, 100));
        polygon.addVertex("C", new Vertex2D(-100, -100));
        polygon.addVertex("D", new Vertex2D(-20, 20));
        polygon.addVertex("E", new Vertex2D(-100, 100));
        polygon.addVertex("F", new Vertex2D(100, -100));
        polygon.addVertex("G", new Vertex2D(0, 0));
        polygon.addVertex("H", new Vertex2D(0, 0));
        // then
        Set<Vertex2D> auxSet = new HashSet<>();
        auxSet.add(new Vertex2D(-20, 20));
        auxSet.add(new Vertex2D(0, 0));
        Collection<Vertex2D> duplicateVertices = polygon.duplicateVertices();
        assertTrue(duplicateVertices.containsAll(auxSet));
        assertTrue(duplicateVertices.size() == 2);
    }
}
