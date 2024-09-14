# Weekly report 2

8.-14.9.

time spent: 13 hours

## What did I do this week?
- migrated to gradle
- made the program work correctly for basic sum expressions
- program has now the class structure I drafted last week
- got started on unit tests

## What can it do now?
```
Welcome to the calculator!

write an expression or :h for help
> :h
good luck!

write an expression or :h for help
> 2 + 3
answer: 5.0

write an expression or :h for help
> 2
answer: 2

write an expression or :h for help
> -2 + 5
answer: 3.0

write an expression or :h for help
> 999 + 1.2
answer: 1000.2

write an expression or :h for help
> 1 + 2 + 3 + 4 + 5
answer: 15.0

write an expression or :h for help
> :q
bye!
```

## How did the project advance?
The program works now end-to-end, and has approximately the expected class structure. 
The shunting yard algorithm is now applied on sums and numbers, 
and the evaluator also warks for these sums

## What did I learn?
A nice little recap to Java and got to learn about Gradle. 

## Anything difficult or unclear?
The last part of the gradle instructions, how to add JUnit tests in the project, didn't work as described. 
Took some time to figure out what had changed on how newer gradle version handles testing.

## What next?
- Now only the evaluator has unit tests. Add unit tests to the other parts too. 
  - This is needed for the refactoring.
- The token class got messy, separate the different types of tokens. 
  - This is needed for expanding the functionality. 
- Add more operators
- Once the structure settles a bit, get serious about documenting.


