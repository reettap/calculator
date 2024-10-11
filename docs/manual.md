# Manual 

## Get started
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
- :v to list variables
- :q to quit

```
Welcome to the calculator!
Write an expression or :h for help

> :h
To use the calculator, just write an expression and press enter!
Supports basic arithmetics: +, -, * /
Supports functions: min, max, sqrt, sin
Examples:
(2-4)*6+sin(7.8)/9
-(min(3 6))
-(max(3 (-9.6)))
sin(9.9)
-sqrt(27)

Variables:
Variables start with an alphabet character a-z, lowercase or uppercase
Variables can also contain numbers and dots
The latest result is stored in special variable ans
=a or a= will assign the most recent result to a
Examples:
cats = 7
DOGS = cats+7
= animals1

:h for help
:v to list variables
:q to quit
```

## Calculator Functionality  

### basic arithmetics
- +, -, *, /
- ()
```
> 3.5-9
answer: -5.5

> 4+-3
answer: 1

> 4 + -3
answer: 1

> 4*-3.3+7.7/2.99
answer: -10.624749163879597

> (1.4+2.2)/8
ans: 0.45

> 99/(6-6)
Error: Dividing by zero
```


### functions
- Square root: sqrt(x)  
- sine: sin(x)  
- minimum: min(x y)  
- maximum: max(x y)  
`note! `  when function takes two arguments, they are separated by space. If an 
argument starts with a minus, it must be wrapped in parentheses: `min((-x) (-y))`

```
> sin(8.8)
ans: 0.5849171928917617

> min(1.4 (4*-2))
ans: -8

> sqrt(-4)
Error: Square root is not defined for negative numbers
```
 

## variables
Variable names can contain alphabet characters from a to z, both 
capital and lowercase, and numbers and dots. The first 
character of the variable name must be an alphabet character. 
Function names (sqrt, sin, min, max) cannot be variable names. 
Variable names are case-sensitive. `ans` is a special variable 
holding the result of the most recent calculation.

```
> cats = 3
cats: 3

> dogs = cats + 5
dogs: 8

> cats+dogs
ans: 11

> =animals
animals: 11

> hedgehogs + 8
Error: Variable hedgehogs not found

> SHARKS!!! = 999*999
Error: Variable name can not contain this character: !

> ans
ans: 998001

> :v
cats: 3
ans: 998001
dogs: 8
animals: 11
```


## Errors 
The calculator will report following errors:
- division by zero
- negative square root
- mismatched parenthesis
- uninitialized variable
- too many operands for the given operators
- too few operands for the given operators
- unrecognised character
- a number containing more than one decimal separator
- variable name can not be reserved function name
- variable name has to be of the required format
- variable assignment can only include one '='