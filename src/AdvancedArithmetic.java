public class AdvancedArithmetic {

    // ==========================================
    // MULTIPLICATION
    // ==========================================
    public static BigNumber multiply(BigNumber a, BigNumber b) {

        // Zero times anything is zero.
        if (Helper.isZero(a) || Helper.isZero(b)) {
            return new BigNumber("0");
        }

        // Result sign is negative only when exactly one input is negative.
        boolean resultNegative = a.isNegative ^ b.isNegative;

        // Convert both numbers into their absolute values so that the digit-by-digit calculation is simplified.
        BigNumber aa = Helper.copy(a); aa.isNegative = false;
        BigNumber bb = Helper.copy(b); bb.isNegative = false;

        BigNumber finalResult = new BigNumber("0");
        Node currB = bb.tail;
        int shiftCount = 0;

        // Outer loop - process each digit of multiplier (second number)
        while (currB != null) {

            BigNumber partialResult = new BigNumber();
            Node currA = aa.tail;
            int carry = 0;

            // Inner loop - multiply each digit of the first number with the current digit of the second number
            while (currA != null || carry > 0) {

                int valA = (currA != null) ? currA.digit : 0;

                int product = (valA * currB.digit) + carry;

                carry = product / 10;

                partialResult.prepend(product % 10);

                // move to the next digit in a
                if (currA != null) {
                    currA = currA.prev;
                }
            }
            // Apply positional shifts by appending zeros based on the current digit place.
            for (int i = 0; i < shiftCount; i++) {
                partialResult.append(0);
            }

            finalResult = BasicArithmetic.add(finalResult, partialResult);

            shiftCount++;
            currB = currB.prev;
        }

        finalResult.removeLeadingZeros();

        // Apply the sign, but only if the result is not zero.
        if (!Helper.isZero(finalResult) && resultNegative) finalResult.isNegative = true;

        return finalResult;
    }

    // ==========================================
    // DIVISION SUPPORT
    // ==========================================
    public static int executeSubtractionLoop(
            BigNumber remainder,
            BigNumber divisor) {

        int quotientDigit = 0;

        // Keep subtracting the divisor until the remainder becomes smaller than the divisor.
        while (Helper.compare(remainder, divisor) >= 0) {

            // save current remainder for safety check to prevent infinite loops
            BigNumber oldRemainder = Helper.copy(remainder);
            BigNumber temp =
                    BasicArithmetic.subtract(remainder, divisor);

            // we compare values to ensure progress is being made
            if (Helper.compare(temp, oldRemainder) >= 0) {
                throw new ArithmeticException(
                        "Division did not make progress.");
            }

            // Java passes object references by value. We need to copy the temp result back into the remainder object.
            remainder.head = temp.head;
            remainder.tail = temp.tail;
            remainder.size = temp.size;
            remainder.isNegative = temp.isNegative;
            remainder.decimalPosition = temp.decimalPosition;
            remainder.removeLeadingZeros();

            // Each successful subtraction represents one increment in the quotient digit.
            quotientDigit++;
            // ensure that each quotient digit does not exceed 9
            if (quotientDigit > 9) {
                throw new ArithmeticException(
                        "Invalid division state: quotient digit exceeded 9.");
            }
        }

        return quotientDigit;
    }

    // ==========================================
    // FULL DIVISION
    // ==========================================
    public static BigNumber divide(
            BigNumber dividend,
            BigNumber divisor) {

        // Division by zero is not allowed.
        if (Helper.isZero(divisor)) {
            throw new ArithmeticException(
                    "Division by zero is undefined.");
        }

        // Zero divided by anything non-zero is still zero.
        if (Helper.isZero(dividend)) {
            return new BigNumber("0");
        }

        // Work with absolute values first, then attach the sign at the end.
        boolean resultNegative = dividend.isNegative ^ divisor.isNegative;
        BigNumber a = Helper.copy(dividend); a.isNegative = false;
        BigNumber b = Helper.copy(divisor); b.isNegative = false;
        a.removeLeadingZeros();
        b.removeLeadingZeros();

        // If both values are equal, the answer is exactly 1.
        if (Helper.compare(a, b) == 0) {
            BigNumber one = new BigNumber("1");
            if (resultNegative) one.isNegative = true;
            return one;
        }

        BigNumber quotient = new BigNumber();
        BigNumber remainder = new BigNumber();

        Node current = a.head;

        // Build the integer part one digit at a time from left to right.
        while (current != null) {

            remainder.append(current.digit);

            removeLeadingZeros(remainder);

            if (Helper.compare(remainder, b) < 0) {
                quotient.append(0);
            } else {
                int quotientDigit = executeSubtractionLoop(remainder, b);
                quotient.append(quotientDigit);
            }

            current = current.next;
        }

        // If dividend < divisor, keep a single zero in the integer part.
        if (quotient.head == null) quotient.append(0);

        // Plain decimal mode: stop after a fixed number of decimal digits.
        final int DECIMAL_PRECISION = 18;
        int decimalPlaces = 0;

        // Keep bringing down zeroes to compute the fractional part.
        while (!Helper.isZero(remainder) && decimalPlaces < DECIMAL_PRECISION) {
            remainder.append(0);
            removeLeadingZeros(remainder);

            int qd = executeSubtractionLoop(remainder, b);
            quotient.append(qd);
            decimalPlaces++;
        }

        if (decimalPlaces > 0) quotient.decimalPosition = decimalPlaces;

        quotient.removeLeadingZeros();

        if (!Helper.isZero(quotient) && resultNegative) quotient.isNegative = true;

        return quotient;
    }

    // ==========================================
    // REMOVE LEADING ZEROS
    // ==========================================
    private static void removeLeadingZeros(
            BigNumber num) {

        // Drop leading zero nodes but never delete the last remaining digit.
        while (num.head != null
                && num.head.digit == 0
                && num.head != num.tail) {

            num.head = num.head.next;

            if (num.head != null) {
                num.head.prev = null;
            }

            num.size--;
        }
    }

}