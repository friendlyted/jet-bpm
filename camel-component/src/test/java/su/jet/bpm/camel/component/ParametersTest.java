package su.jet.bpm.camel.component;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.junit.Before;
import org.junit.Test;
import su.jet.bpm.service.api.BpmStartService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ParametersTest extends AbstractBpmComponentTest {

    private static final String BPM_START_PROCESS = BpmConstants.BMP_SCHEME + ":" + BpmConstants.BMP_TYPE_START + "/processName";
    private static final String PREFIX = "prefix_";
    private static final String PARAM_NAME = "paramName";
    private static final String PARAM = "param";

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
    public void testNoHeaders() throws Exception {
        routeStart.sendBody(null);
        verify(service).startProcess(any(), argThat(Map::isEmpty));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testSinglePrefixParamHeader() throws Exception {
        routeStart.sendBodyAndHeader(null, BpmConstants.BPM_PARAM_PARAM_TO_SELECT_PREFIX, PREFIX);
        verify(service).startProcess(any(), argThat(Map::isEmpty));
        verifyNoMoreInteractions(service);
    }


    @Test
    public void testNoPrefixHeader() throws Exception {
        routeStart.sendBodyAndHeader(null, PARAM_NAME, PARAM);
        verify(service).startProcess(any(), argThat(Map::isEmpty));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testSinglePrefixHeader() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        headers.put(BpmConstants.BPM_PARAM_PARAM_TO_SELECT_PREFIX, PREFIX);
        headers.put(PREFIX + PARAM_NAME, PARAM);
        routeStart.sendBodyAndHeaders(null, headers);
        verify(service).startProcess(any(), argThat(m -> m.size() == 1));
        verifyNoMoreInteractions(service);
    }

    protected RouteDefinition setup(RouteBuilder r) {
        return r.from(DIRECT_START)
                .to(BPM_START_PROCESS);
    }
}
