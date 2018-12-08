package su.jet.bpm.service.api;

/**
 * Simplified interface for sending signals into BPM engine.
 *
 * @author Fedor Resnyanskiy <fa.resnyanskiy@jet.msk.su>
 */
public interface BpmSignalService {

    void signal(String signalName) throws BpmServiceException;

}
