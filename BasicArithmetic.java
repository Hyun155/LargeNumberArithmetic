public class BasicArithmetic {
    // Add two numbers from right to left, carrying when a digit exceeds 9.
    public static BigNumber add(BigNumber num1, BigNumber num2){
        Node currentNode1 = num1.tail;
        Node currentNode2 = num2.tail;
        BigNumber sumNumber = new BigNumber();
        int carry = 0;
        
        // Handle sign combinations by reducing them to addition/subtraction.
        if (num1.isNegative && !num2.isNegative){ // Case 1: -50+30 = 30-50
            num1.isNegative = false;
            BigNumber result = subtract(num2, num1);
            num1.isNegative = true;
            return result;
        }
        else if (!num1.isNegative && num2.isNegative){ // Case 2: 50+(-30) = 50-30
            num2.isNegative = false;
            BigNumber result = subtract(num1, num2);
            num2.isNegative = true;
            return result;
        }
        else if (num1.isNegative && num2.isNegative){ // Case 3: -50+(-30) = -80
            // Temporarily flip the flags to avoid StackOverflow error
            num1.isNegative = false;
            num2.isNegative = false;
            
            BigNumber result = add(num1, num2);
            
            // Restore the original signs back
            num1.isNegative = true;
            num2.isNegative = true;
            
            result.isNegative = true;
            return result;
        }
        else { // Case 4: both numbers are positive
            while (currentNode1!=null || currentNode2!=null || carry>0){
                // Handling different lengths of numbers
                // If its null, its assumed as zero
                int digit1 = (currentNode1!=null)? currentNode1.digit : 0;
                int digit2 = (currentNode2!=null)? currentNode2.digit : 0;
            
                // Update the nodes
                if (currentNode1!=null) currentNode1 = currentNode1.prev;
                if (currentNode2!=null) currentNode2 = currentNode2.prev;
            
                // Sum logic
                int sumnum = 0;
                sumnum = digit1 + digit2 + carry;
               if (sumnum>9){
                   carry = sumnum/10;
                   addHead(sumNumber, sumnum%10);
               } else {
                  carry = 0;
                  addHead(sumNumber, sumnum);
              }
            }   
        }
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
        Node currentNode1 = num1.tail;
        Node currentNode2 = num2.tail;
        BigNumber subtractNumber = new BigNumber();
        int borrow = 0;

        // If the left side is bigger, subtract normally.
        if (Helper.compare(num1, num2) >= 0){ // 1st number is bigger or equal
            while (currentNode1!=null || currentNode2!=null || borrow!=0){
                int digit1 = (currentNode1!=null)? currentNode1.digit : 0;
                int digit2 = (currentNode2!=null)? currentNode2.digit : 0;

                if (currentNode1!=null) currentNode1 = currentNode1.prev;
                if (currentNode2!=null) currentNode2 = currentNode2.prev;

                int subnum = digit1 - digit2 - borrow;
                if (subnum<0){
                    subnum += 10;
                    borrow = 1;
                } else borrow = 0;
                addHead(subtractNumber, subnum);
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
        }

        return subtractNumber;
    }
    
    private static void addHead(BigNumber bn, int digit){
        Node newNode = new Node(digit);
        
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
        
        // Increment size
        bn.size++;
    }
}
