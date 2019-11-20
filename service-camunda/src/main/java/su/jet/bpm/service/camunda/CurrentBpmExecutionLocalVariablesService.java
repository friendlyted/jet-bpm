package su.jet.bpm.service.camunda;

import org.camunda.bpm.engine.impl.context.BpmnExecutionContext;
import org.camunda.bpm.engine.impl.context.Context;
import su.jet.bpm.service.api.PropertiesService;

import java.util.Map;

public class CurrentBpmExecutionLocalVariablesService implements PropertiesService {

    public static final String BODY_NAME = "BODY";

    @Override
    public Map<String, Object> getProperties() {
        final Map<String, Object> props = getProcessProperties();
        props.remove(BODY_NAME);
        return props;
    }

    @Override
    public void setProperties(Map<String, Object> properties) {
        properties.forEach(Context.getBpmnExecutionContext().getExecution()::setVariable);
    }

    @Override
    public Object getBody() {
        final Map<String, Object> props = getProcessProperties();
        return props.get(BODY_NAME);
    }


    @Override
    public void setBody(Object newBody) {
        Context.getBpmnExecutionContext().getExecution().setVariable(BODY_NAME, newBody);
    }

    private Map<String, Object> getProcessProperties() {
        final BpmnExecutionContext bpmnExecutionContext = Context.getBpmnExecutionContext();
        return bpmnExecutionContext.getExecution().getVariablesLocal().asValueMap();
    }
}
