# Manual 

## How to Start
To use this calculator, clone this repository and run 
`gradle run --console=plain` in the root 
of the repository. Java 17 is required.
```
$ git clone git@github.com:reettap/calculator.git
$ cd calculator
$ gradle run --console=plain
```

## Menu options
- :h for help
- :q to quit

## Calculator Functionality
  
```
Welcome to the calculator!
Write an expression or :h for help

> :h
To use the calculator, just write an expression and press enter!
Supported operators: +-/*
Variables and parenthesis are not supported yet

:h for help
:q to quit

> 3.5-9
answer: -5.5

> 4+-3
answer: 1

> 4 + -3
answer: 1

> 4*-3.3+7.7/2.99
answer: -10.624749163879597

> :q
bye!
```
  
To use the calculator, just give it a mathematical expression. 
Following functionality is supported by this calculator:
- +, -, * /  
- negative and decimal numbers  
- functions: sqrt, sin, min, max (not implemented yet)  
- (parenthesis) (not implemented yet)  
- variables (not implemented yet)  

## Variable names
Variable names can contain alphabet characters from a to z, both 
capital and lowercase, and numbers and dots. The first 
character of the variable name must be an alphabet character. 
Function names (sqrt, sin, min, max) cannot be variable names. 
Variable names are case-sensitive.

### valid variable names:  
result  
result1  
result.new  
RESULT.NEW.FINAL  

### not valid variable names:
max  
123variable  
.cats  
what!!  

## Errors (not implemented yet)
- invalid token e.g. unrecognized character
- parsing error e.g. missing parenthesis
-  executing error e.g. dividing by zero