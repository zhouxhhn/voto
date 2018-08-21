package bjl.infrastructure.persistence.hibernate.sequence;

import bjl.domain.model.sequence.ISequenceRepository;
import bjl.domain.model.sequence.Sequence;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi on 2016/3/22.
 */
@Repository("sequenceRepository")
public class SequenceRepository extends AbstractHibernateGenericRepository<Sequence, String>
        implements ISequenceRepository<Sequence, String> {
}
