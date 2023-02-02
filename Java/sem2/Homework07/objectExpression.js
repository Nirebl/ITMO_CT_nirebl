"use strict";

function createElement(obj, toString, evaluate, diff) {
    obj.prototype.toString = toString;
    obj.prototype.evaluate = evaluate;
    obj.prototype.diff = diff;
}

function Const(value) {
    this.value = value;
}

createElement(Const,
    function () {
        return "" + this.value;
    },
    function () {
        return +this.value;
    },
    () => Const.ZERO
);

Const.E = new Const(Math.E);
Const.ZERO = new Const(0);
Const.ONE = new Const(1);

const VARS = {
    "x": 0,
    "y": 1,
    "z": 2
};

function Variable(variableName) {
    this.variableName = variableName;
    this.argIndex = VARS[variableName];
}

createElement(Variable,
    function () {
        return this.variableName;
    },
    function (...args) {
        return args[this.argIndex];
    },
    function (diffVariable) {
        return this.variableName === diffVariable ? Const.ONE : Const.ZERO;
    }
);

function Expression(...args) {
    this.args = args;
}

createElement(Expression,
    function () {
        return this.args.map(f => f.toString()).join(" ") + " " + this.expSign;
    },
    function (...params) {
        return this.operation(...this.args.map(f => f.evaluate(...params)));
    },
    function (diffVariable) {
        return this.diffFunction(diffVariable, ...this.args)
    }
);

const EXPRESSIONS = {}

function createExpression(expSign, func, derivativeFunc) {
    const obj = function (...args) {
        Expression.call(this, ...args);
    };
    obj.prototype = Object.create(Expression.prototype);
    obj.prototype.expSign = expSign;
    obj.prototype.operation = func;
    obj.prototype.diffFunction = derivativeFunc;
    obj.nargs = func.length;

    EXPRESSIONS[expSign] = obj;

    return obj;
}

const Add = createExpression(
    "+",
    (a, b) => a + b,
    (diffVariable, a, b) => new Add(a.diff(diffVariable), b.diff(diffVariable))
);


const Subtract = createExpression(
    "-",
    (a, b) => a - b,
    (diffVariable, a, b) => new Subtract(a.diff(diffVariable), b.diff(diffVariable))
);

const Multiply = createExpression(
    "*",
    (a, b) => a * b,
    (diffVariable, a, b) => new Add(
        new Multiply(a.diff(diffVariable), b),//неверно, нужно сразу передавать продифференцированные функции
        new Multiply(a, b.diff(diffVariable)))
);


const Divide = createExpression(
    "/",
    (a, b) => a / b,
    (diffVariable, a, b) => new Divide(
        new Subtract(
            new Multiply(a.diff(diffVariable), b),
            new Multiply(a, b.diff(diffVariable))
        ),
        new Multiply(b, b))
);

const Negate = createExpression(
    "negate",
    a => -a,
    function (diffVariable, a) {
        return new Negate(a.diff(diffVariable));
    }
);

const Power = createExpression(
    "pow",
    (a, b) => Math.pow(a, b),
    (diffVariable, a, b) => new Multiply(
        new Power(a, new Subtract(b, Const.ONE)),
        new Add(
            new Multiply(b, a.diff(diffVariable)),
            new Multiply(a, new Multiply(new Log(Const.E, a), b.diff(diffVariable)))
        )
    )
);

const Pow = Power;

const Log = createExpression(
    "log",
    (a, b) => (Math.log(Math.abs(b)) / Math.log(Math.abs(a))),
    (diffVariable, a, b) => new Divide(
        new Subtract(
            new Multiply(new Multiply(a, new Log(Const.E, a)), b.diff(diffVariable)),
            new Multiply(b, new Multiply(a.diff(diffVariable), new Log(Const.E, b)))
        ),
        new Multiply(a,
            new Multiply(b, new Multiply(
                new Log(Const.E, a),
                new Log(Const.E, a))
            )
        )
    )
);

function parse(expression) {
    let stack = [];
    expression.split(" ").filter(x => x.length > 0).forEach(token => {
        if (token in EXPRESSIONS) {
            const curExpression = EXPRESSIONS[token];
            stack.push(new curExpression(...stack.splice(-curExpression.nargs)));
        } else if (token in VARS) {
            stack.push(new Variable(token));
        } else {
            stack.push(new Const(+token));
        }

    });

    return stack.pop()
}