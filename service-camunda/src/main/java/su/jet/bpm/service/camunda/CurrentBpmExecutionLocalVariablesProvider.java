package su.jet.bpm.service.camunda;

import org.camunda.bpm.engine.impl.context.BpmnExecutionContext;
import org.camunda.bpm.engine.impl.context.Context;
import su.jet.bpm.service.api.PropertiesProvider;

import java.util.Map;

public class CurrentBpmExecutionLocalVariablesProvider implements PropertiesProvider {
    @Override
    public Map<String, Object> getProperties() {
        final BpmnExecutionContext bpmnExecutionContext = Context.getBpmnExecutionContext();
        return bpmnExecutionContext.getExecution().getVariablesLocal().asValueMap();
    }
}
