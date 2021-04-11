package askael.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

public class PerformanceTest {

    private static final JParser jParser = new JParser();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String NAME_PR = "name";
    private static final byte[] NAME_PR_BYTES = NAME_PR.getBytes();

    private static final String NAME_VALUE = "Ivan";
    private static final byte[] NAME_VALUE_BYTES = NAME_VALUE.getBytes();

    private static final String AGE_PR = "age";
    private static final int AGE_VALUE = 20;

    private static final String JSON_1 = "{\"" + NAME_PR + "\":\"" + NAME_VALUE + "\"," +
            "\"" + AGE_PR + "\":" + AGE_VALUE + "}";

    private static final byte[] JSON_1_BYTES = JSON_1.getBytes();

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void jparser_getValueByName() {
        String value = new String(jParser.parseString(JSON_1_BYTES, NAME_PR_BYTES));
        assert NAME_VALUE.equals(value);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void jackson_getValueByName() throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(JSON_1);
        String value = jsonNode.get(NAME_PR).asText();
        assert NAME_VALUE.equals(value);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void jparser_containsByParamAndValue() {
        boolean value = jParser.containsString(JSON_1_BYTES, NAME_PR_BYTES, NAME_VALUE_BYTES);
        assert Boolean.TRUE.equals(value);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 1, warmups = 1)
    public void jackson_containsByParamAndValue() throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(JSON_1);
        String value = jsonNode.get(NAME_PR).asText();
        assert NAME_VALUE.equals(value);
    }
}
