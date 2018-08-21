package bjl.application.sequence;

/**
 * Created by pengyi on 2016/3/22.
 */
public interface ISequenceAppService {
    long getNextSequence(int suffixLength);

    void initSequence();

    void reset();
}
