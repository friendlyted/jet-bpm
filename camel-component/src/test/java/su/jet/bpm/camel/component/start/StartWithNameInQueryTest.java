package su.jet.bpm.camel.component.start;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.junit.Before;
import org.junit.Test;
import su.jet.bpm.camel.component.AbstractBpmComponentTest;
import su.jet.bpm.camel.component.BpmConstants;
import su.jet.bpm.camel.component.BpmEndpoint;
import su.jet.bpm.service.api.BpmStartService;

import java.util.Map;

import static org.mockito.Mockito.*;
import static su.jet.bpm.camel.component.BpmConstants.BPM_PARAM_PROCESS_NAME;

public class StartWithNameInQueryTest extends AbstractBpmComponentTest {

    private static final String PROCESS_NAME = "processName";
    private static final String BPM_START_PROCESS = BpmConstants.BMP_SCHEME + ":" + BpmConstants.BMP_TYPE_START + "?" + BPM_PARAM_PROCESS_NAME + "=" + PROCESS_NAME;

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
