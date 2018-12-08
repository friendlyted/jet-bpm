package su.jet.bpm.service.camunda;

import org.camunda.bpm.engine.RuntimeService;
import su.jet.bpm.service.api.BpmMessageService;

import java.util.Map;

public class CamelToBpmMessage implements BpmMessageService {

    private RuntimeService runtimeService;

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void message(String processInstanceId, String message, Map<String, Object> parameters) {
        runtimeService
                .createMessageCorrelation(message)
                .processInstanceId(processInstanceId)
                .setVariablesLocal(parameters)
                .correlate();
    }

}
