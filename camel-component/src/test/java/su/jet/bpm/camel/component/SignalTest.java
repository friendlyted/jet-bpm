package su.jet.bpm.camel.component;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.junit.Before;
import org.junit.Test;
import su.jet.bpm.service.api.BpmSignalService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SignalTest extends AbstractBpmComponentTest {

    private static final String SIGNAL_NAME = "signalName";
    private static final String BPM_START_PROCESS = BpmConstants.BMP_SCHEME + ":" + BpmConstants.BMP_TYPE_SIGNAL + "/" + SIGNAL_NAME;

    @Produce(uri = DIRECT_START)
    protected ProducerTemplate routeStart;

    @EndpointInject(uri = BPM_START_PROCESS)
    protected BpmEndpoint bpmEndpoint;

    private BpmSignalService service;

    @Before
    public void setService() {
        service = mock(BpmSignalService.class);
        bpmEndpoint.setBpmSignalService(service);
    }

    @Test
    public void testSignalName() throws Exception {
        routeStart.sendBody(null);
        verify(service).signal(eq(SIGNAL_NAME));
        verifyNoMoreInteractions(service);
    }


    protected RouteDefinition setup(RouteBuilder r) {
        return r
                .from(DIRECT_START)
                .to(BPM_START_PROCESS);
    }
}
