package su.jet.bpm.camel.component;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import su.jet.bpm.service.api.BpmServiceException;
import su.jet.bpm.service.api.BpmStartService;

import java.util.Map;

public class BpmStartProducer extends DefaultProducer {

    public BpmStartProducer(BpmEndpoint endpoint) {
        super(endpoint);
    }

    @Override
    public BpmEndpoint getEndpoint() {
        return (BpmEndpoint) super.getEndpoint();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final String bpmProcessName;
        final String bpmProcessNameFromEndpoint = getEndpoint().getBpmProcessName();
        if (bpmProcessNameFromEndpoint != null) {
            bpmProcessName = bpmProcessNameFromEndpoint;
        } else {
            final String bpmProcessNameFromParameter = BpmHelper.findProperty(BpmConstants.BPM_PARAM_PROCESS_NAME, exchange);
            if (bpmProcessNameFromParameter != null) {
                bpmProcessName = bpmProcessNameFromParameter;
            } else {
                throw new NoNameProvidedException("No process name to call provided");
            }
        }

        final BpmStartService service = getEndpoint().getBpmStartService();
        if (service == null) {
            throw new NoBpmServiceException("No BPM start service provided");
        }

        final String processId;
        try {
            final Map<String, Object> properties = BpmHelper.extractProperties(exchange);
            processId = service.startProcess(bpmProcessName, properties);
            BpmHelper.getMessage(exchange).setHeader(BpmConstants.BPM_PARAM_PROCESS_INSTANCE_ID, processId);
        } catch (BpmServiceException ex) {
            throw new RuntimeException("Cannot start process " + bpmProcessName, ex);
        }
    }


}