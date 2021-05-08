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

    private final String NUMBER_PR_NAME = "number";
    private final Integer NUMBER_VALUE = 123;

    private final String UNDEFINED_PR_NAME = "undefined";
    private final String UNDEFINED_NUMBER_VALUE = "233";
    private final String UNDEFINED_STRING_VALUE = "undefined";

    @BeforeEach
    public void setUp() throws IOException {
        parser = new JParser();
        JSON = String.join("", Files.readAllLines(Path.of("src/test/java/resources/test.json")));
    }

    @Test
    public void parseString_shouldReturnStringValueByPropertyName_whenPropertyExists() {
        var result = parser.parseString(JSON.getBytes(), STRING_PR_NAME.getBytes());

        Assertions.assertArrayEquals(result , STRING_VALUE.getBytes());
    }

    @Test
    public void parseString_shouldNotReturnStringValueByPropertyName_whenPropertyNotExists() {
        var result = parser.parseString(JSON.getBytes(), UNDEFINED_PR_NAME.getBytes());

        Assertions.assertNull(result);
    }

    @Test
    public void parseInt_shouldReturnIntValueByPropertyName_whenPropertyExists() {
        var result = parser.parseInt(JSON.getBytes(), NUMBER_PR_NAME.getBytes());

        Assertions.assertArrayEquals(result , NUMBER_VALUE.toString().getBytes());
    }

    @Test
    public void parseInt_shouldReturnIntValueByPropertyName_whenPropertyNotExists() {
        var result = parser.parseInt(JSON.getBytes(), UNDEFINED_PR_NAME.getBytes());

        Assertions.assertNull(result);
    }

    @Test
    public void containsIntValue_shouldReturnTrue_whenPropertyAndValueAreExists() {
        var result = parser.containsInt(JSON.getBytes(), NUMBER_PR_NAME.getBytes(), NUMBER_VALUE.toString().getBytes());

        Assertions.assertTrue(result);
    }

    @Test
    public void containsIntValue_shouldReturnFalse_whenPropertyExistsButValueNotExists() {
        var result = parser.containsInt(JSON.getBytes(), NUMBER_PR_NAME.getBytes(), Integer.valueOf(UNDEFINED_NUMBER_VALUE).toString().getBytes());

        Assertions.assertFalse(result);
    }

    @Test
    public void containsIntValue_shouldReturnTrue_whenPropertyAndValueAreNotExists() {
        var result = parser.containsInt(JSON.getBytes(), UNDEFINED_PR_NAME.getBytes(), Integer.valueOf(UNDEFINED_NUMBER_VALUE).toString().getBytes());

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
        var result = parser.containsString(JSON.getBytes(), UNDEFINED_STRING_VALUE.getBytes(), UNDEFINED_STRING_VALUE.getBytes());

        Assertions.assertFalse(result);
    }


    @Test
    public void multiExecution_shouldWorkCorrect() {
        // WHEN
        var result1 = parser.parseString(JSON.getBytes(), STRING_PR_NAME.getBytes());
        var result2 = parser.parseString(JSON.getBytes(), STRING_PR_NAME.getBytes());

        // THEN
        Assertions.assertArrayEquals(result1 , STRING_VALUE.getBytes());
        Assertions.assertArrayEquals(result2 , STRING_VALUE.getBytes());
    }

}