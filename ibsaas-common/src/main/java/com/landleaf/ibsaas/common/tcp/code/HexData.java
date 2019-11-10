package com.landleaf.ibsaas.common.tcp.code;

public class HexData {
    private static final String HEXES = "0123456789ABCDEF";
    private static final String HEX_INDICATOR = "0x";
    private static final String HEX_INDICATOR1 = "";
    private static final String SPACE = " ";
    private static final String SPACE1 = "";

    public static String hexToString(byte[] data) {
        if (data != null) {
            StringBuilder hex = new StringBuilder(2 * data.length);
            for (int i = 0; i <= data.length - 1; i++) {
                byte dataAtIndex = data[i];
                hex.append(HEX_INDICATOR1);
                hex.append(HEXES.charAt((dataAtIndex & 0xF0) >> 4))
                        .append(HEXES.charAt((dataAtIndex & 0x0F)));
                hex.append(SPACE1);
            }
            return hex.toString();
        } else {
            return "";
        }
    }

    public static byte[] stringToBytes(String hexString) {
        String stringProcessed = hexString.trim().replaceAll("0x", "");
        stringProcessed = stringProcessed.replaceAll("\\s+", "");
        byte[] data = new byte[stringProcessed.length() / 2];
        int i = 0;
        int j = 0;
        while (i <= stringProcessed.length() - 1) {
            byte character = (byte) Integer.parseInt(stringProcessed.substring(i, i + 2), 16);
            data[j] = character;
            j++;
            i += 2;
        }
        return data;
    }

    static String hex4digits(String id) {
        if (id.length() == 1)
        {
            return "000" + id;
        }
        if (id.length() == 2)
        {
            return "00" + id;
        }
        if (id.length() == 3)
        {
            return "0" + id;
        }
        else
        {
            return id;
        }
    }

    static String hex2digits(String id) {
        if (id.length() == 1)
        {
            return "0" + id;
        }
        else
        {
            return id;
        }
    }
}
