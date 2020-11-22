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