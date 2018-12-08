package su.jet.bpm.camel.component;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class BpmHelper {

    private static final Logger LOG = LoggerFactory.getLogger(BpmHelper.class);

    public static String findBpmInstanceId(Exchange exchange) {
        return findProperty(BpmConstants.BPM_PARAM_PROCESS_INSTANCE_ID, exchange);
    }

    public static String findProperty(String name, Exchange exchange) {
        LOG.debug("searching for property '" + name + "'");
        if (exchange.hasOut()) {
            final String outValue = (String) exchange.getOut().getHeader(name);
            if (outValue != null) {
                LOG.debug("got value '" + outValue + "' from out header");
                return outValue;
            }
        }
        final String inValue = (String) exchange.getIn().getHeader(name);
        if (inValue != null) {
            LOG.debug("got value '" + inValue + "' from in header");
            return inValue;
        }
        final String propertyValue = (String) exchange.getProperty(name);
        if (propertyValue != null) {
            LOG.debug("got value '" + propertyValue + "' from exchange property");
            return propertyValue;
        }
        LOG.debug("value not found");
        return null;
    }

    public static Message getMessage(Exchange exchange) {
        return exchange.hasOut() ? exchange.getOut() : exchange.getIn();
    }

    public static Map<String, Object> extractProperties(Exchange exchange) {
        final String paramToSelectPrefix = findProperty(BpmConstants.BPM_PARAM_PARAM_TO_SELECT_PREFIX, exchange);
        if (paramToSelectPrefix == null) {
            return Collections.emptyMap();
        }
        return getMessage(exchange).getHeaders().entrySet().stream()
                .filter(e -> e.getKey().startsWith(paramToSelectPrefix))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
