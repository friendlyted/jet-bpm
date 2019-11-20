package su.jet.bpm.service.api;

import java.util.Map;

public interface PropertiesService {
    Map<String, Object> getProperties();

    Object getBody();

    void setProperties(Map<String, Object> properties);

    void setBody(Object newBody);
}

