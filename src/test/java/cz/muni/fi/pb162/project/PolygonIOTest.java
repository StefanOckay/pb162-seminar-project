package cz.muni.fi.pb162.project;

import cz.muni.fi.pb162.project.geometry.LabeledPolygon;
import cz.muni.fi.pb162.project.geometry.Vertex2D;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Marek Sabo
 */
public class PolygonIOTest {

    private static final String POLYGON_OK_TXT = "polygon-ok.txt";
    private static final String POLYGON_ERROR_TXT = "polygon-error.txt";
    private static final String POLYGON_OUT_TXT = "polygon-out.txt";
    private static final String POLYGON_BIN = "polygon.bin";

    private LabeledPolygon filledPolygon;
    private LabeledPolygon emptyPolygon;

    @Before
    public void setUp() throws Exception {
        emptyPolygon = new LabeledPolygon();
        filledPolygon = new LabeledPolygon();

        filledPolygon.addVertex("a", new Vertex2D(-100, 0));
        filledPolygon.addVertex("b", new Vertex2D(0, -100));
        filledPolygon.addVertex("c", new Vertex2D(0, 100));
        filledPolygon.addVertex("d", new Vertex2D(-100, 200));
        filledPolygon.addVertex("e", new Vertex2D(-100, 200));
    }

    @Test
    public void readCorrectFileDoesNotThrowException() {
        Exception exception = null;
        try {
            emptyPolygon.read(new File(POLYGON_OK_TXT));
        } catch (Exception ex) {
            exception = ex;
        }
        assertNull(exception);
        exception = null;
        try {
            filledPolygon.read(new File(POLYGON_OK_TXT));
        } catch (Exception ex) {
            exception = ex;
        }
        assertNull(exception);
    }

    @Test
    public void readCorrectStreamDoesNotThrowException() {
        Exception exception = null;
        try {
            emptyPolygon.read(new FileInputStream(POLYGON_OK_TXT));
        } catch (Exception ex) {
            exception = ex;
        }
        assertNull(exception);
        exception = null;
        try {
            filledPolygon.read(new FileInputStream(POLYGON_OK_TXT));
        } catch (Exception ex) {
            exception = ex;
        }
        assertNull(exception);
    }


    @Test
    public void readInvalidFileThrowsException() {
        Exception exception = null;
        try {
            emptyPolygon.read(new File(POLYGON_ERROR_TXT));
        } catch (Exception ex) {
            exception = ex;
        }
        assertTrue(exception instanceof IOException);
    }

    @Test
    public void readInvalidStreamThrowsException() {
        Exception exception = null;
        try {
            emptyPolygon.read(new FileInputStream(POLYGON_ERROR_TXT));
        } catch (Exception ex) {
            exception = ex;
        }
        assertTrue(exception instanceof IOException);
    }

    @Test
    public void readInvalidStreamWrongCoordinate() throws UnsupportedEncodingException {
        // given
        String errorString = "1 2a XXX";
        // when
        InputStream stream = new ByteArrayInputStream(errorString.getBytes(StandardCharsets.UTF_8.name()));
        // then
        Exception exception = null;
        try {
            emptyPolygon.read(stream);
        } catch (Exception ex) {
            exception = ex;
        }
        assertTrue(exception instanceof IOException);
    }

    @Test
    public void readInvalidStreamMissingParameter() throws UnsupportedEncodingException {
        // given
        String errorString = "1 2";
        // when
        InputStream stream = new ByteArrayInputStream(errorString.getBytes(StandardCharsets.UTF_8.name()));
        // then
        Exception exception = null;
        try {
            emptyPolygon.read(stream);
        } catch (Exception ex) {
            exception = ex;
        }
        assertTrue(exception instanceof IOException);
    }

    @Test
    public void readInvalidStreamInvalidNumberFormat() throws UnsupportedEncodingException {
        // given
        String errorString = "1,1 2,1 name";
        // when
        InputStream stream = new ByteArrayInputStream(errorString.getBytes(StandardCharsets.UTF_8.name()));
        // then
        Exception exception = null;
        try {
            emptyPolygon.read(stream);
        } catch (Exception ex) {
            exception = ex;
        }
        assertTrue(exception instanceof IOException);
    }

    @Test
    public void readValidStream() throws UnsupportedEncodingException {
        // given
        String errorString = "1 2 name with spaces";
        // when
        InputStream stream = new ByteArrayInputStream(errorString.getBytes(StandardCharsets.UTF_8.name()));
        // then
        Exception exception = null;
        try {
            emptyPolygon.read(stream);
        } catch (Exception ex) {
            exception = ex;
        }
        assertNull(exception);
    }

    @Test
    public void readCorrectFile() throws IOException {
        // when
        emptyPolygon.read(new FileInputStream(POLYGON_OK_TXT));
        // then
        Collection<Vertex2D> vertices = emptyPolygon.getVertices();
        Iterator it = vertices.iterator();
        assertEquals(new Vertex2D(-100, 0), it.next());
        assertEquals(new Vertex2D(0, -100), it.next());
        assertEquals(new Vertex2D(0, 100), it.next());
        assertEquals(new Vertex2D(-100, 200), it.next());
        assertEquals(new Vertex2D(-100, 200), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void writeAndReadData() throws IOException {
        // when
        filledPolygon.write(new File(POLYGON_OUT_TXT));
        emptyPolygon.read(new FileInputStream(POLYGON_OUT_TXT));
        // then
        Collection<Vertex2D> vertices = emptyPolygon.getVertices();
        Iterator it = vertices.iterator();
        assertEquals(new Vertex2D(-100, 0), it.next());
        assertEquals(new Vertex2D(0, -100), it.next());
        assertEquals(new Vertex2D(0, 100), it.next());
        assertEquals(new Vertex2D(-100, 200), it.next());
        assertEquals(new Vertex2D(-100, 200), it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void binaryWrite() throws IOException {
        // when
        filledPolygon.binaryWrite(new FileOutputStream(POLYGON_BIN));
        // then
        try (BufferedReader br = new BufferedReader(new FileReader(POLYGON_BIN))) {
            String output = br.lines().collect(Collectors.joining(System.lineSeparator()));
            String s = "-100.0 0.0 a" + System.lineSeparator() +
                    "0.0 -100.0 b" + System.lineSeparator() +
                    "0.0 100.0 c" + System.lineSeparator() +
                    "-100.0 200.0 d" + System.lineSeparator() +
                    "-100.0 200.0 e";
            assertEquals(s, output);
        }
    }

}
