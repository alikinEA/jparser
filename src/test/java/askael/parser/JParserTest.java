package askael.parser;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JParserTest {

    private JParser parser;
    private static String JSON;

    private final String STRING_PR_NAME = "string";
    private final String STRING_VALUE = "Hello World";

    private final String INT_PR_NAME = "number";
    private final Integer INT_VALUE = 123;

    private final String INT_ARR_PR_NAME = "intArray";
    private final Integer INT_ARRAY_VALUE_1 = 1;
    private final Integer INT_ARRAY_VALUE_2 = 2;
    private final Integer INT_ARRAY_VALUE_3 = 3;

    private final String STR_ARR_PR_NAME = "strArray";
    private final String STR_ARRAY_VALUE_1 = "str1";
    private final String STR_ARRAY_VALUE_2 = "str2";
    private final String STR_ARRAY_VALUE_3 = "str3";

    private final String BOOLEAN_PR_NAME_TRUE = "booleanTrue";
    private final String BOOLEAN_PR_NAME_FALSE = "booleanFalse";

    private final String UNDEFINED_PR_NAME = "undefined";
    private final String UNDEFINED_INT_VALUE = "233";
    private final String UNDEFINED_STRING_VALUE = "undefined";

    @BeforeEach
    public void setUp() throws IOException {
        parser = new JParser();
        JSON = String.join("", Files.readAllLines(Path.of("src/test/java/resources/test.json")));
    }

    @Test
    public void parseString_shouldReturnStringValueByPropertyName_whenPropertyExists() {
        var result = parser.parseString(JSON.getBytes(), STRING_PR_NAME.getBytes());

        Assertions.assertArrayEquals(STRING_VALUE.getBytes(), result);
    }

    @Test
    public void parseString_shouldNotReturnStringValueByPropertyName_whenPropertyNotExists() {
        var result = parser.parseString(JSON.getBytes(), UNDEFINED_PR_NAME.getBytes());

        Assertions.assertNull(result);
    }

    @Test
    public void parseInt_shouldReturnIntValueByPropertyName_whenPropertyExists() {
        var result = parser.parseInt(JSON.getBytes(), INT_PR_NAME.getBytes());

        Assertions.assertArrayEquals(INT_VALUE.toString().getBytes(), result);
    }

    @Test
    public void parseInt_shouldReturnIntValueByPropertyName_whenPropertyNotExists() {
        var result = parser.parseInt(JSON.getBytes(), UNDEFINED_PR_NAME.getBytes());

        Assertions.assertNull(result);
    }

    @Test
    public void containsIntValue_shouldReturnTrue_whenPropertyAndValueAreExists() {
        var result = parser.containsInt(JSON.getBytes(), INT_PR_NAME.getBytes(), INT_VALUE.toString().getBytes());

        Assertions.assertTrue(result);
    }

    @Test
    public void containsIntValue_shouldReturnFalse_whenPropertyExistsButValueNotExists() {
        var result = parser.containsInt(JSON.getBytes(), INT_PR_NAME.getBytes(), Integer.valueOf(UNDEFINED_INT_VALUE).toString().getBytes());

        Assertions.assertFalse(result);
    }

    @Test
    public void containsIntValue_shouldReturnTrue_whenPropertyAndValueAreNotExists() {
        var result = parser.containsInt(JSON.getBytes(), UNDEFINED_PR_NAME.getBytes(), Integer.valueOf(UNDEFINED_INT_VALUE).toString().getBytes());

        Assertions.assertFalse(result);
    }

    @Test
    public void containsStringValue_shouldReturnTrue_whenPropertyAndValueAreExists() {
        var result = parser.containsString(JSON.getBytes(), STRING_PR_NAME.getBytes(), STRING_VALUE.getBytes());

        Assertions.assertTrue(result);
    }

    @Test
    public void containsStringValue_shouldReturnFalse_whenPropertyExistsButValueNotExists() {
        var result = parser.containsString(JSON.getBytes(), STRING_PR_NAME.getBytes(), UNDEFINED_STRING_VALUE.getBytes());

        Assertions.assertFalse(result);
    }

    @Test
    public void containsStringValue_shouldReturnTrue_whenPropertyAndValueAreNotExists() {
        var result = parser.containsString(JSON.getBytes(), UNDEFINED_PR_NAME.getBytes(), UNDEFINED_STRING_VALUE.getBytes());

        Assertions.assertFalse(result);
    }

    @Test
    public void parseBoolean_shouldReturnTrue_whenPropertyAndValueExists() {
        var result = parser.parseBoolean(JSON.getBytes(), BOOLEAN_PR_NAME_TRUE.getBytes());

        Assertions.assertTrue(result);
    }

    @Test
    public void parseBoolean_shouldReturnFalse_whenPropertyAndValueExists() {
        var result = parser.parseBoolean(JSON.getBytes(), BOOLEAN_PR_NAME_FALSE.getBytes());

        Assertions.assertFalse(result);
    }

    @Test
    public void parseBoolean_shouldReturnNull_whenPropertyNotExists() {
        var result = parser.parseBoolean(JSON.getBytes(), UNDEFINED_PR_NAME.getBytes());

        Assertions.assertNull(result);
    }

    @Test
    public void parseIntArray_shouldReturnFirstValue_whenIndexExists() {
        var result = parser.parseIntArray(JSON.getBytes(), INT_ARR_PR_NAME.getBytes(), 0);

        Assertions.assertArrayEquals(INT_ARRAY_VALUE_1.toString().getBytes(), result);
    }

    @Test
    public void parseIntArray_shouldReturnSecondValue_whenIndexExists() {
        var result = parser.parseIntArray(JSON.getBytes(), INT_ARR_PR_NAME.getBytes(), 1);

        Assertions.assertArrayEquals(INT_ARRAY_VALUE_2.toString().getBytes(), result);
    }

    @Test
    public void parseIntArray_shouldReturnLastValue_whenIndexExists() {
        var result = parser.parseIntArray(JSON.getBytes(), INT_ARR_PR_NAME.getBytes(), 2);

        Assertions.assertArrayEquals(INT_ARRAY_VALUE_3.toString().getBytes(), result);
    }

    @Test
    public void parseIntArray_shouldReturnNullValue_whenIndexNotExists() {
        var result = parser.parseIntArray(JSON.getBytes(), INT_ARR_PR_NAME.getBytes(), 4);

        Assertions.assertNull(result);
    }

    @Test
    public void parseStrArray_shouldReturnFirstValue_whenIndexExists() {
        var result = parser.parseStrArray(JSON.getBytes(), STR_ARR_PR_NAME.getBytes(), 0);

        Assertions.assertArrayEquals(STR_ARRAY_VALUE_1.getBytes(), result);
    }

    @Test
    public void parseStrArray_shouldReturnSecondValue_whenIndexExists() {
        var result = parser.parseStrArray(JSON.getBytes(), STR_ARR_PR_NAME.getBytes(), 1);

        Assertions.assertArrayEquals(STR_ARRAY_VALUE_2.getBytes(), result);
    }

    @Test
    public void parseStrArray_shouldReturnLastValue_whenIndexExists() {
        var result = parser.parseStrArray(JSON.getBytes(), STR_ARR_PR_NAME.getBytes(), 2);

        Assertions.assertArrayEquals(STR_ARRAY_VALUE_3.getBytes(), result);
    }

    @Test
    public void parseStrArray_shouldReturnNullValue_whenIndexNotExists() {
        var result = parser.parseStrArray(JSON.getBytes(), STR_ARR_PR_NAME.getBytes(), 4);

        Assertions.assertNull(result);
    }


    @Test
    public void multiExecution_shouldWorkCorrect() {
        // WHEN
        var result1 = parser.parseString(JSON.getBytes(), STRING_PR_NAME.getBytes());
        var result2 = parser.parseString(JSON.getBytes(), STRING_PR_NAME.getBytes());

        // THEN
        Assertions.assertArrayEquals(STRING_VALUE.getBytes(), result1);
        Assertions.assertArrayEquals(STRING_VALUE.getBytes(), result2);
    }

}