public class AdvancedArithmetic {

    // 1. Multiplication Algorithm
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
                int prod = (valA * currB.digit) + carry;
                
                carry = prod / 10;
                partialResult.prepend(prod % 10);
                if (currA != null) currA = currA.prev;
            }

            for (int i = 0; i < shiftCount; i++) {
                partialResult.append(0);
            }

            // Call Member 3's separated addition method
            finalResult = BasicArithmetic.add(finalResult, partialResult);
            shiftCount++;
            currB = currB.prev;
        }

        finalResult.removeLeadingZeros();
        return finalResult;
    }

    // 2. Division Support Logic (Shared Role)
    public static int executeSubtractionLoop(BigNumber remainder, BigNumber divisor) {
        int quotientDigit = 0;
        
        while (Helper.compare(remainder, divisor) >= 0) {
            // Call Member 3's separated subtraction method
            BigNumber temp = BasicArithmetic.subtract(remainder, divisor);
            
            remainder.head = temp.head;
            remainder.tail = temp.tail;
            quotientDigit++;
        }
        
        return quotientDigit;
    }

    // 1. Full Division Algorithm
}