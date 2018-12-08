package su.jet.bpm.extra;

import org.camunda.bpm.engine.impl.el.ExpressionManager;
import org.camunda.bpm.engine.impl.javax.el.CompositeELResolver;
import org.camunda.bpm.engine.impl.javax.el.ELResolver;

import java.util.List;

public class FlexibleExpressionManager extends ExpressionManager {

    private final CompositeELResolver delegate = new CompositeELResolver();

    public void setResolvers(List<ELResolver> resolvers) {
        resolvers.forEach(delegate::add);
    }

    @Override
    protected ELResolver createElResolver() {
        delegate.add(super.createElResolver());
        return delegate;
    }
}
