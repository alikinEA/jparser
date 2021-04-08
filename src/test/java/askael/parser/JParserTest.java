package askael.parser;


import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class JParserTest {

    private JParser parser;
    private static final String NAME_PR = "name";
    private static final String NAME_VALUE = "Ivan";

    private static final String AGE_PR = "age";
    private static final int AGE_VALUE = 20;

    private static final String JSON_1 = "{\"" + NAME_PR + "\":\"" + NAME_VALUE + "\"," +
        "\"" + AGE_PR + "\":" + AGE_VALUE + "}";
    private static final String JSON_2 = "{\"" + AGE_PR + "\":" + AGE_VALUE + "," +
            "\"" + NAME_PR + "\":\"" + NAME_VALUE + "\"}";


    private static Stream<String> getJSONs() {
        return Stream.of(JSON_1, JSON_2);
    }

    @BeforeEach
    public void setUp() {
        parser = new JParser();
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void parseString_shouldReturnStringValueByPropertyName_whenPropertyExists(String json) {
        var result = parser.parseString(json.getBytes(), NAME_PR.getBytes());

        Assert.assertArrayEquals(result , NAME_VALUE.getBytes());
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void parseString_shouldNotReturnStringValueByPropertyName_whenPropertyNotExists(String json) {
        var result = parser.parseString(json.getBytes(), "price".getBytes());

        Assert.assertNull(result);
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void parseInt_shouldReturnIntValueByPropertyName_whenPropertyExists(String json) {
        var result = parser.parseInt(json.getBytes(), AGE_PR.getBytes());

        Assert.assertArrayEquals(result , Integer.valueOf(AGE_VALUE).toString().getBytes());
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void parseInt_shouldReturnIntValueByPropertyName_whenPropertyNotExists(String json) {
        var result = parser.parseInt(json.getBytes(), "price".getBytes());

        Assert.assertNull(result);
    }

}