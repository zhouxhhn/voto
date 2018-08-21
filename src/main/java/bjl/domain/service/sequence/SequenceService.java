package bjl.domain.service.sequence;

import bjl.domain.model.sequence.ISequenceRepository;
import bjl.domain.model.sequence.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pengyi on 2016/3/22.
 */
@Service("sequenceService")
public class SequenceService implements ISequenceService {

    @Autowired
    private ISequenceRepository<Sequence, String> sequenceRepository;

    @Override
    public long getNextSequence(int suffixLength) {
        List<Sequence> sequenceList = sequenceRepository.findAll();
        Sequence sequence = sequenceList.get(0);
        long nextSequence = (sequence.getNo() + 1);

        if (String.valueOf(nextSequence).length() > suffixLength) {
            sequence.setNo(0);
        } else {
            sequence.setNo(nextSequence);
        }

        sequenceRepository.update(sequence);

        return nextSequence;
    }

    @Override
    public void initSequence() {
        List<Sequence> sequenceList = sequenceRepository.findAll();
        if (sequenceList.size() == 0) {
            Sequence sequence = new Sequence(0);
            sequenceRepository.save(sequence);
        } else if (sequenceList.size() > 1) {
            for (Sequence item : sequenceList) {
                sequenceRepository.delete(item);
            }
            Sequence sequence = new Sequence(0);
            sequenceRepository.save(sequence);
        }
    }

    @Override
    public void reset() {
        List<Sequence> sequenceList = sequenceRepository.findAll();
        for (Sequence item : sequenceList) {
            sequenceRepository.delete(item);
        }
        Sequence sequence = new Sequence(0);
        sequenceRepository.save(sequence);
    }
}
