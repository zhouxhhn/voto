package bjl.core.util;

/**
 * Author pengyi
 * Date 17-4-27.
 */
public class ByteUtils {
    public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        } else {
            if (startIndexInclusive < 0) {
                startIndexInclusive = 0;
            }

            if (endIndexExclusive > array.length) {
                endIndexExclusive = array.length;
            }

            int newSize = endIndexExclusive - startIndexInclusive;
            if (newSize <= 0) {
                return new byte[0];
            } else {
                byte[] subarray = new byte[newSize];
                System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
                return subarray;
            }
        }
    }

    public static byte[] addAll(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            byte[] joinedArray = new byte[array1.length + array2.length];
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        }
    }

    public static byte[] clone(byte[] array) {
        return array == null ? null : (byte[]) ((byte[]) array.clone());
    }

    /**
     * byte[] 转int
     * @param b byte数组
     * @return int
     */
    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    /**
     * int转byte[]
     * @param value int
     * @return byte数组
     */
    public static byte[] intToByteArray(int value) {

        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * short转byte[]
     * @param number short
     * @return byte数组
     */
    public static byte[] shortToByteArray(short number) {

        byte[] b = new byte[2];

        b[0] = (byte) (number >> 8);
        b[1] = (byte) number;

        return b;
    }

    /**
     * byte[] 转 short
     * @param b byte数组
     * @return short
     */
    public static short byteArrayToShort(byte[] b) {
        return (short) (((b[0] << 8) | b[1] & 0xff));
    }
}
