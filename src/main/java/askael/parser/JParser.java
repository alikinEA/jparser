package askael.parser;

/**
 * This class is not thread-safe, need to create new object for each thread.
 */
public class JParser {
    private static int counter = 0;
    private static int name_idx = 0;
    private static int value_idx = 0;
    private static int main_idx = 0;
    private static int start_value_idx = 0;

    public byte[] parseString(byte[] bytes, byte[] prName) {
        if (moveToValue(bytes, prName)) {
            return getStringValue(bytes);
        }
        return null;
    }

    public byte[] parseInt(byte[] bytes, byte[] prName) {
        if (moveToValue(bytes, prName)) {
            return getIntValue(bytes);
        }
        return null;
    }

    public boolean containsInt(byte[] bytes, byte[] prName, byte[] value) {
        if (moveToValue(bytes, prName)) {
            return containsIntValue(bytes, value);
        }
        return false;
    }

    public boolean containsString(byte[] bytes, byte[] prName, byte[] value) {
        if (moveToValue(bytes, prName)) {
            return containsStringValue(bytes, value);
        }
        return false;
    }

    private boolean containsStringValue(byte[] bytes, byte[] value) {
        while (main_idx != bytes.length - 1) {
            if (bytes[main_idx] == Const.QUOTE) {
                if (start_value_idx == 0) {
                    start_value_idx = main_idx + 1;
                } else {
                    return true;
                }
            } else {
                if (value[value_idx] != bytes[main_idx]) {
                    return false;
                } else {
                    value_idx++;
                }
            }

            main_idx++;
        }
        return false;
    }

    private boolean containsIntValue(byte[] bytes, byte[] value) {
        while (main_idx != bytes.length - 1) {
            if ((bytes[main_idx] == Const.COMMA || bytes[main_idx] == Const.CLOSE_BRACKET)) {
                return true;
            } else {
                if (value[value_idx] != bytes[main_idx]) {
                    return false;
                } else {
                    value_idx++;
                }
            }

            main_idx++;
        }
        return false;
    }

    public boolean moveToValue(byte[] bytes, byte[] prName) {
        stateCleanUp();
        while (main_idx != bytes.length - 1) {

            //skip spaces
            if (bytes[main_idx] == Const.SPACE) {
                main_idx++;
                continue;
            }

            // check property name
            if (prName[name_idx] == bytes[main_idx]) {
                counter++;
                name_idx++;
                if (counter == prName.length && bytes[main_idx + 1] == Const.QUOTE) {
                    // last name byte + close quote
                    main_idx = main_idx + 2;

                    skipDividers(bytes);
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

    private void skipDividers(byte[] bytes) {
        while (bytes[main_idx] == Const.SPACE ||
                bytes[main_idx] == Const.COLON)
            main_idx++;
    }

    private byte[] getIntValue(byte[] bytes) {
        start_value_idx = main_idx;

        while (main_idx != bytes.length - 1) {
            if ((bytes[main_idx] == Const.COMMA || bytes[main_idx] == Const.CLOSE_BRACKET)) {
                return createBytesResult(bytes);
            }

            main_idx++;
        }
        return null;
    }

    private byte[] getStringValue(byte[] bytes) {
        while (main_idx != bytes.length - 1) {
            if (bytes[main_idx] == Const.QUOTE) {
                if (start_value_idx == 0) {
                    start_value_idx = main_idx + 1;
                } else {
                    return createBytesResult(bytes);
                }
            }

            main_idx++;
        }
        return null;
    }

    private byte[] createBytesResult(byte[] bytes) {
        byte[] result = new byte[main_idx - start_value_idx];
        System.arraycopy(bytes, start_value_idx, result, 0, result.length);
        return result;
    }

    /**
     * reset all internal indexes
     */
    private void stateCleanUp() {
        main_idx = 0;
        counter = 0;
        name_idx = 0;
        start_value_idx = 0;
        value_idx = 0;
    }
}
