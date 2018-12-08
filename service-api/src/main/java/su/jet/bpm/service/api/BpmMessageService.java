package su.jet.bpm.service.api;

import java.util.Map;

/**
 * Simplified interface for sending messages into BPM engine.
 *
 * @author Fedor Resnyanskiy <fa.resnyanskiy@jet.msk.su>
 */
public interface BpmMessageService {

    void message(String processInstanceId, String message, Map<String, Object> parameters) throws BpmServiceException;

}
