# LargeNumberArithmetic

LargeNumberArithmetic is a simple Java project for doing arithmetic with numbers that are too large for primitive types. It stores each digit in a doubly linked list, which makes it easier to build custom addition, subtraction, multiplication, and division logic.

## What It Does

- Adds large integers
- Subtracts large integers
- Multiplies large integers
- Divides large integers and prints a decimal result
- Supports optional negative input values
- Uses a custom linked-list number structure instead of built-in big integer libraries

## Current Behavior

- Input is read from the console in `Main.java`
- Numbers may start with `-`
- Division prints a plain decimal result with fixed precision, not a repeating-decimal format
- Division by zero is rejected
- The project does not use any external libraries

## How It Works

- `Node.java` stores one digit per node
- `BigNumber.java` holds the linked-list number, sign flag, and formatting logic
- `Helper.java` provides comparison, zero checks, copying, and reverse helpers
- `BasicArithmetic.java` handles addition and subtraction
- `AdvancedArithmetic.java` handles multiplication and division
- `Main.java` reads input and prints the result of each operation

## File Structure

```text
LargeNumberArithmetic/
├── Node.java
├── Helper.java
├── BigNumber.java
├── BasicArithmetic.java
├── AdvancedArithmetic.java
├── Main.java
├── README.md
```

## How To Run

Compile the project:

```bash
javac *.java
```

Run the program:

```bash
java Main
```

Then enter two integers when prompted, for example:

```text
12
3
```

## Example Output

```text
addition       = 15
subtraction    = 9
multiplication = 36
division       = 4
```

For division that produces a decimal, the output is shown with a limited number of decimal places, for example:

```text
division       = 0.090909090909090909
```

## Notes

- This project is written in plain Java and does not use imports beyond `Scanner` in `Main.java`.
- The arithmetic is implemented manually, digit by digit.
- The code is kept intentionally simple so it is easier to understand and modify.
