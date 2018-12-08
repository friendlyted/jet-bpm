package su.jet.bpm.service.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import su.jet.bpm.service.api.FromBpmService;
import su.jet.bpm.service.api.FromBpmServiceException;
import su.jet.bpm.service.api.PropertiesProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FromBpmToCamelDirect implements FromBpmService {

    private List<CamelContext> camelContextList = new ArrayList<>();
    private PropertiesProvider callPropertiesProvider;

    public List<CamelContext> getCamelContextList() {
        return camelContextList;
    }

    public void setCamelContextList(List<CamelContext> camelContextList) {
        this.camelContextList = camelContextList;
    }

    public PropertiesProvider getCallPropertiesProvider() {
        return callPropertiesProvider;
    }

    public void setCallPropertiesProvider(PropertiesProvider callPropertiesProvider) {
        this.callPropertiesProvider = callPropertiesProvider;
    }

    @Override
    public void call(String targetName) throws FromBpmServiceException {
        for (CamelContext context : getCamelContextList()) {
            for (String name : context.getEndpointMap().keySet()) {
                if (nameIsTarget(targetName, name)) {
                    final ProducerTemplate producer = context.createProducerTemplate();
                    producer.setDefaultEndpointUri(name);
                    final Map<String, Object> headers = getCallPropertiesProvider().getProperties();
                    producer.sendBodyAndHeaders(null, headers);
                    return;
                }
            }
        }
        throw new FromBpmServiceException("Cannot get camel route direct://" + targetName);
    }

    private boolean nameIsTarget(String directName, String name) {
        return name.equals("direct://" + directName) ||
                name.startsWith("direct://" + directName + "?");
    }
}
