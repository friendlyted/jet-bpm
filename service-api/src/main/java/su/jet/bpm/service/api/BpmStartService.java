package su.jet.bpm.service.api;

import java.util.Map;

/**
 * Simplified interface for creation process instance
 *
 * @author Fedor Resnyanskiy <fa.resnyanskiy@jet.msk.su>
 */
public interface BpmStartService {

    String startProcess(String processName, Map<String, Object> parameters) throws BpmServiceException;

}
