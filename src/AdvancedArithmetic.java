public class AdvancedArithmetic {

    // Multiply two BigNumbers using long multiplication (grade-school method).
    public static BigNumber multiply(BigNumber a, BigNumber b) {

        // Zero times anything is zero.
        if (Helper.isZero(a) || Helper.isZero(b)) {
            return new BigNumber("0");
        }

        // Result sign is negative only when exactly one input is negative.
        boolean resultNegative = a.isNegative ^ b.isNegative;

        // Work with absolute-value copies to simplify core multiplication logic.
        BigNumber aa = Helper.copy(a); aa.isNegative = false;
        BigNumber bb = Helper.copy(b); bb.isNegative = false;

        BigNumber finalResult = new BigNumber("0"); // Running total of all shifted partial products.
        Node currB = bb.tail;                        // Current multiplier digit (right to left).
        int shiftCount = 0;                          // Number of trailing zeros for current place value.

        // Process each digit of the multiplier (bb) from least to most significant.
        while (currB != null) {

            BigNumber partialResult = new BigNumber(); // Product for one multiplier digit.
            Node currA = aa.tail;                      // Iterate multiplicand digits right to left.
            int carry = 0;                             // Carry from digit multiplication.

            // Multiply one multiplier digit by every multiplicand digit.
            while (currA != null || carry > 0) {

                int valA = (currA != null) ? currA.digit : 0; // After list end, only carry remains.

                int product = (valA * currB.digit) + carry;   // Column product + incoming carry.

                carry = product / 10;                         // Carry to next more-significant column.

                partialResult.prepend(product % 10);          // Keep ones digit in current column.

                // move to the next digit in a
                if (currA != null) {
                    currA = currA.prev;
                }
            }
            // Apply positional shifts by appending zeros based on the current digit place.
            for (int i = 0; i < shiftCount; i++) {
                partialResult.append(0);
            }

            // Add the shifted partial product into accumulated result.
            finalResult = BasicArithmetic.add(finalResult, partialResult);

            shiftCount++;               // Next multiplier digit is one place to the left.
            currB = currB.prev;         // Move to next multiplier digit.
        }

        finalResult.removeLeadingZeros(); // Canonicalize before sign assignment.

        // Apply the sign, but only if the result is not zero.
        if (!Helper.isZero(finalResult) && resultNegative) finalResult.isNegative = true;

        return finalResult;
    }

    // Repeatedly subtract divisor from remainder to compute one quotient digit.
    public static int executeSubtractionLoop(
            BigNumber remainder,
            BigNumber divisor) {

        int quotientDigit = 0; // Counts how many times divisor fits into current remainder.

        // Keep subtracting the divisor until the remainder becomes smaller than the divisor.
        while (Helper.compare(remainder, divisor) >= 0) {

            // Keep a snapshot to prove each iteration strictly decreases remainder.
            BigNumber oldRemainder = Helper.copy(remainder);
            BigNumber temp =
                    BasicArithmetic.subtract(remainder, divisor);

            // If subtraction does not decrease, division state is invalid.
            if (Helper.compare(temp, oldRemainder) >= 0) {
                throw new ArithmeticException(
                        "Division did not make progress.");
            }

            // Copy temp back into the same remainder object reference used by caller.
            remainder.head = temp.head;
            remainder.tail = temp.tail;
            remainder.size = temp.size;
            remainder.isNegative = temp.isNegative;
            remainder.decimalPosition = temp.decimalPosition;
            remainder.removeLeadingZeros();

            // Each successful subtraction contributes one to this quotient place.
            quotientDigit++;
            // Base-10 long division requires each quotient digit to be in [0, 9].
            if (quotientDigit > 9) {
                throw new ArithmeticException(
                        "Invalid division state: quotient digit exceeded 9.");
            }
        }

        return quotientDigit;
    }

    // Divide two BigNumbers using long division with fixed decimal precision.
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
        BigNumber a = Helper.copy(dividend); a.isNegative = false; // Absolute dividend.
        BigNumber b = Helper.copy(divisor); b.isNegative = false;   // Absolute divisor.
        a.removeLeadingZeros();                                     // Normalize for stable compare/divide.
        b.removeLeadingZeros();

        // If both values are equal, the answer is exactly 1.
        if (Helper.compare(a, b) == 0) {
            BigNumber one = new BigNumber("1");
            if (resultNegative) one.isNegative = true;
            return one;
        }

        BigNumber quotient = new BigNumber();  // Digits of final quotient (integer + decimal).
        BigNumber remainder = new BigNumber(); // Running remainder during long division.

        Node current = a.head; // Consume dividend digits from most to least significant.

        // Build the integer part one digit at a time from left to right.
        while (current != null) {

            remainder.append(current.digit); // "Bring down" next dividend digit.

            removeLeadingZeros(remainder);

            if (Helper.compare(remainder, b) < 0) {
                quotient.append(0); // Divisor does not fit yet for this place.
            } else {
                int quotientDigit = executeSubtractionLoop(remainder, b);
                quotient.append(quotientDigit);
            }

            current = current.next;
        }

        // If dividend < divisor, keep a single zero in the integer part.
        if (quotient.head == null) quotient.append(0);

        // Fixed precision decimal mode: stop after configured fractional places.
        final int DECIMAL_PRECISION = 18;
        int decimalPlaces = 0;

        // Continue long division into fractional part by bringing down trailing zeros.
        while (!Helper.isZero(remainder) && decimalPlaces < DECIMAL_PRECISION) {
            remainder.append(0);
            removeLeadingZeros(remainder);

            int qd = executeSubtractionLoop(remainder, b);
            quotient.append(qd);
            decimalPlaces++;
        }

        if (decimalPlaces > 0) quotient.decimalPosition = decimalPlaces; // Mark fractional digit count.

        quotient.removeLeadingZeros();

        if (!Helper.isZero(quotient) && resultNegative) quotient.isNegative = true;

        return quotient;
    }

    // Trim leading zeros while preserving at least one digit node.
    private static void removeLeadingZeros(
            BigNumber num) {

        // Drop leading zero nodes but never delete the last remaining digit.
        while (num.head != null
                && num.head.digit == 0
                && num.head != num.tail) {

            num.head = num.head.next;

            if (num.head != null) {
                num.head.prev = null; // Detach removed prefix from new head.
            }

            num.size--; // Keep explicit size metadata accurate.
        }
    }

}