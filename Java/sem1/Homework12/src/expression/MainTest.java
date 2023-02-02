package expression;

import expression.parser.ExpressionParser;

import java.math.BigInteger;
import java.util.Objects;

public class MainTest {
    public static void main(String[] args) {
        //String strExp = "5 * x + 4";
        //String strExp = "(0)";
        //String strExp = "-1";
        //String strExp = "\f-1";
        //String strExp = " (0 + 10)";
        //String strExp = " (4 + 10)/(1+6)";
        //String strExp = "(3) + 6";
        //String strExp = "-(-(- -5 + 6   *x))";
        //String strExp = "-(-(- -5 + 6   *x))";
        //String strExp = "-(-(3   +5)))";
        //String strExp = "l0((y + z))";
        //String strExp = "l0((l0(y) / (x + x)))";
        //String strExp = "l0(y) / (x + x)";
        //String strExp = "-(-(-    -5 + 16   *x*y) + 1 * z) -(((-11)))";
        String strExp = "-(3+5)";
        ExpressionParser parser = new ExpressionParser();

        TripleExpression exp = parser.parse(strExp);

        System.out.println(exp.toMiniString());
        System.out.println(exp.toString());



    }
}
