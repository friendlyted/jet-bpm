package su.jet.bpm.camel.component;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import su.jet.bpm.service.api.BpmMessageService;

import java.util.Map;

public class BpmMessageProducer extends DefaultProducer {

    public BpmMessageProducer(BpmEndpoint endpoint) {
        super(endpoint);
    }

    @Override
    public BpmEndpoint getEndpoint() {
        return (BpmEndpoint) super.getEndpoint();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final String processId;
        try {
            processId = BpmHelper.findBpmInstanceId(exchange);
        } catch (Exception ex) {
            throw new RuntimeException("Cannot find any " + BpmConstants.BPM_PARAM_PROCESS_INSTANCE_ID + " property", ex);
        }

        final BpmMessageService service = getEndpoint().getBpmMessageService();
        if (service == null) {
            throw new NoBpmServiceException("Not BPM message service provided");
        }

        final String name = getEndpoint().getMessageName();
        final Map<String, Object> properties = BpmHelper.extractProperties(exchange);
        service.message(processId, name, properties);

    }
}
