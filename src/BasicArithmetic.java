public class BasicArithmetic {
    // Add two numbers from right to left, carrying when a digit exceeds 9.
    public static BigNumber add(BigNumber num1, BigNumber num2){
        Node currentNode1 = num1.tail;           // Start from least-significant digit of num1.
        Node currentNode2 = num2.tail;           // Start from least-significant digit of num2.
        BigNumber sumNumber = new BigNumber();   // Destination list for resulting sum.
        int carry = 0;                           // Carry propagated to the next column.
        
        // Handle sign combinations by reducing them to addition/subtraction.
        if (num1.isNegative && !num2.isNegative){ // Case 1: -a + b = b - a
            num1.isNegative = false;
            BigNumber result = subtract(num2, num1);
            num1.isNegative = true;
            return result;
        }
        else if (!num1.isNegative && num2.isNegative){ // Case 2: a + (-b) = a - b
            num2.isNegative = false;
            BigNumber result = subtract(num1, num2);
            num2.isNegative = true;
            return result;
        }
        else if (num1.isNegative && num2.isNegative){ // Case 3: (-a) + (-b) = -(a + b)
            // Temporarily use absolute values so we can reuse the positive add path.
            num1.isNegative = false;
            num2.isNegative = false;
            
            BigNumber result = add(num1, num2);
            
            // Restore original input signs before returning.
            num1.isNegative = true;
            num2.isNegative = true;
            
            result.isNegative = true;            // Sum of two negatives is negative.
            return result;
        }
        else { // Case 4: both numbers are positive
            while (currentNode1!=null || currentNode2!=null || carry>0){
                // If one number is shorter, missing digits are treated as zero.
                int digit1 = (currentNode1!=null)? currentNode1.digit : 0;
                int digit2 = (currentNode2!=null)? currentNode2.digit : 0;
            
                // Move one column to the left in each operand.
                if (currentNode1!=null) currentNode1 = currentNode1.prev;
                if (currentNode2!=null) currentNode2 = currentNode2.prev;
            
                int sumnum = 0;
                sumnum = digit1 + digit2 + carry; // Column sum including incoming carry.
               if (sumnum>9){
                   carry = sumnum/10;           // For base-10 this will be 1 here.
                   addHead(sumNumber, sumnum%10); // Keep the ones place in current column.
               } else {
                  carry = 0;                    // No carry needed for next column.
                  addHead(sumNumber, sumnum);  // Push full value if it is a single digit.
              }
            }   
        }
        sumNumber.removeLeadingZeros();         // Canonicalize output like 00042 -> 42.
        return sumNumber;
    }
    
    // Subtract two numbers from right to left, borrowing when needed.
    public static BigNumber subtract(BigNumber num1, BigNumber num2){
        // Handle sign combinations first.
        if (num1.isNegative && !num2.isNegative) {
            // (-a) - b = -(a + b)
            BigNumber a = Helper.copy(num1);
            a.isNegative = false;
            BigNumber sum = add(a, num2);
            sum.isNegative = true;
            return sum;
        }
        if (!num1.isNegative && num2.isNegative) {
            // a - (-b) = a + b
            BigNumber b = Helper.copy(num2);
            b.isNegative = false;
            return add(num1, b);
        }
        if (num1.isNegative && num2.isNegative) {
            // (-a) - (-b) = b - a
            BigNumber a = Helper.copy(num1); a.isNegative = false;
            BigNumber b = Helper.copy(num2); b.isNegative = false;
            return subtract(b, a);
        }

        // Now both are non-negative. Compare magnitudes using Helper.compare.
        Node currentNode1 = num1.tail;          // Start from least-significant digit of minuend.
        Node currentNode2 = num2.tail;          // Start from least-significant digit of subtrahend.
        BigNumber subtractNumber = new BigNumber(); // Destination list for difference.
        int borrow = 0;                         // Borrow propagated from previous column.

        // If the left side is bigger, subtract normally.
        if (Helper.compare(num1, num2) >= 0){ // 1st number is bigger or equal
            while (currentNode1!=null || currentNode2!=null || borrow!=0){
                int digit1 = (currentNode1!=null)? currentNode1.digit : 0;
                int digit2 = (currentNode2!=null)? currentNode2.digit : 0;

                // Move left to process next more-significant column.
                if (currentNode1!=null) currentNode1 = currentNode1.prev;
                if (currentNode2!=null) currentNode2 = currentNode2.prev;

                int subnum = digit1 - digit2 - borrow;
                if (subnum<0){
                    subnum += 10;               // Borrow from next column in base-10 arithmetic.
                    borrow = 1;
                } else borrow = 0;              // No borrow needed if column stayed non-negative.
                addHead(subtractNumber, subnum); // Prepend current result digit.
            }
        } else { // If the left side is smaller, flip the order and mark negative.
            BigNumber result = subtract(num2, num1);
            result.isNegative = true;
            return result;
        }

        // Remove extra zero nodes from the front.
        Node subcurrent = subtractNumber.head;
        while (subcurrent!=null && subcurrent.digit==0 && subcurrent.next!=null){
            subtractNumber.head = subcurrent.next;
            subtractNumber.head.prev = null;
            subcurrent = subcurrent.next;
            subtractNumber.size--;              // Keep explicit node count synchronized.
        }

        return subtractNumber;
    }
    
    private static void addHead(BigNumber bn, int digit){
        Node newNode = new Node(digit);         // New most-significant node to insert.
        
        if(bn.head == null) {
            // The list is empty, so this node becomes both head and tail.
            bn.head = newNode;
            bn.tail = newNode;
        } else {
            // Attach the new digit before the current head.
            newNode.next = bn.head;   
            bn.head.prev = newNode;   
            bn.head = newNode;  
        }
        
        bn.size++;                              // Track list size after insertion.
    }
}
