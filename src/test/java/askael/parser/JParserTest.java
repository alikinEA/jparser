package askael.parser;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

        Assertions.assertArrayEquals(result , NAME_VALUE.getBytes());
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void parseString_shouldNotReturnStringValueByPropertyName_whenPropertyNotExists(String json) {
        var result = parser.parseString(json.getBytes(), "price".getBytes());

        Assertions.assertNull(result);
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void parseInt_shouldReturnIntValueByPropertyName_whenPropertyExists(String json) {
        var result = parser.parseInt(json.getBytes(), AGE_PR.getBytes());

        Assertions.assertArrayEquals(result , Integer.valueOf(AGE_VALUE).toString().getBytes());
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void parseInt_shouldReturnIntValueByPropertyName_whenPropertyNotExists(String json) {
        var result = parser.parseInt(json.getBytes(), "price".getBytes());

        Assertions.assertNull(result);
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void containsIntValue_shouldReturnTrue_whenPropertyAndValueAreExists(String json) {
        var result = parser.containsInt(json.getBytes(), AGE_PR.getBytes(), Integer.valueOf(AGE_VALUE).toString().getBytes());

        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void containsIntValue_shouldReturnFalse_whenPropertyExistsButValueNotExists(String json) {
        var result = parser.containsInt(json.getBytes(), AGE_PR.getBytes(), Integer.valueOf(21).toString().getBytes());

        Assertions.assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void containsIntValue_shouldReturnTrue_whenPropertyAndValueAreNotExists(String json) {
        var result = parser.containsInt(json.getBytes(), "price".getBytes(), Integer.valueOf(21).toString().getBytes());

        Assertions.assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void containsStringValue_shouldReturnTrue_whenPropertyAndValueAreExists(String json) {
        var result = parser.containsString(json.getBytes(), NAME_PR.getBytes(), NAME_VALUE.getBytes());

        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void containsStringValue_shouldReturnFalse_whenPropertyExistsButValueNotExists(String json) {
        var result = parser.containsString(json.getBytes(), NAME_PR.getBytes(), "Vasya".getBytes());

        Assertions.assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("getJSONs")
    public void containsStringValue_shouldReturnTrue_whenPropertyAndValueAreNotExists(String json) {
        var result = parser.containsString(json.getBytes(), "price".getBytes(), "Vasya".getBytes());

        Assertions.assertFalse(result);
    }


    @Test
    public void multiExecution_shouldWorkCorrect() {
        // WHEN
        var result1 = parser.parseString(JSON_1.getBytes(), NAME_PR.getBytes());
        var result2 = parser.parseString(JSON_2.getBytes(), NAME_PR.getBytes());

        var result3 = parser.parseInt(JSON_1.getBytes(), AGE_PR.getBytes());
        var result4 = parser.parseInt(JSON_2.getBytes(), AGE_PR.getBytes());

        // THEN
        Assertions.assertArrayEquals(result1 , NAME_VALUE.getBytes());
        Assertions.assertArrayEquals(result2 , NAME_VALUE.getBytes());

        Assertions.assertArrayEquals(result3 , Integer.valueOf(AGE_VALUE).toString().getBytes());
        Assertions.assertArrayEquals(result4 , Integer.valueOf(AGE_VALUE).toString().getBytes());
    }

}