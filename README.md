# LargeNumberArithmetic

## Overview

The Large Number Arithmetic System is a Java-based application designed to perform arithmetic operations on integers that exceed the limits of primitive data types. The system uses a custom linked-list-based structure to represent large numbers and supports efficient computation through modularized components.

This project demonstrates the application of data structures, object-oriented programming principles, and algorithmic problem-solving techniques in handling large integer operations.

---

## Features

- Addition of arbitrarily large integers  
- Subtraction of arbitrarily large integers  
- Multiplication of large numbers  
- Division of large numbers  
- Linked-list-based number representation  
- Modular and scalable architecture  

---

# 📁 Project Structure

```bash
LargeNumberArithmetic/
├── Node.java              # Core link structures (Member 1)
├── Helper.java            # Utilities & validation checks (Member 2)
├── BigNumber.java         # Storage, parsing, and display foundation (Member 1)
├── BasicArithmetic.java   # Addition & Subtraction (Member 3)
├── AdvancedArithmetic.java # Multiplication & Division support (Member 4)
└── Main.java              # System Integration & entry test hub (Member 5)
```

System Design

The system represents large integers using a linked list, where each node stores a single digit. This allows arithmetic operations on numbers of arbitrary size.

Core Components

Node

Represents a single digit in the linked list

BigNumber

Handles parsing, storage, and formatting of large integers

Helper

Provides validation and reusable utility functions

BasicArithmetic

Implements addition and subtraction algorithms

AdvancedArithmetic

Implements multiplication and division algorithms

Main

Entry point of the program
Integrates all components and handles execution flow
How It Works
Input numbers are parsed into a linked-list representation
Each digit is stored in a separate node
Arithmetic operations are performed digit by digit
Results are constructed and returned as a new BigNumber object

This design removes limitations of Java primitive data types such as int and long.

Requirements
Java Development Kit (JDK 8 or higher)
How to Run
1. Compile the program
javac *.java
2. Run the program
java Main
Example Usage
Input A: 999999999999999999
Input B: 123456789123456789

Addition Result: 1123456789123456788
Subtraction Result: 876543210876543210
Multiplication Result: (computed large value)
Division Result: (computed large value)
Key Concepts Used
Linked List Data Structure
Object-Oriented Programming (OOP)
Modular Software Architecture
Big Integer Arithmetic Algorithms
Input Validation and Error Handling
