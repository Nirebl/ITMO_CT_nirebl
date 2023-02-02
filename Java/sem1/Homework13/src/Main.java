import expression.TripleExpression;
import expression.exceptions.ExpressionParser;
import expression.exceptions.ParsingException;

public class Main {

    public static void main(String[] args) {
        //String strExp = "((0+0)";
        //String strExp = "(((0 )+ -1)))";
        //String strExp = "0 + -1+()";
        //String strExp = "(1\f\n" +" + 2147483647)";
        //String strExp = "-(-2147483648)";
        //String strExp = "-(-2147483648)";
        //String strExp = "(4 ** 30)";

        //String strExp = "((y ** y) ** y)";
        //String strExp = "(y ** (z ** y))";
        //String strExp = "(y * (z * (0 ** x)))";

        //String strExp = "((x // x) // z)";
        //String strExp = "(0 + 0)";
        String strExp = "((0)* + (1)";

        ExpressionParser parser = new ExpressionParser();

        TripleExpression exp = null;
        try {
            exp = parser.parse(strExp);
        } catch (ParsingException e) {
            e.printStackTrace();
        }

//        System.out.println(exp.evaluate(-825761493, -935530951, 1332958861));


        System.out.println(exp.toMiniString());
        System.out.println(exp.toString());
    }
}
