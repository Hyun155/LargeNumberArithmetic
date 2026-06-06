public class DivisionArithmetic {

    /**
     * Performs Long Division on two BigNumbers (dividend / divisor).
     * Returns a new BigNumber representing the Quotient.
     */
    public static BigNumber divide(BigNumber dividend, BigNumber divisor) {
        // Edge Case 1: Division by Zero
        if (Helper.isZero(divisor)) {
            throw new ArithmeticException("Deplorable Condition: Division by zero is mathematically undefined!");
        }

        // Edge Case 2: If dividend is 0, the quotient is always 0
        if (Helper.isZero(dividend)) {
            return new BigNumber("0");
        }

        // Edge Case 3: If dividend < divisor, the quotient is 0
        if (Helper.compare(dividend, divisor) < 0) {
            return new BigNumber("0");
        }

        // Edge Case 4: If dividend == divisor, the quotient is 1
        if (Helper.compare(dividend, divisor) == 0) {
            return new BigNumber("1");
        }

        BigNumber quotient = new BigNumber();
        BigNumber remainder = new BigNumber();
        
        // Grab the head pointer of the dividend to process digits from Most Significant to Least Significant
        Node current = dividend.head;

        // Simulate manual step-by-step long division
        while (current != null) {
            // Append the next single digit from the dividend to our current running remainder
            remainder.append(current.digit);
            
            // Clean up leading zeros from the remainder so Helper.compare assesses it accurately
            removeLeadingZeros(remainder);

            // Compare running remainder against the divisor
            if (Helper.compare(remainder, divisor) < 0) {
                // If the remainder is smaller than the divisor, it fits 0 times
                quotient.append(0);
            } else {
                // Leverage Member 4's AdvancedArithmetic.executeSubtractionLoop
                // This mutates the remainder in-place and returns how many times the divisor fits
                int quotientDigit = AdvancedArithmetic.executeSubtractionLoop(remainder, divisor);
                quotient.append(quotientDigit);
            }
            
            // Step to the next digit down the linked list
            current = current.next;
        }

        // Clean up any structural leading zeros generated during processing in the final quotient
        removeLeadingZeros(quotient);
        return quotient;
    }

    /**
     * Helper method to strip out structural leading zeros from standard tracking operations
     * (e.g., turning a list representation of "0034" into "34").
     */
    private static void removeLeadingZeros(BigNumber num) {
        while (num.head != null && num.head.digit == 0 && num.head != num.tail) {
            num.head = num.head.next;
            if (num.head != null) {
                num.head.prev = null;
            }
        }
    }
}