public class BasicArithmetic {
    // Addition function from tail (least sig digit), handle carry
    public static BigNumber add(BigNumber num1, BigNumber num2){
        Node currentNode1 = num1.tail;
        Node currentNode2 = num2.tail;
        BigNumber sumNumber = new BigNumber();
        int carry = 0;
        
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
        else { // Case 4: 50+30=80
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
    
    // Subtraction function from tail, handle borrow, ensure sign (neg)
    public static BigNumber subtract(BigNumber num1, BigNumber num2){
        Node currentNode1 = num1.tail;
        Node currentNode2 = num2.tail;
        BigNumber subtractNumber = new BigNumber();
        int borrow = 0;
        
        if (compareBiggerThan(num1, num2)){ // 1st number is bigger than 2nd number
            while (currentNode1!=null || currentNode2!=null || borrow!=0){
                
                // Assign digits
                int digit1 = (currentNode1!=null)? currentNode1.digit : 0;
                int digit2 = (currentNode2!=null)? currentNode2.digit : 0;
                
                // Update the nodes
                if (currentNode1!=null) currentNode1 = currentNode1.prev;
                if (currentNode2!=null) currentNode2 = currentNode2.prev;
            
                // Subtract logic
                // example: (1)2-9 = 2-9+10
                int subnum = digit1 - digit2 - borrow;
                if (subnum<0){
                    subnum += 10; // if its borrowed, add 10 
                    borrow = 1; // next number will subtract 1 cuz borrowed
                } else borrow = 0;
                addHead(subtractNumber, subnum);
            }
        } else { // 1st number is smaller than 2nd number
            BigNumber result = subtract(num2, num1);
            // Change negative flag
            result.isNegative = true;
            return result;
        }
        
        // clean the leading zeros
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
            //List is empty, this node is both head and tail
            bn.head = newNode;
            bn.tail = newNode;
        } else {
            // Connect new node at head
            newNode.next = bn.head;   
            bn.head.prev = newNode;   
            bn.head = newNode;  
        }
        
        // Increment size
        bn.size++;
    }
    
    private static boolean compareBiggerThan(BigNumber bn1, BigNumber bn2){
        if (bn1.size>bn2.size) return true;
        else if (bn1.size<bn2.size) return false;
        else { // if bn1 and bn2 have same number length
            Node currentNode1 = bn1.head;
            Node currentNode2 = bn2.head;
            while (currentNode1!=null){
                if (currentNode1.digit < currentNode2.digit) return false;
                if (currentNode1.digit > currentNode2.digit) return true;
                // if current digits are the same, continue comparing next digit
                currentNode1 = currentNode1.next;
                currentNode2 = currentNode2.next;
            }
            return true;
        }
    }
}
