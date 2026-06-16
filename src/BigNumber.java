public class BigNumber {
    // Head pointer to the most-significant digit node.
    Node head;
    // Tail pointer to the least-significant digit node.
    Node tail;
    // Number of digit nodes currently stored in the list.
    int size = 0;
    // Sign flag: true means the represented number is negative.
    boolean isNegative = false;
    // Count of digits to render after the decimal point in toString().
    int decimalPosition = 0;
    
    //Constructor1 - create an empty big number 
    public BigNumber() {
        this.head =null;   // Empty list has no head node.
        this.tail =null;   // Empty list has no tail node.
        this.size = 0;     // Explicitly initialize size metadata.
    }
    
    //Constructor2 - Create a BigNumber from a String
    public BigNumber(String number) {
        this.head = null;  // Start with an empty digit list.
        this.tail = null;
        this.size = 0;
        if (number == null || number.length() == 0) return; // Keep default zero-state for empty input.

        int start = 0; // Index where digit parsing should begin.
        // Allow an optional leading sign when reading input.
        if (number.charAt(0) == '-') {
            isNegative = true;
            start = 1;
        } else if (number.charAt(0) == '+') {
            start = 1;
        }

        // Store each digit as its own node.
        for (int i = start; i < number.length(); i++) {
            char c = number.charAt(i);                // Read one source character.
            if (c < '0' || c > '9') continue; // ignore any non-digit (simple approach)
            int digit = c - '0';                     // Convert ASCII char to numeric digit.
            append(digit);                           // Preserve left-to-right order in the list.
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
        Node newNode = new Node(digit); // New node becomes the new most-significant digit.
        if (head == null) {
            // Empty list case: head and tail must both point to this node.
            head = newNode;
            tail = newNode;
        } else {
            // Non-empty list case: stitch new node before old head.
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++; // Track node count after insertion.
    }

    // Remove structural leading zeros (e.g., turn 0034 into 34)
    public void removeLeadingZeros() {
        // Remove extra nodes at the front, but keep one zero if the number is zero.
        while (head != null && head.digit == 0 && head != tail) {
            head = head.next;                // Move head forward by one node.
            if (head != null) head.prev = null; // New head has no previous node.
            size--;                          // Keep list size consistent with removed node.
        }
    }
    @Override
    // Traverses from head to tail and builds the number string
    public String toString() {
        
        // If the list is empty, represent it as zero.
        if(head == null) return "0";
        
        StringBuilder str = new StringBuilder(); // Final formatted output, including sign/decimal point.
        Node current = head;                     // Traverse stored digits from most to least significant.
        
        // Build the digit string first, then place the decimal point if needed.
        StringBuilder digits = new StringBuilder(); // Raw contiguous digit sequence.
        while (current != null) {
            digits.append(current.digit);
            current = current.next;
        }

        // Add the sign before the numeric part.
        if (isNegative) str.append("-");

        if (decimalPosition <= 0) {
            // Integer representation: append all digits directly.
            str.append(digits.toString());
            return str.toString();
        }

        int total = digits.length(); // Total raw digit count before decimal insertion.
        if (total <= decimalPosition) {
            // number is less than 1, need leading zero and extra zeros
            str.append("0.");
            for (int i = 0; i < decimalPosition - total; i++) str.append('0');

            str.append(digits.toString());
            return str.toString();
        }

        // split integer and fractional parts
        int split = total - decimalPosition; // Boundary index between integer/fractional digits.
        str.append(digits.substring(0, split));
        str.append('.');

        str.append(digits.substring(split));

        return str.toString();
    }
}
