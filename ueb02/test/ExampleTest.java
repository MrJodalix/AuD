import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import api.ExpressionAPI;
import expression.Context;
import expression.Counter;
import expression.Expression;
import expression.IncompleteContextException;

public class ExampleTest {
    
    
    @Test
    public final void testToStringEquivalenceOr() {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        Assert.assertEquals("((T || F) <-> T)", 
            api.makeEquivalenceExpression(
                api.makeOrExpression(wahr, falsch),
                wahr
            ).toString()
        );
    }
    
    @Test
    public final void testToGraphvizEquivalenceOr() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        TestToolkit.assertDotEquals("((T || F) <-> T) als Graphviz", 
            api.makeEquivalenceExpression(
                api.makeOrExpression(wahr, falsch),
                wahr),
            "a1");
    }
    
    
    @Test
    public final void testToStringEquivalenceAnd() {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        Assert.assertEquals("((T && F) <-> F)", 
            api.makeEquivalenceExpression(
                api.makeAndExpression(wahr, falsch),
                falsch
            ).toString()
        );
    }

    @Test
    public final void testToGraphvizEquivalenceAnd() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        TestToolkit.assertDotEquals("((T && F) <-> F) als Graphviz",  
                api.makeEquivalenceExpression(
                    api.makeAndExpression(wahr, falsch),
                    falsch),
            "a2");
    }

        
    @Test
    public final void testToStringAlmostAll() {
        final ExpressionAPI api = new ExpressionAPI();
        Assert.assertEquals("((a -> b) <-> ((!a) || (b)))",
            api.makeEquivalenceExpression(
                api.makeConsequenceExpression(
                    api.makeVariableExpression("a"),
                    api.makeVariableExpression("b")
                ),
                api.makeOrExpression(
                    api.makeNotExpression(
                        api.makeVariableExpression("a")
                    ),
                    api.makeIdExpression(
                        api.makeVariableExpression("b")
                    )
                )
            ).toString()
        );
    }

    @Test
    public final void testToGraphvizAlmostAll() throws IOException {
        final ExpressionAPI api = new ExpressionAPI();
        TestToolkit.assertDotEquals("((a -> b) <-> ((!a) || (b))) als Graphviz",
            api.makeEquivalenceExpression(
                api.makeConsequenceExpression(
                    api.makeVariableExpression("a"),
                    api.makeVariableExpression("b")
                ),
                api.makeOrExpression(
                    api.makeNotExpression(
                        api.makeVariableExpression("a")
                    ),
                    api.makeIdExpression(
                        api.makeVariableExpression("b")
                    )
                )
            ), "a3");
    }

    
    @Test
    public final void testEvaluateNot() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression variable = api.makeVariableExpression("a");
        ctx.set("a", false);
        
        Assert.assertTrue(api.makeNotExpression(variable).evaluateShort(ctx));
        Assert.assertTrue(api.makeNotExpression(variable).evaluateComplete(ctx));
    }
    
    @Test
    public final void testEvaluateOr() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        
        Assert.assertTrue(api.makeOrExpression(wahr, falsch).evaluateShort(ctx));
        Assert.assertTrue(api.makeOrExpression(wahr, falsch).evaluateComplete(ctx));
    }
    
    @Test
    public final void testEvaluateAnd() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        
        Assert.assertFalse(api.makeAndExpression(wahr, falsch).evaluateShort(ctx));
        Assert.assertFalse(api.makeAndExpression(wahr, falsch).evaluateComplete(ctx));
    }
    
    
    @Test
    public final void testEvaluateAndParallel() throws IncompleteContextException {
        final Context ctx = new Context();
        final ExpressionAPI api = new ExpressionAPI();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        
        Counter.initialize();
        Assert.assertFalse(api.makeAndExpression(wahr, falsch).evaluateParallel(ctx, 2));
        Assert.assertEquals(1, Counter.getCounter());
    }

   
    @Test
    public final void testParallelAlmostAll() throws IncompleteContextException {
        final ExpressionAPI api = new ExpressionAPI();
        final Context ctx = new Context();
        final Expression wahr = api.makeConstantExpression(true);
        final Expression falsch = api.makeConstantExpression(false);
        
        final Expression bigger =
            api.makeEquivalenceExpression(
                api.makeConsequenceExpression(
                    falsch,
                    wahr
                ),
                api.makeOrExpression(
                    api.makeNotExpression(
                        falsch
                    ),
                    api.makeIdExpression(
                       wahr
                    )
                )
            );
        
        Counter.initialize();
        Assert.assertTrue(bigger.evaluateParallel(ctx, 6));
        Assert.assertEquals(1, Counter.getCounter());
        
        Counter.initialize();
        Assert.assertTrue(bigger.evaluateParallel(ctx, 2));
        Assert.assertEquals(3, Counter.getCounter());        
    }
    
    

}
