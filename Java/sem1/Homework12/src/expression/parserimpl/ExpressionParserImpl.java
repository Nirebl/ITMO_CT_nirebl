package expression.parserimpl;

import expression.*;
import expression.TripleExpression;
import expression.parserimpl.factories.BinaryMultiExpressionFactory;

public class ExpressionParserImpl extends ParserBase {
    private String currentExpressionMark = ")";

    public ExpressionParserImpl(CharSource source) {
        super(source);
    }

    private MultiExpressionElement parseVariable() {
        if (testAnyFromString("xyz"))
            return new Variable(Character.toString(take()));

        throw error("Illegal symbol");
    }

    private String parseStrNumber() {
        skipWhitespace();

        StringBuilder sb = new StringBuilder();
        while (between('0', '9')) {
            sb.append(take());
        }

        return sb.toString();
    }

    private MultiExpressionElement parseConst(boolean isNegative) {
        StringBuilder sb = new StringBuilder();
        if (isNegative)
            sb.append("-");

        sb.append(parseStrNumber());

        try {
            return new Const(Integer.parseInt(sb.toString()));
        } catch (NumberFormatException e) {
            throw error("Illegal constant :" + sb);
        }
    }

    private MultiExpressionElement parseZeroHigh() {
        take();
        expect('0');

        return new ZeroesHight(parseExpression(Priority.Lowest));
    }

    private MultiExpressionElement parseZeroLow() {
        take();
        expect('0');

        return new ZeroesLow(parseExpression(Priority.Lowest));
    }


    private MultiExpressionElement parseMinus() {
        //take();
        return new UnaryMinus(parseExpression(Priority.Lowest));
    }

    private MultiExpressionElement parseElementExpression() {
        skipWhitespace();

        MultiExpressionElement element = null;

        if (testAnyFromString("xyz")) {
            element = parseVariable();
            parseExpressionMark();
        } else if (test('-')) {
            take();
            if (between('0', '9')) {
                element = parseConst(true);
                parseExpressionMark();
            }
            else {
                element = parseMinus();
            }
        } else if (between('0', '9')) {
            element = parseConst(false);
            parseExpressionMark();
        } else if (test('l')) {
            element = parseZeroHigh();
            //parseExpressionMark();
        } else if (test('t')) {
            element = parseZeroLow();
            //parseExpressionMark();
        } else if (test('(')) {
            take();
            element = parseExpression(Priority.Highest);
        }

        skipWhitespace();

        if (element == null)
            throw error("Unsupported element");

        return element;
    }

    private void parseExpressionMark() {
        skipWhitespace();

        char firstMark = take();
        switch (firstMark) {
            case '+':
                currentExpressionMark = "+";
                break;
            case '-':
                currentExpressionMark = "-";
                break;
            case '*':
                currentExpressionMark = "*";
                break;
            case '/':
                currentExpressionMark = "/";
                break;
            case ')':
            case END:
                currentExpressionMark = ")";
                break;
            case 'm':
                char secSymbol = take();
                if (secSymbol == 'i') {
                    expect('n');
                    currentExpressionMark = "min";
                } else if (secSymbol == 'a') {
                    expect('x');
                    currentExpressionMark = "max";
                }
                break;
        }

        error("Unknown operation: " + firstMark);
    }


    private MultiExpressionElement parseBinaryExpression(Priority priority) {
        MultiExpressionElement expression = parseExpression(priority.dec());
        while (Priority.getExpressionPriority(currentExpressionMark).equals(priority)) {
            expression = BinaryMultiExpressionFactory.Create(currentExpressionMark,
                    expression, parseExpression(priority.dec()));
        }
        if (priority.equals(Priority.Highest)) {
            parseExpressionMark();
        }
        return expression;
    }


    private MultiExpressionElement parseExpression(Priority priority) {
        skipWhitespace();

        if (priority.equals(Priority.Lowest)) {
            return parseElementExpression();
        } else {
            return parseBinaryExpression(priority);
        }
    }

    public TripleExpression parse() {
        return parseExpression(Priority.Highest);
    }


}
