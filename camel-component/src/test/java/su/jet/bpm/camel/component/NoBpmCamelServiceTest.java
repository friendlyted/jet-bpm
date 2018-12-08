package su.jet.bpm.camel.component;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.junit.Test;

public class NoBpmCamelServiceTest extends AbstractBpmComponentTest {

    private static final String PROCESS_NAME = "processName";
    private static final String BPM_START_PROCESS = BpmConstants.BMP_SCHEME + ":" + BpmConstants.BMP_TYPE_START + "/" + PROCESS_NAME;

    @Produce(uri = DIRECT_START)
    protected ProducerTemplate routeStart;

    @Test
    public void testStartWithNameInPath() {
        try {
            routeStart.sendBody(null);
            fail("Exception expected");
        } catch (CamelExecutionException ex) {
            assertEquals(ex.getCause().getClass(), NoBpmServiceException.class);
        }
    }

    protected RouteDefinition setup(RouteBuilder r) {
        return r
                .from(DIRECT_START)
                .to(BPM_START_PROCESS);
    }
}
