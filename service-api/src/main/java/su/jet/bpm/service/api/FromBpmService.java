package su.jet.bpm.service.api;

/**
 * Interface for outgoing BPM exchanges. See more in implementations.
 *
 * @author Fedor Resnyanskiy <fa.resnyanskiy@jet.msk.su>
 */
public interface FromBpmService {
    /**
     * Outgoing call from BPM process.
     *
     * @param targetName identifier to send to delegate.
     * @throws FromBpmServiceException
     */
    void call(String targetName) throws FromBpmServiceException;
}
