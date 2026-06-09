public class BigNumber {
    // Doubly linked list that stores one digit per node.
    Node head;
    Node tail;
    int size = 0;
    boolean isNegative = false;
    // Number of digits after the decimal point.
    int decimalPosition = 0;
    
    //Constructor1 - create an empty big number 
    public BigNumber() {
        this.head =null;
        this.tail =null;
    }
    
    //Constructor2 - Create a BigNumber from a String
    public BigNumber(String number) {
        this.head = null;
        this.tail = null;
        if (number == null || number.length() == 0) return;

        int start = 0;
        // Allow an optional leading sign when reading input.
        if (number.charAt(0) == '-') {
            isNegative = true;
            start = 1;
        } else if (number.charAt(0) == '+') {
            start = 1;
        }

        // Store each digit as its own node.
        for (int i = start; i < number.length(); i++) {
            char c = number.charAt(i);
            if (c < '0' || c > '9') continue; // ignore any non-digit (simple approach)
            int digit = c - '0';
            append(digit);
        }
    }
        
    // -------------------------------------------------------
    // METHOD: append(int digit)
    // Adds a new node at the TAIL of the linked list
    // Handles both empty list and normal cases
    // -------------------------------------------------------
    public void append(int digit) {
            
            // Create a brand new node with this digit
            Node newNode = new Node(digit);
            
            if(head == null) {
                //List is empty — this node is both head and tail
                head = newNode;
                tail = newNode;
            } else {
                // Connect new node after current tail
                newNode.prev = tail;   // new node looks back at old tail
                tail.next = newNode;   // old tail looks forward to new node
                tail = newNode;        // update tail to be the new node
            }
            // increment size for every append
            size++;
        }
    

    // Add a new node at the HEAD of the linked list
    public void prepend(int digit) {
        Node newNode = new Node(digit);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // Remove structural leading zeros (e.g., turn 0034 into 34)
    public void removeLeadingZeros() {
        // Remove extra nodes at the front, but keep one zero if the number is zero.
        while (head != null && head.digit == 0 && head != tail) {
            head = head.next;
            if (head != null) head.prev = null;
            size--;
        }
    }
    @Override
    // Traverses from head to tail and builds the number string
    public String toString() {
        
        // If the list is empty, represent it as zero.
        if(head == null) return "0";
        
        StringBuilder str = new StringBuilder();
        Node current = head;
        
        // Build the digit string first, then place the decimal point if needed.
        StringBuilder digits = new StringBuilder();
        while (current != null) {
            digits.append(current.digit);
            current = current.next;
        }

        // Add the sign before the numeric part.
        if (isNegative) str.append("-");

        if (decimalPosition <= 0) {
            str.append(digits.toString());
            return str.toString();
        }

        int total = digits.length();
        if (total <= decimalPosition) {
            // number is less than 1, need leading zero and extra zeros
            str.append("0.");
            for (int i = 0; i < decimalPosition - total; i++) str.append('0');

            str.append(digits.toString());
            return str.toString();
        }

        // split integer and fractional parts
        int split = total - decimalPosition;
        str.append(digits.substring(0, split));
        str.append('.');

        str.append(digits.substring(split));

        return str.toString();
    }
}
