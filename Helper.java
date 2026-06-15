public class Helper {

    // =====================================================
    // 1. LENGTH FUNCTION
    // Count how many digits are in the BigNumber
    // =====================================================
    public static int length(BigNumber num) {

        int count = 0;
        Node current = num.head;

        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }

    // =====================================================
    // 2. NORMALIZATION HELPERS
    // =====================================================
    private static Node firstSignificantDigit(BigNumber num) {
        Node current = num.head;
        while (current != null && current.digit == 0 && current.next != null) {
            current = current.next;
        }
        return current;
    }

    private static int significantLength(BigNumber num) {
        Node current = firstSignificantDigit(num);
        int count = 0;

        while (current != null) {
            count++;
            current = current.next;
        }

        return count;
    }

    private static int compareMagnitude(BigNumber a, BigNumber b) {
        int lenA = significantLength(a);
        int lenB = significantLength(b);
        Node p = firstSignificantDigit(a);
        Node q = firstSignificantDigit(b);

        if (lenA > lenB) return 1;
        if (lenA < lenB) return -1;

        while (p != null && q != null) {
            if (p.digit > q.digit) return 1;
            if (p.digit < q.digit) return -1;
            p = p.next;
            q = q.next;
        }

        return 0;
    }

    // =====================================================
    // 3. COMPARE FUNCTION
    // return 1 if a > b
    // return -1 if a < b
    // return 0 if equal
    // =====================================================
    public static int compare(BigNumber a, BigNumber b) {

        // Handle sign differences first.
        if (a.isNegative && !b.isNegative) return -1;
        if (!a.isNegative && b.isNegative) return 1;

        // Both numbers have the same sign.
        int magnitudeComparison = compareMagnitude(a, b);

        if (!a.isNegative) {
            return magnitudeComparison;
        }

        // Both negative: larger magnitude means smaller value.
        return -magnitudeComparison;
    }

    // =====================================================
    // 3. IS ZERO FUNCTION
    // Check if number is 0 (or 0000, etc.)
    // =====================================================
    public static boolean isZero(BigNumber num) {

        // A number is zero only when every digit is zero.
        Node current = num.head;

        while (current != null) {

            if (current.digit != 0) {
                return false;
            }

            current = current.next;
        }

        return true;
    }

    // =====================================================
    // 4. DEEP COPY FUNCTION
    // Create a completely new BigNumber
    // =====================================================
    public static BigNumber copy(BigNumber original) {

        // Create a new list with the same digits and metadata.
        BigNumber newNum = new BigNumber();

        Node current = original.head;

        while (current != null) {

            newNum.append(current.digit);

            current = current.next;
        }

        newNum.isNegative = original.isNegative;
        newNum.decimalPosition = original.decimalPosition;

        return newNum;
    }

}