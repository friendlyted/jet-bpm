package su.jet.bpm.service.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;
import su.jet.bpm.service.api.FromBpmService;
import su.jet.bpm.service.api.FromBpmServiceException;
import su.jet.bpm.service.api.PropertiesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FromBpmToCamelDirect implements FromBpmService {

    private List<CamelContext> camelContextList = new ArrayList<>();
    private PropertiesService callPropertiesService;

    public List<CamelContext> getCamelContextList() {
        return camelContextList;
    }

    public void setCamelContextList(List<CamelContext> camelContextList) {
        this.camelContextList = camelContextList;
    }

    public PropertiesService getCallPropertiesService() {
        return callPropertiesService;
    }

    public void setCallPropertiesService(PropertiesService callPropertiesService) {
        this.callPropertiesService = callPropertiesService;
    }

    @Override
    public void call(String targetName) throws FromBpmServiceException {
        for (CamelContext context : getCamelContextList()) {
            for (String name : context.getEndpointMap().keySet()) {
                if (nameIsTarget(targetName, name)) {
                    final Object body = getCallPropertiesService().getBody();
                    final Map<String, Object> properties = getCallPropertiesService().getProperties();
                    final ExchangeBuilder builder = ExchangeBuilder
                            .anExchange(context)
                            .withBody(body);

                    properties.forEach(builder::withHeader);

                    final Exchange exchange = builder.build();
                    context.createProducerTemplate().send(name, exchange);

                    if (exchange.getException() != null) {
                        throw new FromBpmServiceException(exchange.getException());
                    }

                    final Object newBody = exchange.getIn().getBody();
                    final Map<String, Object> newProperties = exchange.getIn().getHeaders();

                    if (newBody != null) {
                        getCallPropertiesService().setBody(newBody);
                    }
                    getCallPropertiesService().setProperties(newProperties);

                    return;
                }
            }
        }
        throw new FromBpmServiceException("Cannot get camel route direct://" + targetName);
    }

    private boolean nameIsTarget(String directName, String name) {
        return name.equals("direct://" + directName) || name.startsWith("direct://" + directName + "?");
    }
}
