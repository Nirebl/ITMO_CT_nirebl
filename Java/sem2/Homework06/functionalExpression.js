"use strict";

const ExpressionBase = operation => (...operands) => (x, y, z) => {
    let result = [];
    operands.map(operand =>
        result.push(operand(x, y, z))
    );
    return operation(...result);
};
const cnst = value => (x, y, z) => value;

const add = ExpressionBase((a, b) => a + b);
const subtract = ExpressionBase((a, b) => a - b);
const multiply = ExpressionBase((a, b) => a * b);
const divide = ExpressionBase((a, b) => a / b);
const negate = ExpressionBase((a) => -a);
const abs = ExpressionBase(a => Math.abs(a));
const iff = ExpressionBase((a, b, c) => {
    return a >= 0 ? b : c;
});
const variable = (name) => (...args) => {
    return args[VARIABLES[name]];
};

const CONSTANTS = {
    "one": 1,
    "two": 2,
};
const one = cnst(1);
const two = cnst(2);

const EXPRESSIONS = {
    "+": [add, 2],
    "-": [subtract, 2],
    "*": [multiply, 2],
    "/": [divide, 2],
    "negate": [negate, 1],
    "abs": [abs, 1],
    "iff": [iff, 3]
};

const pi = cnst(Math.PI);
const e = cnst(Math.E);

const CONST_MAP = {
    "pi": pi,
    "e": e
};

const VARIABLES = {
    "x": 0,
    "y": 1,
    "z": 2,
};

function parse(expression) {
    return (x, y, z) => {
        let tokens = expression.split(" ").filter(e => e.length > 0);
        let stack = [];
        tokens.map(token => {
            if (token in EXPRESSIONS) {
                let args = stack.slice(stack.length - EXPRESSIONS[token][1], stack.length);
                stack = stack.slice(0, stack.length - EXPRESSIONS[token][1]);
                stack.push(EXPRESSIONS[token][0](...args));
            } else if (token in VARIABLES) {
                stack.push(variable(token));
            } else if (token in CONST_MAP) {
                stack.push(CONST_MAP[token]);
            } else {
                stack.push(cnst((token in CONSTANTS) ? CONSTANTS[token] : parseInt(token)));
            }
        });
        return stack.pop()(x, y, z);
    }
};