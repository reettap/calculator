# Implementation Document

The user interface is provided by the `CalculatorUI` class, which delecates 
calculating the expressions to the `Calculator` class. `Calculator` also 
manages the variables, which are stored as a HashMap of variable-value pairs. 

The `Calculator.calculate` method orchestrates the three phases of 
interpreting and calculating the expression: `tokenizing`, `parsing` 
and `evaluating`. Each of these parts is implemented as a static 
class with a corresponding name. The intermediary format is an ArrayDeque 
containing `Token` objects. A `Token` represents an `Operator` or a `Value` 
in the expression.

`Tokenizer` takes in a String representing the mathematical expression, 
and traverses it character by character, producing an ArrayDeque of tokens 
in the same order as they are introduced in the expression.

`Parser` takes in an ArrayDeque of the tokens produced by the Tokenizer. 
It uses the `Shunting Yard Algorithm` to produce an ArrayDeque of Tokens 
representing the reverse polish notation of the original expression.

`Evaluator` takes in the ArrayDeque tokens produced by Parser. It then 
evaluates the reverse polish notation, resolves the operators to Java operations 
and produces the final result. 

At each phase there are specific errors that can be recognized: 
- Tokenizer can recognize unexpected characters
- Parser notices mismatched parentheses
- Evaluator reports on illegal operations and mismatched numbers of operators and operands.    

Each or these errors throws a specific error with a descriptive error 
message, which gets propagated all the way to the user interface, 
where the message is shown to the user instead of the result.

## Time and Space Complexity
- O(n) Time: each of the three phases in the calculation is linear time: 
the list of tokens is traversed (linear operation) and each token is hadled with constant time operations.
- O(n) Space: the number of the tokens introduced is up to the 
number of the characters in the input. The same tokens will be passed 
on until the evaluation, where they get reduced to the single 
token representing the result.  

## Limitations and Possible Improvements
The greatest limitation of this calculator is, that the shunting yard algorithm 
does recognize all valid expressions, but cannot recognize all types 
of invalid ones. 
For example `2 2 2 + +` is considered valid and yields `6`, as the algorithm has 
no regards of the relative positions of the operators and the arguments, 
only their relative number. The expected positions get resolved at the evaluation, 
where each operator takes its operands from a stack of values where they 
have been accumulated. `2 3 1 + -` yields the same reverse polish 
notation as `2 - (3 + 1)`, and is evaluated as such.  

Floating point arithmetics can yield unprecise results.  

A possible next step could be to show visually where in the expression 
the encountered error is. 


## Large Language Models
LLM:s have not been utilized in this project.

## References
https://en.wikipedia.org/wiki/Shunting_yard_algorithm  
https://mathcenter.oxford.emory.edu/site/cs171/shuntingYardAlgorithm/  
https://blog.devgenius.io/problems-with-a-shunting-yard-algorithm-95cc462dc750  