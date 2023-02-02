package expression.generic;

import expression.AbstractElement;
import expression.exceptions.EvaluatingException;
import expression.handler.Handler;
import expression.handler.HandlerMngr;
import parser.ExpressionParser;
import parser.StringSource;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return fill(HandlerMngr.getHandlerByMode(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] fill(Handler<T> handler, String expression, int x1, int x2,
                                  int y1, int y2, int z1, int z2) throws Exception {
        AbstractElement element = new ExpressionParser<T>(new StringSource(expression)).parse();
        Object[][][] ans = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = 0; i <= x2 - x1; i++) {
            for (int j = 0; j <= y2 - y1; j++) {
                for (int k = 0; k <= z2 - z1; k++) {
                    try {
                        ans[i][j][k] = element.evaluate(handler.cast(i + x1), handler.cast(j + y1),
                                handler.cast(k + z1), handler);
                    } catch (EvaluatingException ex){}
                }
            }
        }

        return ans;
    }
}