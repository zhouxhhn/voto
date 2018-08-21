package bjl.domain.service.sequence;

/**
 * Created by pengyi on 2016/3/22.
 */
public interface ISequenceService {
    long getNextSequence(int suffixLength);

    void initSequence();

    void reset();
}
