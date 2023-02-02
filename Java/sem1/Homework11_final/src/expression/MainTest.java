package expression;

import java.math.BigInteger;
import java.util.Objects;

public class MainTest {
    public static void main(String[] args) {

/*
       Expression exp = null;
        exp = new Multiply(
                new Const(1),
                new Multiply(new Const(2), new Const(3)));

        System.out.println(exp.toMiniString());

 */


        BigInteger bg1 = BigInteger.valueOf(-510552320);
        BigInteger bg2 = BigInteger.valueOf(-510552320);

        System.out.println(Objects.equals(bg1, bg2));



        /*
        Expression exp1 = new Add(new Const(-510552320), new Variable("x"));
        Expression exp2 = new Add(new Const(-510552320), new Variable("x"));

        System.out.println(exp1.equals(exp2));


         */



/*        Expression exp = null;
        exp = new Subtract(new Const(1), new Subtract(new Const(2), new Const(3)));
        */

        //exp = new Add(new Const(10), new Subtract(new Const(3), new Const(2)));


 /*       Expression exp = null;
        exp = new Subtract(new Const(1), new Subtract(new Const(2), new Const(3)));
        //exp = new Add(new Const(10), new Subtract(new Const(3), new Const(2)));
        System.out.println(exp.toMiniString());


  */
/*
        exp = new Subtract(
                new Add(
                        new Const(10),
                        new Const(3)),
                new Const(2)
        );

        System.out.println(exp.toMiniString());

        exp = new Add(
                new Const(10),
                new Subtract(
                        new Const(3),
                        new Const(2))
        );

        System.out.println(exp.toMiniString());
*/

/*
        exp = new Subtract(
                new Add(
                        new Const(11),
                        new Const(12)),
                new Add(
                        new Const(2),
                        new Const(3)

                ));

        System.out.println(exp.toMiniString());
*/
/*
        exp = new Add(
                new Const(10),
                new Subtract(
                        new Const(3),
                        new Const(2)

                ));

        System.out.println(exp.toString());
        System.out.println(exp.toMiniString());

*/

/*
        BiExpression e1 = new Multiply(new Const(2), new Variable("x"));
        BiExpression e2 = new Multiply(new Const(2), new Variable("x"));
        BiExpression e3 = new Multiply(new Variable("x"), new Const(2));
        System.out.println(e1.equals(e2));
        System.out.println(e1.equals(e3));

        BiExpression exp = new Subtract(
                new Add(
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        ),
                        new Multiply(
                                new Const(3),
                                new Variable("y")

                        )),
                new Multiply(
                        new Const(5),
                        new Variable("z")
                ));
        System.out.println(exp.toString());
        System.out.println(exp.toMiniString());

        System.out.println(exp.evaluate(10, 10 , 1));

 */
    }
}
