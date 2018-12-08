package su.jet.bpm.camel.component;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import su.jet.bpm.service.api.BpmServiceException;
import su.jet.bpm.service.api.BpmSignalService;

public class BpmSignalProducer extends DefaultProducer {

    public BpmSignalProducer(BpmEndpoint endpoint) {
        super(endpoint);
    }

    @Override
    public BpmEndpoint getEndpoint() {
        return (BpmEndpoint) super.getEndpoint();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final BpmSignalService service = getEndpoint().getBpmSignalService();
        if (service == null) {
            throw new BpmServiceException("Not BPM signal service provided");
        }

        final String name = getEndpoint().getSignalName();
        service.signal(name);
    }
}
