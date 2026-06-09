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
    // 2. COMPARE FUNCTION
    // return 1 if a > b
    // return -1 if a < b
    // return 0 if equal
    // =====================================================
    public static int compare(BigNumber a, BigNumber b) {

        // Handle sign differences first.
        if (a.isNegative && !b.isNegative) return -1;
        if (!a.isNegative && b.isNegative) return 1;

        // Both numbers have the same sign, so compare magnitudes.
        int lenA = length(a);
        int lenB = length(b);

        if (!a.isNegative) {
            // Both positive: longer length means larger value.
            if (lenA > lenB) return 1;
            if (lenA < lenB) return -1;

            Node p = a.head;
            Node q = b.head;
            while (p != null) {
                if (p.digit > q.digit) return 1;
                if (p.digit < q.digit) return -1;
                p = p.next;
                q = q.next;
            }
            return 0;
        } else {
            // Both negative: larger magnitude means smaller value.
            if (lenA > lenB) return -1;
            if (lenA < lenB) return 1;

            Node p = a.head;
            Node q = b.head;
            while (p != null) {
                if (p.digit > q.digit) return -1;
                if (p.digit < q.digit) return 1;
                p = p.next;
                q = q.next;
            }
            return 0;
        }
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