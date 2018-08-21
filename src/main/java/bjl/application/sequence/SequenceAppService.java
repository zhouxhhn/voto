package bjl.application.sequence;

import bjl.domain.service.sequence.ISequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pengyi on 2016/3/22.
 */
@Service("sequenceAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SequenceAppService implements ISequenceAppService {

    @Autowired
    private ISequenceService sequenceService;

    @Override
    public long getNextSequence(int suffixLength) {
        return sequenceService.getNextSequence(suffixLength);
    }

    @Override
    public void initSequence() {
        sequenceService.initSequence();
    }

    @Override
    public void reset() {
        sequenceService.reset();
    }

}
