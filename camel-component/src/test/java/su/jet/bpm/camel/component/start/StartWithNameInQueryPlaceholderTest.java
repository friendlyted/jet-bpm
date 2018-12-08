package su.jet.bpm.camel.component.start;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.model.RouteDefinition;
import org.junit.Before;
import org.junit.Test;
import su.jet.bpm.camel.component.AbstractBpmComponentTest;
import su.jet.bpm.camel.component.BpmConstants;
import su.jet.bpm.camel.component.BpmEndpoint;
import su.jet.bpm.service.api.BpmStartService;

import java.util.Map;

import static org.mockito.Mockito.*;

public class StartWithNameInQueryPlaceholderTest extends AbstractBpmComponentTest {

    private static final String PROCESS_NAME = "processName";
    private static final String PLACEHOLDER = "placeholder";
    private static final String BPM_START_PROCESS = BpmConstants.BMP_SCHEME + ":" + BpmConstants.BMP_TYPE_START + "?" + BpmConstants.BPM_PARAM_PROCESS_NAME + "={{" + PLACEHOLDER + "}}";

    @Produce(uri = DIRECT_START)
    protected ProducerTemplate routeStart;

    @EndpointInject(uri = BPM_START_PROCESS)
    protected BpmEndpoint bpmEndpoint;

    private BpmStartService service;

    @Before
    public void setService() {
        service = mock(BpmStartService.class);
        bpmEndpoint.setBpmStartService(service);
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        final CamelContext context = super.createCamelContext();
        final java.util.Properties props = new java.util.Properties();
        props.setProperty(PLACEHOLDER, PROCESS_NAME);
        final PropertiesComponent pc = context.getComponent("properties", PropertiesComponent.class);
        pc.setOverrideProperties(props);
        return context;
    }

    @Test
    public void testStartWithNameInQuery() throws Exception {
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
