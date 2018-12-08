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

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class StartWithNameInPathTest extends AbstractBpmComponentTest {

    private static final String PROCESS_NAME = "processName";
    private static final String BPM_START_PROCESS = BpmConstants.BMP_SCHEME + ":" + BpmConstants.BMP_TYPE_START + "/" + PROCESS_NAME;

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
    public void testStartWithNameInPath() throws Exception {
        routeStart.sendBody(null);
        verify(service).startProcess(eq(PROCESS_NAME), argThat(Map::isEmpty));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testStartWithNameInPathWithHeader() throws Exception {
        routeStart.sendBodyAndHeader(null, "var", "val");
        verify(service).startProcess(eq(PROCESS_NAME), argThat(Map::isEmpty));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testStartWithNameInPathWithHeaders() throws Exception {
        final Map<String, Object> params = new HashMap<>();
        params.put(BpmConstants.BPM_PARAM_PARAM_TO_SELECT_PREFIX, "var");
        params.put("var", "val");
        routeStart.sendBodyAndHeaders(null, params);
        verify(service).startProcess(eq(PROCESS_NAME), argThat(m -> m.get("var").equals("val")));
        verifyNoMoreInteractions(service);
    }

    protected RouteDefinition setup(RouteBuilder r) {
        return r
                .from(DIRECT_START)
                .to(BPM_START_PROCESS);
    }
}
