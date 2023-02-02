package expression.exceptions;

import expression.*;
import expression.parserbase.CharSource;
import expression.parserbase.ParserBase;

public class CheckedExpressionParserImpl extends ParserBase {

    public CheckedExpressionParserImpl(CharSource source) {
        super(source);
    }

    public TripleExpression parse() throws ParsingException {
        MultiExpressionElement el = parseLowPriority(0);

        return el;
    }

    private MultiExpressionElement parseLowPriority(int level) throws ParsingException {
        MultiExpressionElement left = parseMiddlePriority(level);

        skipWhitespace();

        char ch = current();
        while (ch != END) {
            switch (ch) {
                case '+':
                    take();
                    left = new CheckedAdd(left, parseMiddlePriority(level));
                    break;
                case '-':
                    take();
                    left = new CheckedSubtract(left, parseMiddlePriority(level));
                    break;
                case 'm':
                    take();
                    char secSymbol = take();
                    if (secSymbol == 'i') {
                        expect('n');
                        left = new MinExpression(left, parseLowPriority(level));
                    } else if (secSymbol == 'a') {
                        expect('x');
                        left = new MaxExpression(left, parseLowPriority(level));
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

    private MultiExpressionElement parseMiddlePriority(int level) throws ParsingException {
        MultiExpressionElement left = parseHighPriority(level);

        skipWhitespace();

        char ch = current();
        while (ch != END) {
            switch (ch) {
                case '*':
                    take();
/*                    if (test('*')) {
                        take();
                        left = new POW(left, parseValuesPriority(level));
                    } else

 */
                        left = new CheckedMultiply(left, parseHighPriority(level));
                    break;
                case '/':
                    take();
   /*                 if (test('/')) {
                        take();
                        left = new LOG(left, parseValuesPriority(level));
                    } else

    */
                        left = new CheckedDivide(left, parseHighPriority(level));
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


    private MultiExpressionElement parseHighPriority(int level) throws ParsingException {
        MultiExpressionElement left = parseValuesPriority(level);

        skipWhitespace();

        char ch = current();
        while (ch != END) {
            switch (ch) {
                case '*':
                    take();
                    if (!test('*')) {
                        back();
                        return left;
                    }
                    take();
                    left = new POW(left, parseValuesPriority(level));
                    break;
                case '/':
                    take();
                    if (!test('/')) {
                        back();
                        return left;
                    }

                    take();
                    left = new LOG(left, parseValuesPriority(level));
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

    private MultiExpressionElement parseValuesPriority(int level) throws ParsingException {
        skipWhitespace();

        MultiExpressionElement el = null;

        if (testAnyFromString("xyz")) {
            return parseVariable();
        }

        if (between('0', '9')) {
            el = parseConst(false);

            if(test('m'))
                throw error("Unexpected symbol");

            return el;
        }

        char ch = current();
        switch (ch) {
            case '(':
                take();
                el = parseLowPriority(level + 1);
                expect(')');
                break;
            case '-':
                take();
                if (between('0', '9')) {
                    el = parseConst(true);
                } else {
                    el = parseValuesPriority(level);
                    el = new CheckedNegate(el);
                }
                break;
            case 'l':
                take();
                expect('0');
                el = parseValuesPriority(level);
                el = new ZeroesHight(el);
                break;
            case 't':
                take();
                expect('0');
                el = parseValuesPriority(level);
                el = new ZeroesLow(el);
                break;
            case 'a':
                take();
                expect("bs");
                if(!(test(' ') || test('(') ))
                    throw error("Unexpected symbol after abs " + current());

                //expect('s');
                el = parseValuesPriority(level);
                el = new ABS(el);
                break;
            default:
                throw error("Unexpected symbol " + ch);

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
