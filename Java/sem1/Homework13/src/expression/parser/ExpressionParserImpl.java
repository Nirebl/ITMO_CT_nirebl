package expression.parser;

import expression.*;
import expression.parserbase.CharSource;
import expression.parserbase.ParserBase;

public class ExpressionParserImpl extends ParserBase {
    private static final String variableValues = "xyz";

    public ExpressionParserImpl(CharSource source) {
        super(source);
    }

    public TripleExpression parse() {
        MultiExpressionElement el = parseLowPriority();

        return el;
    }

    private MultiExpressionElement parseLowPriority() {
        MultiExpressionElement left = parseHighPriority();

        char ch = current();
        while(ch != END){
            switch (ch) {
                case '+':
                    take();
                    left = new Add(left, parseHighPriority());
                    break;
                case '-':
                    take();
                    left = new Subtract(left, parseHighPriority());
                    break;
                case 'm':
                    take();
                    char secSymbol = take();
                    if (secSymbol == 'i') {
                        take();
                        left = new MinExpression(left, parseLowPriority());
                    } else if (secSymbol == 'a') {
                        take();
                        left = new MaxExpression(left, parseLowPriority());
                    }
                    break;
                case  ')':
                    return left;
            }
            ch = current();
        }

        return left;
    }

    private MultiExpressionElement parseHighPriority() {
        MultiExpressionElement left = parseValuesPriority();

        skipWhitespace();

        char ch = current();
        while(ch != END){
            switch (ch) {
                case '*':
                    take();
                    left = new Multiply(left, parseValuesPriority());
                    break;
                case '/':
                    take();
                    left = new Divide(left, parseValuesPriority());
                    break;
                case  ')':
                case  '+':
                case  '-':
                case  'm':
                    return left;
            }
            skipWhitespace();
            ch = current();
        }

        return left;
    }

    private MultiExpressionElement parseValuesPriority() {
        skipWhitespace();

        if (testAnyFromString("xyz")) {
            return parseVariable();
        }

        if (between('0', '9')){
            return parseConst(false);
        }

        MultiExpressionElement el = null;
        char ch = current();
        switch (ch) {
            case '(':
                take();
                el = parseLowPriority();
                take();
                break;
            case '-':
                take();
                if (between('0', '9')) {
                    el = parseConst(true);
                }
                else {
                    el = parseValuesPriority();
                    el = new UnaryMinus(el);
                }
                break;
            case 'l':
                take();
                take();
                el = parseValuesPriority();
                el = new ZeroesHight(el);
                break;
            case 't':
                take();
                take();
                el = parseValuesPriority();
                el = new ZeroesLow(el);
                break;
        }

        skipWhitespace();

        return el;
    }


    private MultiExpressionElement parseVariable() {
        return new Variable(Character.toString(take()));
    }

    private MultiExpressionElement parseConst(boolean isNegative) {
        StringBuilder sb = new StringBuilder();
        if (isNegative)
            sb.append("-");

        while (between('0', '9')) {
            sb.append(take());
        }

        return new Const(Integer.parseInt(sb.toString()));
    }

}
