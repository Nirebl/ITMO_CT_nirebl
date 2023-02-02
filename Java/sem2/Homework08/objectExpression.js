"use strict";

function createElement(obj, toString, evaluate, diff, prefix = toString, postfix = toString) {
    obj.prototype.toString = toString;
    obj.prototype.evaluate = evaluate;
    obj.prototype.diff = diff;
    obj.prototype.prefix = prefix;
    obj.prototype.postfix = postfix;
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
Const.TWO = new Const(2);

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
    },
    function () {
        return "(" + this.expSign + " " + this.args.map(f => f.prefix()).join(" ") + ")";
    },
    function () {
        return "(" + this.args.map(f => f.postfix()).join(" ") + " " + this.expSign + ")";
    }
);

const EXPRESSIONS = {}

function createExpression(expSign, func, derivativeFunc) {
    const obj = function (...args) {
        Expression.call(this, ...args);
    };
    obj.prototype = Object.create(Expression.prototype);
    obj.prototype.constructor = obj;
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
        new Multiply(a.diff(diffVariable), b),
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

const Mean = createExpression(
    "mean",
    (...args) => args.length === 0 ? 0 : args.reduce((a, b) => a + b, 0) / args.length,
    (v, ...args) => new Mean(...args.map(f => f.diff(v)))
);

const Var = createExpression(
    "var",
    (...args) => {
        const mean = args.reduce((a, b) => a + b, 0) / args.length;
        return args.length === 0 ? 0 : args.reduce((a, b) => a + (b - mean) * (b - mean), 0) / args.length;
    },
    (v, ...args) => {
        const mean = new Mean(...args);
        return new Multiply(Const.TWO,
            new Mean(...args.map(f =>
                    new Multiply(
                        new Subtract(f, mean),
                        new Subtract(f.diff(v), mean.diff(v))
                    )
                )
            )
        );
    }
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

function ParsingException(message) {
    this.message = message;
}

ParsingException.prototype = Object.create(Error.prototype);
ParsingException.prototype.name = "ParsingException";
ParsingException.prototype.constructor = ParsingException;

function createParsingException(name, errorMsgBuilderFunc) {
    function Error(...args) {
        ParsingException.call(this, errorMsgBuilderFunc(...args));
    }

    Error.prototype = Object.create(ParsingException.prototype);
    Error.prototype.name = name;
    Error.prototype.constructor = ParsingException;
    return Error;
}

const MissingBracketException = createParsingException("MissingBracketException",
    (pos, foundToken) => "Missing closing bracket ')' at pos " + pos + " but found symbol" + foundToken);
const IncorrectOperationException = createParsingException("IncorrectOperationException",
    (pos, foundToken) => "Invalid operation sign at pos " + pos + ", found  symbol '" + foundToken + "'");
const UnexpectedArgsCountException = createParsingException("UnexpectedArgsCountException",
    (pos, operand, foundCount, expectedCount) => "For operation '"  + operand + "' at pos '" + pos +
        " expected '" + expectedCount + " arguments 'but found '" + foundCount + "'");
const InvalidSymbolException = createParsingException("InvalidSymbolException",
    (pos, foundToken) => "Invalid symbol '" + foundToken + "' at pos " + pos);

function ParserImpl(source, separators = []) {
    let _pos = 0;
    this.getPos = () => _pos;
    this.incPos = (n = 1) => _pos += n;
    this.getNext = (n = 1) => {
        return source.slice(_pos, _pos += n);
    };
    this.getSource = () => source;
    this.isSeparator = (c) => separators.includes(c);

    this.hasNext = function hasNext (n = 1) {
        return this.getPos() + n <= this.getSource().length;
    };

    this.skipWhitespaces = function () {
        const source = this.getSource();
        while (this.getPos() < source.length && source[this.getPos()].trim() === "") {
            this.incPos();
        }
    };

    this.test = function (expectedToken) {
        this.skipWhitespaces();
        const source = this.getSource();
        const pos = this.getPos();
        if (expectedToken.length > source.length - pos) {
            return false;
        }
        let ans = true;
        for (let i = 0; i < expectedToken.length; i++) {
            ans = ans && (expectedToken[i] === source[pos + i]);
        }
        return ans;
    };

    this.parseToken = function () {
        this.skipWhitespaces();
        let token = "";
        const source = this.getSource();
        if (this.hasNext() && this.isSeparator(source[this.getPos()])) {
            token = this.getNext();
        } else {
            while (this.hasNext() && !(this.isSeparator(source[this.getPos()]))) {
                token += this.getNext();
            }
        }
        return token;
    };

    this.viewToken = function () {
        let token = this.parseToken();
        this.incPos(-token.length);
        return token;
    };
}

function parseExpression(expression, mode) {
    const parser = new ParserImpl(expression.trim(), [" ", "(", ")"]);

    function parseArgument(token) {
        let result;
        if (token === "(") {
            result = parseExpression();
            token = parser.parseToken();
            if (token !== ")") {
                throw new MissingBracketException(parser.getPos(), token);
            }
        } else if (token in VARS) {
            result = new Variable(token);
        } else if (!isNaN(+token)) {
            result = new Const(+token);
        } else {
            throw new InvalidSymbolException(parser.getPos(), token);
        }
        return result;
    }

    function parseArgs() {
        let operationArgs = [];
        while (parser.hasNext() && !parser.test(")") && !(parser.viewToken() in EXPRESSIONS)) {
            operationArgs.push(parseArgument(parser.parseToken()))
        }
        return operationArgs;
    }

    function parseOperation() {
        const token = parser.parseToken();
        if (!(token in EXPRESSIONS)) {
            throw new IncorrectOperationException(parser.getPos(), token);
        }
        return EXPRESSIONS[token];
    }

    function parseExpression() {
        let operationArgs, curOperation;
        if (mode === "prefix") {
            curOperation = parseOperation();
            operationArgs = parseArgs();
        } else {
            operationArgs = parseArgs();
            curOperation = parseOperation();
        }
        const argsLen = curOperation.prototype.operation.length;
        if (argsLen !== 0 && operationArgs.length !== argsLen) {
            throw new UnexpectedArgsCountException(parser.getPos(), curOperation.prototype.expSign,
                operationArgs.length, argsLen);
        }
        return new curOperation(...operationArgs);
    }

    function parse() {
        if (expression.trim().length === 0) {
            throw new IncorrectOperationException(0, "");
        }
        let result = parseArgument(parser.parseToken());
        if (parser.hasNext()) {
            throw new ParsingException("Unexpected symbols at end of expression");
        } else {
            return result;
        }
    }

    return parse();
}

const parsePrefix = (expression) => parseExpression(expression, "prefix");
const parsePostfix = (expression) => parseExpression(expression, "postfix");