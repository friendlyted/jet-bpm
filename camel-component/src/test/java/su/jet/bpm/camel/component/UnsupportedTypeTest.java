package su.jet.bpm.camel.component;

import org.apache.camel.FailedToCreateProducerException;
import org.apache.camel.FailedToCreateRouteException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.junit.Test;

public class UnsupportedTypeTest extends AbstractBpmComponentTest {

    private static final String BPM_START_PROCESS = BpmConstants.BMP_SCHEME + ":stat";

    @Test
    public void testUnsupportedType() throws Exception {
        try {
            this.context.addRoutes(
                    new RouteBuilder() {
                        @Override
                        public void configure() {
                            from(DIRECT_START).to(BPM_START_PROCESS);
                        }
                    });
            fail("Exception expected");
        } catch (FailedToCreateRouteException ex) {
            assertEquals(ex.getCause().getClass(), FailedToCreateProducerException.class);
            assertEquals(ex.getCause().getCause().getClass(), UnsupportedBpmCaseException.class);
        }
    }

    @Override
    protected RouteDefinition setup(RouteBuilder r) {
        return null;
    }
}