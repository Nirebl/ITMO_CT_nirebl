package expression.parser;

import expression.TripleExpression;
import expression.parserbase.StringSource;

public class ExpressionParser implements Parser {
    @Override
    public TripleExpression parse(String expression) {
        return new ExpressionParserImpl(new StringSource(expression)).parse();
    }
}
