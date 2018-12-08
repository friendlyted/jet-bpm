package su.jet.bpm.camel.component;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import su.jet.bpm.service.api.BpmMessageService;
import su.jet.bpm.service.api.BpmSignalService;
import su.jet.bpm.service.api.BpmStartService;

public class BpmEndpoint extends DefaultEndpoint {

    private String name;
    private String type;
    private BpmStartService bpmStartService;
    private BpmMessageService bpmMessageService;
    private BpmSignalService bpmSignalService;

    public BpmEndpoint() {
        setSynchronous(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBpmProcessName() {
        return name;
    }

    public void setBpmProcessName(String bpmProcessName) {
        this.name = bpmProcessName;
    }

    public String getSignalName() {
        return name;
    }

    public void setSignalName(String signalName) {
        this.name = signalName;
    }

    public String getMessageName() {
        return name;
    }

    public void setMessageName(String messageName) {
        this.name = messageName;
    }

    public BpmStartService getBpmStartService() {
        return bpmStartService;
    }

    public void setBpmStartService(BpmStartService bpmStartService) {
        this.bpmStartService = bpmStartService;
    }

    public BpmMessageService getBpmMessageService() {
        return bpmMessageService;
    }

    public void setBpmMessageService(BpmMessageService bpmMessageService) {
        this.bpmMessageService = bpmMessageService;
    }

    public BpmSignalService getBpmSignalService() {
        return bpmSignalService;
    }

    public void setBpmSignalService(BpmSignalService bpmSignalService) {
        this.bpmSignalService = bpmSignalService;
    }

    @Override
    public Producer createProducer() throws Exception {
        switch (type) {
            case BpmConstants.BMP_TYPE_START:
                return new BpmStartProducer(this);
            case BpmConstants.BMP_TYPE_MESSAGE:
                return new BpmMessageProducer(this);
            case BpmConstants.BMP_TYPE_SIGNAL:
                return new BpmSignalProducer(this);
            default:
                throw new UnsupportedBpmCaseException("There is no BPM producer for type '" + type + "'");
        }
    }

    @Override
    public Consumer createConsumer(Processor processor) {
        throw new RuntimeException("There is no consumer options for bpm component");
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    protected String createEndpointUri() {
        final StringBuilder stringBuilder = new StringBuilder(BpmConstants.BMP_SCHEME)
                .append(":")
                .append(getType());
        if (getName() != null) {
            stringBuilder
                    .append("/")
                    .append(getName());
        }
        return stringBuilder.toString();
    }


}
