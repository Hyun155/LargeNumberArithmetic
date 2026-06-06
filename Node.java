
public class Node {
    
    int digit;  //store a single digit
    Node prev;  //pointer to the previous node
    Node next;  //pointer to the next node
    
    //constructor - to create the node with digit but not links yet
    public Node(int digit) {
        this.digit = digit;
        this.prev = null;
        this.next = null;
    }
}
