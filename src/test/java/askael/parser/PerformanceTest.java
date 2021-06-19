package askael.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PerformanceTest {

    private static final JParser jParser = new JParser();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String JSON = readFile();
    private static final byte[] JSON_BYTES = JSON.getBytes();

    private static final String STRING_PR_NAME = "color";
    private static final byte[] STRING_PR_NAME_BYTES = STRING_PR_NAME.getBytes();

    private static final String STRING_VALUE = "gold";
    private static final byte[] STRING_VALUE_BYTES = STRING_VALUE.getBytes();

    private static final String STR_ARR_PR_NAME = "strArray";
    private static final byte[] STR_ARR_PR_NAME_BYTES = STR_ARR_PR_NAME.getBytes();

    private static final String STR_ARRAY_VALUE_2 = "str2";

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    private static String readFile() {
        try {
            return String.join("", Files.readAllLines(Path.of("src/test/java/resources/test.json")));
        } catch (IOException e) {
            return "";
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void jparser_getValueByName() {
        String value = new String(jParser.parseString(JSON_BYTES, STRING_PR_NAME_BYTES));
        assert STRING_VALUE.equals(value);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void jackson_getValueByName() throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(JSON);
        String value = jsonNode.get(STRING_PR_NAME).asText();
        assert STRING_VALUE.equals(value);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void jparser_containsByParamAndValue() {
        boolean value = jParser.containsString(JSON_BYTES, STRING_PR_NAME_BYTES, STRING_VALUE_BYTES);
        assert Boolean.TRUE.equals(value);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void jackson_containsByParamAndValue() throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(JSON);
        String value = jsonNode.get(STRING_PR_NAME).asText();
        assert STRING_VALUE.equals(value);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void jparser_getArrayStrValue() {
        String value = new String(jParser.parseStrArray(JSON_BYTES, STR_ARR_PR_NAME_BYTES, 1));
        assert STR_ARRAY_VALUE_2.equals(value);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void jackson_getArrayStrValue() throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(JSON);
        String value = jsonNode.get(STR_ARR_PR_NAME).get(1).asText();
        assert STR_ARRAY_VALUE_2.equals(value);
    }
}
