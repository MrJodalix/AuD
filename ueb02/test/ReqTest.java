import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import api.ExpressionAPI;
import expression.Context;
import expression.Counter;
import expression.Expression;
import expression.IncompleteContextException;

public class ReqTest {

    @Test
    public final void testEvaluateShortEquivalence() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression variable = api.makeVariableExpression("a");
        final Expression variable2 = api.makeVariableExpression("a");
        ctx.set("a", false);

        Assert.assertTrue(api.makeEquivalenceExpression(variable, variable2).evaluateShort(ctx));
    }

    @Test
    public final void testEvaluateCompleteAnd() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression variable = api.makeVariableExpression("a");
        final Expression variable2 = api.makeVariableExpression("a");
        ctx.set("a", false);

        Assert.assertFalse(api.makeAndExpression(variable, variable2).evaluateComplete(ctx));
    }

    @Test
    public final void testEvaluateParallelAnd() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression variable = api.makeVariableExpression("a");
        final Expression variable2 = api.makeVariableExpression("a");
        ctx.set("a", false);

        Counter.initialize();
        Assert.assertFalse(api.makeAndExpression(variable, variable2).evaluateParallel(ctx, 1));
        Assert.assertEquals(1, Counter.getCounter());
    }

    @Test
    public final void testEvaluateParallelAll() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression variableA = api.makeVariableExpression("a");
        final Expression variableB = api.makeVariableExpression("b");
        final Expression variableC = api.makeVariableExpression("c");
        ctx.set("a", false);
        ctx.set("b", false);
        ctx.set("c", true);
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);

        final Expression bigger =
                api.makeIdExpression(
                        api.makeAndExpression(
                                api.makeXorExpression(variableA,
                                        api.makeNotExpression(variableB)),
                                variableC)
                );

        Counter.initialize();

        Assert.assertTrue(bigger.evaluateParallel(ctx, 2));
        Assert.assertEquals(2, Counter.getCounter());
    }

    @Test
    public final void testToStringAll() {
        final ExpressionAPI api    = new ExpressionAPI();
        final Expression    wahr   = api.makeConstantExpression(true);
        final Expression    falsch = api.makeConstantExpression(false);

        Assert.assertEquals("(((F -> (T <-> T)) || ((T ^ (!T)) && F)))", api.makeIdExpression(
                        api.makeOrExpression(api.makeConsequenceExpression(falsch,
                                api.makeEquivalenceExpression(wahr, wahr)), api.makeAndExpression(
                                api.makeXorExpression(wahr, api.makeNotExpression(wahr)), falsch))).toString());
    }

    @Test
    public final void testToGraphvizAll() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();

        TestToolkit.assertDotEquals("(((F -> (T <-> T)) || ((T ^ (!T)) && F))) als Graphviz",
                api.makeIdExpression(
                        api.makeOrExpression(
                                api.makeConsequenceExpression(api.makeConstantExpression(false),
                                        api.makeEquivalenceExpression(api.makeConstantExpression(true),
                                                api.makeConstantExpression(true))),
                                api.makeAndExpression(
                                        api.makeXorExpression(api.makeConstantExpression(true),
                                                api.makeNotExpression(api.makeConstantExpression(true))),
                                        api.makeConstantExpression(false)))), "a4");
    }
}
