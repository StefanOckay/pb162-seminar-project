package cz.muni.fi.pb162.project.geometry;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Input/output methods
 *
 * @author Radek Oslejsek
 */
public interface PolygonIO {


    /**
     * Write polygon data into output stream.
     *
     * @param os output stream
     * @throws IOException on write error
     */
    void write(OutputStream os) throws IOException;

    /**
     * Write polygon data into file.
     *
     * @param file ouput file
     * @throws IOException on write error
     */
    void write(File file) throws IOException;

    /**
     * Read polygon data from input stream.
     * @param is input stream
     * @throws IOException on read error
     */
    void read(InputStream is) throws IOException;

    /**
     * Read polygon data from file.
     * @param file input file
     * @throws IOException on read error
     */
    void read(File file) throws IOException;
}
