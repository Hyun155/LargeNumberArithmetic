public class Helper {

    // Count total nodes in the number list (raw length, including leading zeros).
    public static int length(BigNumber num) {

        int count = 0;                  // Running digit counter.
        Node current = num.head;        // Start at the most significant node.

        while (current != null) {       // Traverse until end of linked list.
            count++;                    // Count this digit node.
            current = current.next;     // Move to next digit.
        }

        return count;                   // Total stored digits.
    }

    // Return the first significant digit by skipping leading zeros.
    // Keep one trailing node so values like 0000 still resolve to a single zero node.
    private static Node firstSignificantDigit(BigNumber num) {
        Node current = num.head;
        while (current != null && current.digit == 0 && current.next != null) {
            current = current.next;     // Skip formatting zeros at the front.
        }
        return current;                 // First meaningful node (or last zero node).
    }

    // Count digits after leading-zero normalization (magnitude length).
    private static int significantLength(BigNumber num) {
        Node current = firstSignificantDigit(num); // Start at first non-leading-zero digit.
        int count = 0;                             // Normalized digit count.

        while (current != null) {
            count++;                    // Count only significant/magnitude digits.
            current = current.next;     // Continue through remaining digits.
        }

        return count;
    }

    // Compare two BigNumbers: 1 if a>b, -1 if a<b, 0 if equal.
    public static int compare(BigNumber a, BigNumber b) {

        // Sign check decides immediately when signs differ.
        if (a.isNegative && !b.isNegative) return -1;
        if (!a.isNegative && b.isNegative) return 1;

        int lenA = significantLength(a);    // Magnitude length of a after trimming zeros.
        int lenB = significantLength(b);    // Magnitude length of b after trimming zeros.
        Node p = firstSignificantDigit(a);  // Pointer into a's first significant digit.
        Node q = firstSignificantDigit(b);  // Pointer into b's first significant digit.

        if (!a.isNegative) {
            // Positive numbers: longer magnitude means larger numeric value.
            if (lenA > lenB) return 1;
            if (lenA < lenB) return -1;

            // Same length: compare digit-by-digit from most significant side.
            while (p != null && q != null) {
                if (p.digit > q.digit) return 1;
                if (p.digit < q.digit) return -1;
                p = p.next;
                q = q.next;
            }
            return 0; // Every compared digit matched.
        } else {
            // Negative numbers: larger magnitude means smaller numeric value.
            if (lenA > lenB) return -1;
            if (lenA < lenB) return 1;

            // Same length: reverse digit comparison results because both are negative.
            while (p != null && q != null) {
                if (p.digit > q.digit) return -1;
                if (p.digit < q.digit) return 1;
                p = p.next;
                q = q.next;
            }
            return 0; // Same magnitude and same sign means equal values.
        }
    }

    // Return true only when every stored digit is zero (0, 00, 0000, etc.).
    public static boolean isZero(BigNumber num) {

        Node current = num.head;            // Begin at head and inspect all digits.

        while (current != null) {

            if (current.digit != 0) {
                return false;               // Found a non-zero digit, so value is not zero.
            }

            current = current.next;         // Continue scanning remaining digits.
        }

        return true;                        // No non-zero digits were found.
    }

    // Deep copy: duplicate all digit nodes and metadata into a new BigNumber object.
    public static BigNumber copy(BigNumber original) {

        BigNumber newNum = new BigNumber(); // Independent destination object.

        Node current = original.head;       // Traverse source number from head.

        while (current != null) {

            newNum.append(current.digit);   // Copy current digit into new node chain.

            current = current.next;         // Move to next source digit.
        }

        newNum.isNegative = original.isNegative;         // Preserve sign metadata.
        newNum.decimalPosition = original.decimalPosition; // Preserve decimal placement.

        return newNum;                      // Return fully independent equivalent number.
    }

}