package General;

import javax.script.*;
import net.objecthunter.exp4j.*;
public class Formula {
    private static Expression expression;

    public Formula() {
    }
    public Formula(Expression ex) {
        expression = ex;
    }
    public static double f(double x) {
        try{
            return expression.setVariable("x",x).evaluate();
        }catch (Throwable cause){
            if(cause instanceof ArithmeticException && "Division by zero!".equals(cause.getMessage()))
                return Double.POSITIVE_INFINITY;
            else{
                return Double.NaN;
            }
        }
    }

}
