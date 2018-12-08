package su.jet.bpm.camel.component;

import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.model.RouteDefinition;
import org.junit.Test;
import su.jet.bpm.service.api.BpmStartService;

import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class BpmCamelServicePlaceholderTest extends AbstractBpmComponentTest {

    private static final String PROCESS_NAME = "processName";
    private static final String PLACEHOLDER = "placeholder";
    private static final String BPM_START_PROCESS = BpmConstants.BMP_SCHEME + ":" + BpmConstants.BMP_TYPE_START + "/" + PROCESS_NAME + "?bpmStartService=#" + PLACEHOLDER;

    @Produce(uri = DIRECT_START)
    protected ProducerTemplate routeStart;

    private BpmStartService service;

    @Override
    protected CamelContext createCamelContext() throws Exception {
        final CamelContext context = super.createCamelContext();
        service = mock(BpmStartService.class);
        context.getRegistry(JndiRegistry.class).bind(PLACEHOLDER, service);
        return context;
    }

    @Test
    public void testStartWithNameInPath() throws Exception {
        routeStart.sendBody(null);
        verify(service).startProcess(eq(PROCESS_NAME), argThat(Map::isEmpty));
        verifyNoMoreInteractions(service);
    }

    protected RouteDefinition setup(RouteBuilder r) {
        return r
                .from(DIRECT_START)
                .to(BPM_START_PROCESS);
    }
}
