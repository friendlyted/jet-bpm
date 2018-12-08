package su.jet.bpm.camel.component;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;

public abstract class AbstractBpmComponentTest extends CamelTestSupport {

    public static final String DIRECT_START = "direct:routeStart";

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                setup(this);
            }
        };
    }

    protected abstract RouteDefinition setup(RouteBuilder r);

}
