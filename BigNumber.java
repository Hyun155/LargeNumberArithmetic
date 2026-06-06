/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.largenumberarithmetic;

/**
 *
 * @author HP
 */
public class BigNumber {
    Node head;
    Node tail;
    int size = 0;
    boolean isNegative = false;
    
    //Constructor1 - create an empty big number 
    public BigNumber() {
        this.head =null;
        this.tail =null;
    }
    
    //Constructor2 - Create a BigNumber from a String
    public BigNumber(String number) {
        this.head = null;
        this.tail = null;
        
        // Loop through each character in the String
        for(int i=0; i<number.length(); i++) {
            
            //convert char to integer
            int digit = number.charAt(i) - '0';
            
            //Add this digit as a new node at the tail
            append(digit);
            
            // Increment size
            size++;
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
                size ++;               // increment size
            }       
        }
    
    @Override
    // Traverses from head to tail and builds the number string
    public String toString() {
        
        //if the list is empty, represent as "0"
        if(head == null) return "0";
        
        StringBuilder str = new StringBuilder();
        Node current = head;
        
        // if the number is negative, add negative sign
        if (isNegative) str.append("-");
        
        // Walk from head to tail, appending each digit
        while(current != null) {
            str.append(current.digit);
            current = current.next;
        }
        return str.toString();
    }
}
