package cz.muni.fi.pb162.project.geometry;

import cz.muni.fi.pb162.project.exceptions.EmptyDrawableException;
import cz.muni.fi.pb162.project.exceptions.MissingVerticesException;
import cz.muni.fi.pb162.project.exceptions.TransparentColorException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests implementation of drawable interface, Paper.
 *
 * @author Marek Sabo
 */
public class PaperTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    private Paper paper;
    private Polygon polygon = new Triangle(new Vertex2D(0, 0), new Vertex2D(10, 10), new Vertex2D(-5, 5));
    private Polygon polygon2 = new ArrayPolygon(new Vertex2D[]{
            new Vertex2D(0, 0),
            new Vertex2D(15, 11),
            new Vertex2D(-4, -9),
            new Vertex2D(14, -1),
    });
    private List<Vertex2D> polygonList1 = Collections.unmodifiableList(new ArrayList<>(polygon.getVertices()));
    private List<Vertex2D> polygonList2 = Collections.unmodifiableList(new ArrayList<>(polygon2.getVertices()));

    @Before
    public void setUp() {
        paper = new Paper();
    }

    @Test
    public void constructorCopiesCollection() throws TransparentColorException, EmptyDrawableException {
        paper.drawPolygon(polygon);
        Paper paper2 = new Paper(paper);
        paper.eraseAll();
        assertColoredCollections(Collections.singletonList(new ColoredPolygon(polygon, Color.BLACK)), paper2.getAllDrawnPolygons());
    }

    @Test
    public void drawPolygonWhiteColor() throws TransparentColorException {
        expectedEx.expect(TransparentColorException.class);
        expectedEx.expectMessage("white");

        paper.changeColor(Color.WHITE);
        paper.drawPolygon(polygon);
    }

    @Test
    public void drawPolygon() throws TransparentColorException {
        paper.drawPolygon(polygon);
        assertColoredCollections(Collections.singletonList(new ColoredPolygon(polygon, Color.BLACK)), paper.getAllDrawnPolygons());
    }

    @Test(expected = TransparentColorException.class)
    public void whiteColorDoesNotDraw() throws TransparentColorException {
        paper.changeColor(Color.WHITE);
        paper.drawPolygon(polygon);
    }

    @Test
    public void changeColor() throws TransparentColorException {
        paper.changeColor(Color.RED);
        paper.drawPolygon(polygon);
        assertColoredCollections(Collections.singletonList(new ColoredPolygon(polygon, Color.RED)), paper.getAllDrawnPolygons());
    }

    @Test
    public void drawSamePolygonTwice() throws TransparentColorException {
        paper.drawPolygon(polygon);
        paper.drawPolygon(polygon);
        assertColoredCollections(Collections.singletonList(new ColoredPolygon(polygon, Color.BLACK)), paper.getAllDrawnPolygons());
    }

    @Test
    public void drawSamePolygonTwiceColorSame() throws TransparentColorException {
        paper.changeColor(Color.YELLOW);
        paper.drawPolygon(polygon);
        paper.changeColor(Color.RED);
        paper.drawPolygon(polygon);
        assertColoredCollections(Collections.singletonList(new ColoredPolygon(polygon, Color.YELLOW)), paper.getAllDrawnPolygons());
    }

    @Test
    public void erasePolygon() throws TransparentColorException {
        paper.drawPolygon(polygon);
        paper.erasePolygon(new ColoredPolygon(polygon, Color.BLACK));
        assertEquals(0, paper.getAllDrawnPolygons().size());
    }

    @Test(expected = EmptyDrawableException.class)
    public void eraseOnEmptyPaper() throws TransparentColorException, EmptyDrawableException {
        paper.eraseAll();
    }

    @Test
    public void eraseAll() throws TransparentColorException, EmptyDrawableException {
        paper.drawPolygon(polygon);
        paper.eraseAll();
        assertEquals(0, paper.getAllDrawnPolygons().size());
    }

    @Test
    public void findPolygonWithVertex() throws TransparentColorException {
        paper.drawPolygon(polygon);
        paper.changeColor(Color.YELLOW);
        paper.drawPolygon(polygon2);

        Set<ColoredPolygon> expected = new HashSet<>();
        expected.add(new ColoredPolygon(polygon, Color.BLACK));
        expected.add(new ColoredPolygon(polygon2, Color.YELLOW));

        assertSets(expected, paper.findPolygonsWithVertex(new Vertex2D(0, 0)));

        assertSets(Collections.singletonList(new ColoredPolygon(polygon, Color.BLACK)),
                paper.findPolygonsWithVertex(new Vertex2D(10, 10)));
    }

    private void assertSets(Collection<?> first, Collection<?> second) {
        assertTrue(first.containsAll(second));
        assertTrue(second.containsAll(first));
    }

    @Test
    public void findPolygonWithNonExistingVertex() {
        assertEquals(0, paper.findPolygonsWithVertex(new Vertex2D(425, -789)).size());
        assertEquals(0, paper.findPolygonsWithVertex(null).size());
    }

    private void assertColoredCollections(Collection<ColoredPolygon> expected, Collection<ColoredPolygon> actual) {
        assertEquals(expected.size(), actual.size());
        ColoredPolygon[] expectedArray = expected.toArray(new ColoredPolygon[expected.size()]);
        ColoredPolygon[] actualArray = actual.toArray(new ColoredPolygon[actual.size()]);
        for (int i = 0; i < expectedArray.length; i++) {
            assertColoredPolygon(expectedArray[i], actualArray[i]);
        }
    }

    private void assertColoredPolygon(ColoredPolygon expected, ColoredPolygon actual) {
        assertEquals(expected.getColor(), actual.getColor());
        assertEquals(expected.getPolygon().getVertices(), actual.getPolygon().getVertices());
    }

    @Test
    public void tryToCreateValidPolygons() {
        try {
            Polygon p1 = paper.tryToCreatePolygon(polygonList1);
            Polygon p2 = paper.tryToCreatePolygon(polygonList2);
            assertNotNull(p1);
            assertNotNull(p2);
        } catch (MissingVerticesException e) {
            fail("Exception " + e + "should not have been thrown.");
        }
    }

    @Test
    public void tryToCreateCorrectPolygon() {
        Polygon p;
        try {
            p = paper.tryToCreatePolygon(polygonList1);
            assertTrue("Method did not create all vertices",
                    polygon.getVertices().containsAll(p.getVertices()));

            p = paper.tryToCreatePolygon(polygonList2);
            assertTrue("Method did not create all vertices",
                    polygon2.getVertices().containsAll(p.getVertices()));
            // ok
        } catch (MissingVerticesException e) {
            fail("Exception " + e + "should not have been thrown.");
        }
    }

    @Test(expected = NullPointerException.class)
    public void tryToCreatePolygonWithNull() throws MissingVerticesException {
        paper.tryToCreatePolygon(null);
    }

    @Test(expected = MissingVerticesException.class)
    public void tryToCreatePolygonWith1Vertex() throws MissingVerticesException {
        List<Vertex2D> list = new ArrayList<>();
        list.add(new Vertex2D(0, 1));
        paper.tryToCreatePolygon(list);
    }

    @Test
    public void tryToCreatePolygonWithTooManyNulls1() {
        List<Vertex2D> list = Arrays.asList(new Vertex2D(0, 0),
                null,
                null,
                null,
                null
        );
        try {
            paper.tryToCreatePolygon(list);
            fail("Method should have thrown an exception, there is not enough vertices in the collection");
        } catch (MissingVerticesException e) {
            assertEquals("MissingVerticesException should have set cause IllegalArgumentException",
                    IllegalArgumentException.class, e.getCause().getClass());
        }
    }


    @Test
    public void tryToCreatePolygonWithTooManyNulls2() {
        List<Vertex2D> list = Arrays.asList(null, null, new Vertex2D(-7, 1), null, new Vertex2D(0, 0));
        try {
            paper.tryToCreatePolygon(list);
            fail("Method should have thrown an exception, there is not enough vertices in the collection");
        } catch (MissingVerticesException e) {
            assertEquals("MissingVerticesException should have set cause IllegalArgumentException",
                    IllegalArgumentException.class, e.getCause().getClass());
        }
    }


    @Test
    public void tryToCreatePolygonWithSomeNulls() {
        List<Vertex2D> list = Arrays.asList(new Vertex2D(0, 0),
                null,
                null,
                new Vertex2D(4, 2),
                new Vertex2D(3, 1),
                null
        );
        try {
            Polygon polygon = paper.tryToCreatePolygon(list);
            assertTrue("Method did not create some vertices",
                    polygon.getVertices().containsAll(filterNulls(list)));
        } catch (MissingVerticesException e) {
            fail("Everything is fine, method should not have thrown an exception");
        }
    }

    private List<Vertex2D> filterNulls(List<Vertex2D> listWithNulls) {
        return listWithNulls.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Test
    public void filterPolygonsAccordingToColor() throws TransparentColorException {
        assertEquals("Nothing has been drawn with orange color",
                0, paper.getPolygonsWithColor(Color.ORANGE).size());
        paper.changeColor(Color.RED);
        paper.drawPolygon(polygon);
        paper.changeColor(Color.BLUE);
        paper.drawPolygon(polygon2);
        paper.changeColor(Color.RED);
        CollectionPolygon polygon3 =
                new CollectionPolygon(new Vertex2D[]{new Vertex2D(0, 0), new Vertex2D(1, 2)});
        paper.drawPolygon(polygon3);

        assertContainsAll(Arrays.asList(polygon, polygon3), paper.getPolygonsWithColor(Color.RED));
        assertContainsAll(Collections.singletonList(polygon2), paper.getPolygonsWithColor(Color.BLUE));
    }

    private void assertContainsAll(List<Polygon> expected, Collection<Polygon> actual) {
        String msg = "Expected: " + expected + System.lineSeparator() + "but got: " + actual;
        assertTrue(msg, expected.containsAll(actual));
        assertTrue(msg, actual.containsAll(expected));
    }

    @Test
    public void tryToDrawCorrectPolygons() throws EmptyDrawableException {
        List<List<Vertex2D>> listOfLists = new ArrayList<>();
        listOfLists.add(new ArrayList<>(polygon.getVertices()));
        listOfLists.add(new ArrayList<>(polygon2.getVertices()));

        paper.tryToDrawPolygons(listOfLists);
        List<Polygon> outList =
                paper.getAllDrawnPolygons().stream().map(ColoredPolygon::getPolygon).collect(Collectors.toList());
        areListsSame(Arrays.asList(polygon2, polygon), outList);
    }

    private void areListsSame(List<Polygon> expected, List<Polygon> actual) {
        // TODO: fix for next year by adding equals & hashCode to CollectionPolygon
        for (Polygon e : expected) {
            boolean containsPolygon = false;
            for (Polygon a : actual) {
                if (e.getVertices().containsAll(a.getVertices())) containsPolygon = true;
            }
            if (!containsPolygon) fail(e + " is not in " + actual);
        }
    }

    @Test
    public void tryToDrawOnlyWrongPolygons() {
        List<List<Vertex2D>> listOfLists = new ArrayList<>();
        listOfLists.add(Arrays.asList(new Vertex2D(0, 1), null, null));
        listOfLists.add(Arrays.asList(null, null, null));
        listOfLists.add(Arrays.asList(null, new Vertex2D(-456, 11), null));
        listOfLists.add(Arrays.asList(null, new Vertex2D(-456, 11)));

        try {
            paper.tryToDrawPolygons(listOfLists);
            fail("Method should have thrown an exception, nothing has been drawn");
        } catch (EmptyDrawableException e) {
            assertEquals("EmptyDrawableException should have set cause MissingVerticesException",
                    MissingVerticesException.class, e.getCause().getClass());
        }
    }

    @Test
    public void tryToDrawWithWhiteColor() {
        List<List<Vertex2D>> listOfLists = new ArrayList<>();
        listOfLists.add(Arrays.asList(new Vertex2D(0, 1), null, new Vertex2D(4, 5), new Vertex2D(2, 7)));
        paper.changeColor(Color.WHITE);

        try {
            paper.tryToDrawPolygons(listOfLists);
            fail("Method should have thrown an exception, nothing has been drawn");
        } catch (EmptyDrawableException e) {
            assertEquals("EmptyDrawableException should have set cause TransparentColorException",
                    TransparentColorException.class, e.getCause().getClass());
        }
    }

    @Test
    public void tryToDrawOneCorrectPolygon() {
        List<Vertex2D> correctList = Arrays.asList(
                new Vertex2D(-45, 11), null, new Vertex2D(26, 18), new Vertex2D(4, 5), null);

        List<List<Vertex2D>> listOfLists = new ArrayList<>();
        listOfLists.add(Arrays.asList(new Vertex2D(0, 1), null, null));
        listOfLists.add(correctList);
        listOfLists.add(Arrays.asList(null, new Vertex2D(-456, 11), null));

        try {
            paper.tryToDrawPolygons(listOfLists);

            List<Polygon> outList = paper.getAllDrawnPolygons().stream()
                    .map(ColoredPolygon::getPolygon)
                    .collect(Collectors.toList());

            areListsSame(Collections.singletonList(
                    new CollectionPolygon(filterNulls(correctList).toArray(new Vertex2D[3]))),
                    outList);
        } catch (EmptyDrawableException e) {
            fail("Method should not have thrown an exception");
        }
    }

}
