package askael.parser;

import java.nio.charset.StandardCharsets;

/*
    1. synchronize
    2. contains value by name without allocations
    3. parse int
    4. parse
 */
public class JParser {
    private static int counter = 0;
    private static int name_idx = 0;
    private static int main_idx = 0;
    private static int start_value_idx = 0;

    public byte[] parseString(byte[] bytes, byte[] prName) {
        if (parse(bytes, prName)) {
            return getStringValue(bytes);
        }
        return null;
    }

    public byte[] parseInt(byte[] bytes, byte[] prName) {
        if (parse(bytes, prName)) {
            return getIntValue(bytes);
        }
        return null;
    }

    public synchronized boolean parse(byte[] bytes, byte[] prName) {
        stateCleanUp();
        while (main_idx != bytes.length - 1) {
            if (prName[name_idx] == bytes[main_idx]) {
                counter++;
                name_idx++;
                if (counter == prName.length) {
                    main_idx++;//close quote
                    return true;
                }
            } else {
                counter = 0;
                name_idx = 0;
            }
            main_idx++;
        }
        return false;
    }

    private byte[] getIntValue(byte[] bytes) {
        while (main_idx != bytes.length - 1) {
            main_idx++;
            if (bytes[main_idx] == Const.COLON) {
                start_value_idx = main_idx + 1;
            } else if ((bytes[main_idx] == Const.COMMA || bytes[main_idx] == Const.CLOSE_BRACKET) && start_value_idx != 0) {
                return createBytesResult(bytes);
            }
        }
        return null;
    }

    private byte[] getStringValue(byte[] bytes) {
        while (main_idx != bytes.length - 1) {
            main_idx++;
            if (bytes[main_idx] == Const.QUOTE) {
                if (start_value_idx == 0) {
                    start_value_idx = main_idx + 1;
                } else {
                    return createBytesResult(bytes);
                }
            }
        }
        return null;
    }

    private byte[] createBytesResult(byte[] bytes) {
        byte[] result = new byte[main_idx - start_value_idx];
        System.arraycopy(bytes, start_value_idx, result, 0, result.length);
        return result;
    }


    private void stateCleanUp() {
        main_idx = 0;
        counter = 0;
        name_idx = 0;
        start_value_idx = 0;
    }
}
