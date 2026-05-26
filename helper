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

        // Step 1: compare length first
        int lenA = length(a);
        int lenB = length(b);

        if (lenA > lenB) return 1;
        if (lenA < lenB) return -1;

        // Step 2: same length → compare digit by digit
        Node p = a.head;
        Node q = b.head;

        while (p != null) {

            if (p.digit > q.digit) return 1;
            if (p.digit < q.digit) return -1;

            p = p.next;
            q = q.next;
        }

        return 0;
    }

    // =====================================================
    // 3. IS ZERO FUNCTION
    // Check if number is 0 (or 0000, etc.)
    // =====================================================
    public static boolean isZero(BigNumber num) {

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

        BigNumber newNum = new BigNumber();

        Node current = original.head;

        while (current != null) {

            newNum.append(current.digit);

            current = current.next;
        }

        return newNum;
    }

    // =====================================================
    // 5. REVERSE FUNCTION
    // Reverse the doubly linked list in-place
    // =====================================================
    public static void reverse(BigNumber num) {

        Node current = num.head;
        Node temp = null;

        while (current != null) {

            // swap prev and next
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;

            current = current.prev;
        }

        // swap head and tail
        temp = num.head;
        num.head = num.tail;
        num.tail = temp;
    }
}