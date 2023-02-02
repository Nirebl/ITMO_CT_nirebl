package parser;

import expression.AbstractElement;
import expression.*;

public final class ExpressionParser<T> extends ParserBase {

    public ExpressionParser(CharSource source) {
        super(source);
    }

    public AbstractElement<T> parse() throws ParsingException {
        return parsePriority1(0);
    }

    private AbstractElement<T> parsePriority1(int level) throws ParsingException {
        var left = parsePriority2(level);

        skipWhitespace();

        char ch = current();
        while (ch != END) {
            switch (ch) {
                case '+':
                    take();
                    left = new Add(left, parsePriority2(level));
                    break;
                case '-':
                    take();
                    left = new Subtract(left, parsePriority2(level));
                    break;
                case 'm':
                    take();
                    char secSymbol = take();
                    if (secSymbol == 'i') {
                        expect('n');
                        left = new MinExpression(left, parsePriority1(level));
                    } else if (secSymbol == 'a') {
                        expect('x');
                        left = new MaxExpression(left, parsePriority1(level));
                    }
                    break;
                case ')':
                    if (level == 0)
                        throw error("Unexpected  closing bracket");
                    return left;
                default:
                    throw error("Unexpected symbol " + ch);
            }
            ch = current();
        }

        return left;
    }

    private AbstractElement<T> parsePriority2(int level) throws ParsingException {
        var left = parsePriority3(level);

        skipWhitespace();

        char ch = current();
        while (ch != END) {
            switch (ch) {
                case '*':
                    take();
                    left = new Multiply(left, parsePriority3(level));
                    break;
                case '/':
                    take();
                    left = new Divide(left, parsePriority3(level));
                    break;
                case ')':
                    if (level == 0)
                        throw error("Unexpected  closing bracket");
                    return left;
                case '+':
                case '-':
                case 'm':
                    return left;
                default:
                    throw error("Unexpected symbol " + ch);

            }
            skipWhitespace();
            ch = current();
        }

        return left;
    }

    private AbstractElement<T> parsePriority3(int level) throws ParsingException {
        skipWhitespace();

        AbstractElement<T> el = null;

        if (test('x') || test('y') || test('z')) {
            return parseVariable();
        }

        if (between('0', '9')) {
            el = parseConst(false);

            if (test('m'))
                throw error("Unexpected symbol");

            return el;
        }

        char ch = current();
        switch (ch) {
            case '(':
                take();
                el = parsePriority1(level + 1);
                expect(')');
                break;
            case '-':
                take();
                if (between('0', '9'))
                    el = parseConst(true);
                else
                    el = new UnaryMinus<>(parsePriority3(level));
                break;

            case 'c':
                take();
                expect("ount");
                if (!(test(' ') || test('(')))
                    throw error("Unexpected symbol after count " + current());

                el = new Count<>(parsePriority3(level));
                break;
            default:
                throw error("Unexpected symbol " + ch);

        }
        skipWhitespace();
        return el;
    }


    private AbstractElement<T> parseVariable() {
        return new Variable(Character.toString(take()));
    }

    private AbstractElement<T> parseConst(boolean isNegative) {
        StringBuilder sb = new StringBuilder();
        if (isNegative)
            sb.append("-");

        while (between('0', '9')) {
            sb.append(take());
        }

        return new Const<T>(sb.toString());
    }
}
