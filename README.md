# Graded Assignment 1: Java SRPN

## Brief
Whilst performing some maintenance on a legacy system you find that it makes use of a program called SRPN (srpn). SRPN is not documented and no one seems to know who wrote it, so your boss tells you to rewrite it in Java. SRPN is a reverse Polish notation calculator with the extra feature that all arithmetic is saturated, i.e. when it reaches the maximum value that can be stored in a variable, it stays at the maximum rather than wrapping around.

1. Your task is to write a program which matches the functionality of SRPN as closely as possible. Note that this includes not adding or enhancing existing features. 
2. The legacy program and the example code you need to edit are available [here](https://moodle.bath.ac.uk/mod/page/view.php?id=774569).
3. The SRPN code is available in the [repl.it window](https://repl.it/@bathuniversity/Coursework1-SRPN#SRPN.java). This allows you to run and interact with the srpn program.
4. You should start by typing in the 4 tests shown [here](https://moodle.bath.ac.uk/mod/page/view.php?id=774569), observe the output and them implement your code to replicate this functionality.
5. You have 2 weeks in which to complete this coursework. I suggest that:  
This week you complete Test 1 and Test 2.  Try the inputs in the legacy SRPN calculator and observe the outputs.  Start of small by tackling the the first example in Test 1 and write some code that allows you to pass this test.  From there you can build on this simple piece of code and add in functionality to pass the second test.    
Next week you complete Test 3 and Test 4, as well as exploring if there are other features of the SRPN calculator that are not covered in the tests provided.  The challenge this week is to understand some of the interesting features of the SRPN calculator.  For example, what does ‘r’ do?  What does ‘d’ do?  Does the input "1+1” produce the same output as “1 + 1” (note the spaces)?
6. Your program will be tested on the same 4 tests below and others that are similar. Successfully completing each step will give you 15 marks each. The remaining marks 40 marks are for good programming practice include, commenting, program structure etc. for a total of 100.
7. Submit your completed solution at the end of week 6.

## Description
The SRPN (saturated reverse Polish notation calculator) is responsible for handling commands and
generating the appropriate output to display to the user. The SRPN is accessed publicly via the
`SRPN.processCommand(String)` method.

The SRPN recognises various different input types, described in the table below.

| Type              | Symbol/example | Result |
| ----------------- | -------------- | ------ |
| Operand (base 10) | `100`          | An integer value that can be operated upon (100, in this case). |
| Operand (base 8)  | `010`          | An integer value that can be operated upon (8 in this case). Numbers start with a zero and then being proceeded by any character in the range `0-7` will be interpreted as octal. Note that, when displaying this value, the result will always be displayed in base 10. |
| Random number     | `r`            | Generates a random number between 0 and `Integer.MAX_VALUE` that will then be treated as an operand. |
| Equals            | `=`            | Peeks on the stack, or prints `Stack empty.` if the stack is empty. |
| Display stack     | `d`            | Prints the stack from oldest entry to newest, or prints `Integer.MIN_VALUE` if the stack is empty. |
| Addition          | `+`            | Adds two operands together. |
| Subtraction       | `-`            | Subtracts one operand from another. |
| Multiplication    | `*`            | Calculates the product of two operands. |
| Division          | `/`            | Divides one operand by another. |
| Raise to power    | `^`            | Raises one operand to an exponent. |
| Modulo            | `%`            | Calculcates the modulus of one operand by another. |

Note that this calculator is saturated - as a result, any operations that would result in a value
`> Integer.MAX_VALUE` or `< Integer.MIN_VALUE` will be returned as `Integer.MAX_VALUE` and
`Integer.MIN_VALUE` respectively.

This calculator can be used both in reverse Polish notation, and also using common operator
notation. To use reverse Polish notation, the values and operators should be separated by
whitespace or new lines. For example:
```
20 10-5 / =
```
would result in the output of `4`.


### Compilation and Execution Stages
In this implementation, when a command is received, the SRPN processes this through a number of
stages to derive the result.

#### 1. Tokenization
The tokenization stage takes the raw input string and converts this into a sequence of tokens using
an `InputLineTokenizer`. For example
```
1 3 +
```
would become
```
[1] [whitespace] [3] [whitespace] [+]
```

#### 2. Token grouping
Once input has been converted into tokens, these tokens are then split into command groups. Command
groups, typically separated by whitespace, are always executed one group at a time, thus the first
group must be executed and completed before the second group can be executed. Executing a token
group is equivalent to executing an inout line containing only the tokens from that group, i.e.
```
{tokenGroup1} {tokenGroup2}
```
is equivalent to executing two separate commands:
```
{tokenGroup1}
{tokenGroup2}
```
Tokens are split into groups using a `TokenGrouper`.

#### 3. Token group execution
Once the tokens have been grouped, each group needs to be executed. The SRPN uses a stack of
operands that can then be operated upon or added to as each token in the group is applied. The
tokens include their own instructions regarding how they should affect the stack. The execution
is completed using a `TokenGroupExecutor`.</p>