package su.jet.bpm.service.camunda;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import su.jet.bpm.service.api.BpmStartService;

import java.util.Map;

public class CamelToBpmStart implements BpmStartService {

    private RuntimeService runtimeService;

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public String startProcess(String processName, Map<String, Object> parameters) {
        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processName, parameters);
        return processInstance.getId();
    }
}
