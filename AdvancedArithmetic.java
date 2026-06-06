public class AdvancedArithmetic {

    // ==========================================
    // MULTIPLICATION
    // ==========================================
    public static BigNumber multiply(BigNumber a, BigNumber b) {

        if (Helper.isZero(a) || Helper.isZero(b)) {
            return new BigNumber("0");
        }

        BigNumber finalResult = new BigNumber("0");
        Node currB = b.tail;
        int shiftCount = 0;

        while (currB != null) {

            BigNumber partialResult = new BigNumber();
            Node currA = a.tail;
            int carry = 0;

            while (currA != null || carry > 0) {

                int valA = (currA != null) ? currA.digit : 0;

                int product = (valA * currB.digit) + carry;

                carry = product / 10;

                partialResult.prepend(product % 10);

                if (currA != null) {
                    currA = currA.prev;
                }
            }

            for (int i = 0; i < shiftCount; i++) {
                partialResult.append(0);
            }

            finalResult = BasicArithmetic.add(finalResult, partialResult);

            shiftCount++;
            currB = currB.prev;
        }

        finalResult.removeLeadingZeros();

        return finalResult;
    }

    // ==========================================
    // DIVISION SUPPORT
    // ==========================================
    public static int executeSubtractionLoop(
            BigNumber remainder,
            BigNumber divisor) {

        int quotientDigit = 0;

        while (Helper.compare(remainder, divisor) >= 0) {

            BigNumber temp =
                    BasicArithmetic.subtract(remainder, divisor);

            remainder.head = temp.head;
            remainder.tail = temp.tail;

            quotientDigit++;
        }

        return quotientDigit;
    }

    // ==========================================
    // FULL DIVISION
    // ==========================================
    public static BigNumber divide(
            BigNumber dividend,
            BigNumber divisor) {

        if (Helper.isZero(divisor)) {
            throw new ArithmeticException(
                    "Division by zero is undefined.");
        }

        if (Helper.isZero(dividend)) {
            return new BigNumber("0");
        }

        if (Helper.compare(dividend, divisor) < 0) {
            return new BigNumber("0");
        }

        if (Helper.compare(dividend, divisor) == 0) {
            return new BigNumber("1");
        }

        BigNumber quotient = new BigNumber();
        BigNumber remainder = new BigNumber();

        Node current = dividend.head;

        while (current != null) {

            remainder.append(current.digit);

            removeLeadingZeros(remainder);

            if (Helper.compare(remainder, divisor) < 0) {

                quotient.append(0);

            } else {

                int quotientDigit =
                        executeSubtractionLoop(
                                remainder,
                                divisor);

                quotient.append(quotientDigit);
            }

            current = current.next;
        }

        removeLeadingZeros(quotient);

        return quotient;
    }

    // ==========================================
    // REMOVE LEADING ZEROS
    // ==========================================
    private static void removeLeadingZeros(
            BigNumber num) {

        while (num.head != null
                && num.head.digit == 0
                && num.head != num.tail) {

            num.head = num.head.next;

            if (num.head != null) {
                num.head.prev = null;
            }
        }
    }
}