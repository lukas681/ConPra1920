import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import problemA.A;
import problemB.B;
import problemB.B_dimi;
import problemC.C;
import problemD.D;
import problemE.E;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Tests {

    private static final String[] EMPTY_ARRAY = new String[0];

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void problemA() throws IOException {
        var inputs = getTestInput("A");
        var outputs = getTestOutput("A");
        for (int i=0; i<inputs.length; i++) {
            var in = inputs[i];
            var expectedOut = outputs[i];
            var mockIn = new ByteArrayInputStream(in.getBytes());
            System.setIn(mockIn);
            A.main(EMPTY_ARRAY);
            System.setIn(System.in);
            assertEquals(expectedOut, outContent.toString());
            outContent.reset();
        }
    }

    String generateInputProblemB() {
        var rnd = new Random();
        var sb = new StringBuilder();
        var casesCount = rnd.nextInt(19) + 1;
        sb.append(casesCount).append("\n");
        for (int t=1; t<=casesCount; t++) {
            var n = rnd.nextInt(9) + 1;
            var m = rnd.nextInt(9) + 1;
            var b = rnd.nextInt(9) + 1;
            sb.append(n).append(" ").append(m).append(" ").append(b).append("\n");
            for (int i=0; i<n; i++) sb.append(rnd.nextInt(m) + 1).append(" ").append(rnd.nextInt(b) + 1).append("\n");
            sb.append("\n");
        }

        return sb.toString();
    }

    @Test
    void autogenProblemB() throws IOException {
        for (int i=0; i<500000; i++) {
            var in = generateInputProblemB();
            var mockIn = new ByteArrayInputStream(in.getBytes());
            var bt = new ByteArrayOutputStream();
            var expectedOut = new PrintStream(bt);
            System.setIn(mockIn);
            System.setOut(expectedOut);
            B_dimi.main(EMPTY_ARRAY);
            var abt = new ByteArrayOutputStream();
            var actualOut = new PrintStream(abt);
            mockIn = new ByteArrayInputStream(in.getBytes());
            System.setIn(mockIn);
            System.setOut(actualOut);
            B.main(EMPTY_ARRAY);
            if (!bt.toString(Charset.defaultCharset()).equals(abt.toString(Charset.defaultCharset()))) {
                originalOut.println("Input:\n" + in);
            }
            assertEquals(bt.toString(Charset.defaultCharset()), abt.toString(Charset.defaultCharset()));
        }
    }

    @Test
    void problemB() throws IOException {
        var inputs = getTestInput("B");
        var outputs = getTestOutput("B");
        for (int i=0; i<inputs.length; i++) {
            var in = inputs[i];
            var expectedOut = outputs[i];
            var mockIn = new ByteArrayInputStream(in.getBytes());
            System.setIn(mockIn);
            B.main(EMPTY_ARRAY);
            System.setIn(System.in);
            assertEquals(expectedOut, outContent.toString());
            outContent.reset();
        }
    }

    @Test
    void problemC() throws IOException {
        var inputs = getTestInput("C");
        var outputs = getTestOutput("C");
        for (int i=0; i<inputs.length; i++) {
            var in = inputs[i];
            var expectedOut = outputs[i];
            var mockIn = new ByteArrayInputStream(in.getBytes());
            System.setIn(mockIn);
            C.main(EMPTY_ARRAY);
            System.setIn(System.in);
            assertEquals(expectedOut, outContent.toString());
            outContent.reset();
        }
    }

    @Test
    void problemD() throws IOException {
        var inputs = getTestInput("D");
        var outputs = getTestOutput("D");
        for (int i=0; i<inputs.length; i++) {
            var in = inputs[i];
            var expectedOut = outputs[i];
            var mockIn = new ByteArrayInputStream(in.getBytes());
            System.setIn(mockIn);
            D.main(EMPTY_ARRAY);
            System.setIn(System.in);
            assertEquals(expectedOut, outContent.toString());
            outContent.reset();
        }
    }

    @Test
    void problemE() throws IOException {
        var inputs = getTestInput("E");
        var outputs = getTestOutput("E");
        for (int i=0; i<inputs.length; i++) {
            var in = inputs[i];
            var expectedOut = outputs[i];
            var mockIn = new ByteArrayInputStream(in.getBytes());
            System.setIn(mockIn);
            E.main(EMPTY_ARRAY);
            System.setIn(System.in);
            assertEquals(expectedOut, outContent.toString());
            outContent.reset();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    String[] getTestInput(String problem) {
        ClassLoader classLoader = getClass().getClassLoader();
        File folder = new File(Objects.requireNonNull(classLoader.getResource("problem" + problem)).getFile());
        var inFiles = folder.listFiles((dir, name) -> name.endsWith(".in"));
        assert inFiles != null;
        Arrays.sort(inFiles);
        return Arrays.stream(inFiles).map(File::getPath).map(Tests::readFile).toArray(String[]::new);
    }

    String[] getTestOutput(String problem) {
        ClassLoader classLoader = getClass().getClassLoader();
        File folder = new File(Objects.requireNonNull(classLoader.getResource("problem" + problem)).getFile());
        var outFiles = folder.listFiles((dir, name) -> name.endsWith(".ans"));
        assert outFiles != null;
        Arrays.sort(outFiles);
        return Arrays.stream(outFiles).map(File::getPath).map(Tests::readFile).toArray(String[]::new);
    }

    static String readFile(String path) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, Charset.defaultCharset());
    }

}
