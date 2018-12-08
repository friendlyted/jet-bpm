package su.jet.bpm.service.camunda;

import org.camunda.bpm.engine.RuntimeService;
import su.jet.bpm.service.api.BpmSignalService;

public class CamelToBpmSignal implements BpmSignalService {

    private RuntimeService runtimeService;

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void signal(String signalName) {
        runtimeService
                .createSignalEvent(signalName)
                .send();
    }
}
