package cz.muni.fi.pb162.project.geometry;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Stefan Ockay
 */

public class LabeledPolygon extends SimplePolygon implements Labelable, Sortable, PolygonIO {
    private TreeMap<String, Vertex2D> verticesMap = new TreeMap<>();

    public LabeledPolygon() {
        super(new Vertex2D[0]);
    }

    @Override
    public Vertex2D getVertex(int index) throws IllegalArgumentException {
        if (index < 0) {
            throw new IllegalArgumentException("Negative index.");
        }
        Collection<Vertex2D> values = verticesMap.values();
        index %= values.size();
        for (Vertex2D vertex: values) {
            if (index == 0) {
                return vertex;
            }
            index--;
        }
        return null;
    }

    @Override
    public int getNumVertices() {
        return verticesMap.keySet().size();
    }

    @Override
    public Collection<Vertex2D> getVertices() {
        return Collections.unmodifiableCollection(verticesMap.values());
    }

    @Override
    public void addVertex(String label, Vertex2D vertex) {
        if (label == null) {
            throw new IllegalArgumentException("label");
        }
        if (vertex == null) {
            throw new IllegalArgumentException("vertex");
        }
        verticesMap.put(label, vertex);
    }

    @Override
    public Vertex2D getVertex(String label) {
        Vertex2D vertex = verticesMap.get(label);
        if (vertex == null) {
            throw new NullPointerException("No such label.");
        }
        return vertex;
    }

    @Override
    public Collection<String> getLabels() {
        return Collections.unmodifiableCollection(verticesMap.keySet());
    }

    @Override
    public Collection<String> getLabels(Vertex2D vertex) {
        TreeSet<String> labels = new TreeSet<>();
        Vertex2D v;
        for (String key: verticesMap.keySet()) {
            v = verticesMap.get(key);
            if (v.getX() == vertex.getX() && v.getY() == vertex.getY()) {
                labels.add(key);
            }
        }
        return Collections.unmodifiableCollection(labels);
    }

    @Override
    public Collection<Vertex2D> getSortedVertices() {
        return new TreeSet<>(verticesMap.values());
    }

    @Override
    public Collection<Vertex2D> getSortedVertices(Comparator<Vertex2D> comparator) {
        TreeSet<Vertex2D> sortedVertices = new TreeSet<>(comparator);
        sortedVertices.addAll(verticesMap.values());
        return sortedVertices;
    }

    public Collection<Vertex2D> duplicateVertices() {
        HashSet<Vertex2D> auxSet = new HashSet<>();
        HashSet<Vertex2D> result = new HashSet<>();
        Collection<Vertex2D> values = verticesMap.values();
        for (Vertex2D vertex: values) {
            if (auxSet.contains(vertex)) {
                result.add(vertex);
            } else {
                auxSet.add(vertex);
            }
        }
        return Collections.unmodifiableCollection(result);
    }

    @Override
    public void write(OutputStream os) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(os);
            Vertex2D vertex;
            String line;
            for (String label: verticesMap.keySet()) {
                vertex = verticesMap.get(label);
                line = Double.toString(vertex.getX()) + " " + Double.toString(vertex.getY()) + " " + label;
                line += label + "\n";
                pw.write(line);
            }
        } finally {
            if(pw != null) {
                pw.flush();
            }
        }
    }

    @Override
    public void write(File file) throws IOException {
        write(new FileOutputStream(file));
    }

    @Override
    public void read(InputStream is) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new BufferedReader(new InputStreamReader(is)));
            String line = br.readLine();
            String[] fields;
            String label;
            while(line != null) {
                fields = line.split(" ", 3);
                if (fields.length != 3) {
                    throw new IOException("too few arguments");
                }
                double x = Double.parseDouble(fields[0]);
                double y = Double.parseDouble(fields[1]);
                label = fields[2];
                verticesMap.put(label, new Vertex2D(x, y));
                line = br.readLine();
            }
        } catch (NumberFormatException ex) {
            throw new IOException("number format");
        } finally {
            if(br != null) {
                br.close();
            }
        }
    }

    @Override
    public void read(File file) throws IOException {
        read(new FileInputStream(file));
    }

    public void binaryWrite(OutputStream os) throws IOException {
        try {
            Vertex2D vertex;
            String line;
            byte[] lineByteArray;
            for (String label: verticesMap.keySet()) {
                vertex = verticesMap.get(label);
                line = Double.toString(vertex.getX()) + " " + Double.toString(vertex.getY()) + " " + label;
                line += System.lineSeparator();
                lineByteArray = line.getBytes();
                os.write(lineByteArray);
            }
        } finally {
            if (os != null) {
                os.flush();
            }
        }
    }
}
