import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import expression.Expression;

/**
 * Klasse mit statischen Methoden für JUnit-Tests. Die Klasse beinhaltet
 * unter anderem Methoden zum Vergleichen von Dateien und zum Erstellen
 * und Anzeigen von PNG-Dateien aus Graphviz-Dateien.
 * 
 * @author aan, avh, mhe, mre, tti
 */
public final class TestToolkit {

    /**
     * Bestimmt, ob die erzeugten Graphvizbeschreibungen in eine Grafik
     * konvertiert und anschließend angezeigt werden sollen.
     * 
     * Sollte auf false gesetzt werden, falls die Programme "dot" (graphviz)
     * und "display" nicht installiert sind.
     */
    private static final boolean CONVERT_AND_DISPLAY_GRAPH = false;

    /**
     * Dateiendung der anzuzeigenden Dateien
     */
    private static final String PIC_EXTENSION = ".png";

    /**
     * Dateiendung der zu vergleichenden Dateien
     */
    private static final String GRAPH_EXTENSION = ".dot";

    /**
     * Verzeichnis, in das die Dateien geschrieben werden sollen
     */
    private static final String OUTPUT_DIR = "./test/results/";

    /**
     * Verzeichnis mit den Vergleichsdateien
     */
    private static final String EXPECTED_DIR = "./test/expected/";

    /**
     * Privater Konstruktor, um das Erzeugen eines default-Konstruktors zu
     * verhindern. Diese Klasse soll nicht instanzierbar sein, da sie nur
     * statische Hilfsfunktionen enthält, die zum Testen benötigt werden.
     */
    private TestToolkit() {
        throw new AssertionError();
    }

    /**
     * Wandelt die übergebene dot-Datei in eine png-Datei um.
     * 
     * @param dotFilename Dateiname der .dot Datei
     * @param pngFilename Dateiname der .png Datei
     * @return true, wenn alles geklappt hat, ansonsten false
     */
    public static boolean graphvizDotToPng(String dotFilename,
            String pngFilename) {
        final Runtime runtime = Runtime.getRuntime();
        Process proc;
        try {
            proc = runtime.exec(new String[] { "dot", "-Tpng",
                    "-o" + pngFilename, dotFilename });
            final int exitValue = proc.waitFor();
            return exitValue == 0;
        } catch (IOException | InterruptedException e) {
            System.err.println("Fehler beim Aufruf von dot.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Schreibt die Graphviz-Repräsentation des übergebenen Baumes in die Datei
     * filename.
     * 
     * @param tree der Baum
     * @param filename (Dateipfad +) Dateiname
     */
    public static void writeGraphvizFile(Expression tree, String filename)
            throws IOException {
        FileWriter f = null;
        try {
            f = new FileWriter(filename);
            f.write(tree.toGraphviz());
        } finally {
            if (f != null) {
                f.close();
            }
        }
    }

    /**
     * Schreibt die Graphviz-Repräsentation des übergebenen Baumes im
     * entsprechenden Verzeichnis in die Datei mit dem übergebenen Namen und
     * überprüft, ob sie der erwarteten Graphviz-Datei entspricht.
     * 
     * @param tree der zu testende Baum
     * @param filename Dateiname der Ziel- und der Vergleichsdatei
     *            im Ordner EXPECTED_DIR (jeweils ohne Endung)
     */
    public static void assertDotEquals(Expression tree, String filename)
            throws IOException {
        assertDotEquals("Die Datei sieht nicht wie erwartet aus!\n", tree,
                filename);
    }

    /**
     * Schreibt die Graphviz-Repräsentation des übergebenen Baumes im
     * entsprechenden Verzeichnis in die Datei mit dem übergebenen Namen und
     * überprüft, ob sie der erwarteten Graphviz-Datei entspricht.
     * 
     * @param message zusätzliche Beschreibung der potentiellen Fehlermeldung
     * @param tree der zu testende Baum
     * @param filename Dateiname der Ziel- und der Vergleichsdatei
     *            im Ordner EXPECTED_DIR (jeweils ohne Endung)
     */
    public static void assertDotEquals(String message, Expression tree,
            String filename) throws IOException {

        // Ausgabeverzeichnis anlegen, falls noch nicht vorhanden
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                System.err.println("Das Verzeichnis " + OUTPUT_DIR
                        + " konnte nicht erstellt werden!");
            }
        }

        final String outFilename = OUTPUT_DIR + filename + ".out";
        final String expFilename = EXPECTED_DIR + filename + ".exp";

        writeGraphvizFile(tree, outFilename + GRAPH_EXTENSION);

        assertTrue(
                message + " Erwartet:" + expFilename + GRAPH_EXTENSION + "\n"
                        + " Ergebnis:" + outFilename + GRAPH_EXTENSION,
                dotFilesAreEqual(expFilename, outFilename));
    }

    /**
     * Ermittelt, ob der Inhalt der Dot-Dateien fileName1 und fileName2
     * identisch ist. Zeigt die entsprechenden Graphen an, wenn
     * CONVERT_AND_DISPLAY_GRAPH auf true steht.
     * 
     * @param fileName1 erste Datei ohne Endung.
     * @param fileName2 zweite Datei ohne Endung.
     * 
     * @return boolscher Wert, der angibt, ob der Inhalt der Dateien fileName1
     *         und fileName2 identisch ist.
     * @throws IOException Dateifehler
     * 
     * @pre Dateiname duerfen keine Leerzeichen enthalten.
     */
    public static boolean dotFilesAreEqual(String fileName1, String fileName2) throws IOException {
        final String fileName1Dot = fileName1 + GRAPH_EXTENSION;
        final String fileName2Dot = fileName2 + GRAPH_EXTENSION;
        final String fileName1Png = fileName1 + PIC_EXTENSION;
        final String fileName2Png = fileName2 + PIC_EXTENSION;

        if (filesAreEqual(fileName1Dot, fileName2Dot)) {
            return true;
        } else if (CONVERT_AND_DISPLAY_GRAPH) {
            // .dot Dateien in .png Dateien rendern
            graphvizDotToPng(fileName1Dot, fileName1Png);
            graphvizDotToPng(fileName2Dot, fileName2Png);

            // beide Bilder mit "display" getrennt anzeigen:
            try {
                Runtime runtime = Runtime.getRuntime();
                runtime.exec(new String[] { "display", fileName1Png });
                runtime.exec(new String[] { "display", fileName2Png });
            } catch (IOException e) {
                System.err.println("Fehler beim der Ausführung von display.");
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    
    /**
     * Ermittelt, ob der Inhalt der Dateien fileName1 und fileName2 identisch ist. Funktioniert nur
     * für Dateien bis zu einer Größe von 2GB.
     * 
     * @param fileName1 erste Datei.
     * @param fileName2 zweite Datei.
     * 
     * @return boolscher Wert, der angibt, ob der Inhalt der Dateien fileName1 und fileName2
     *         identisch ist.
     * 
     * @throws IOException Dateifehler
     */
    public static boolean filesAreEqual(String fileName1, String fileName2)
            throws IOException {

        Path f1 = Paths.get(fileName1);
        Path f2 = Paths.get(fileName2);

        long size = Files.size(f1);
        if (size != Files.size(f2)) {
            return false;
        }

        return Arrays.equals(Files.readAllBytes(f1), Files.readAllBytes(f2));
    }

}
