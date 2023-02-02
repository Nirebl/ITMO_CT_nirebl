package expression.exceptions;

import expression.TripleExpression;
import expression.parserbase.StringSource;

public class ExpressionParser implements Parser {
    public static String expr = "";

    @Override
    public TripleExpression parse(String expression) throws ParsingException {
        expr = expression;

        CheckedExpressionParserImpl parser = new CheckedExpressionParserImpl(new StringSource(expression));

        TripleExpression exp = parser.parse();
        return exp;
    }
}
