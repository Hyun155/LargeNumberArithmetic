/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.largenumberarithmetic;

/**
 *
 * @author HP
 */
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
