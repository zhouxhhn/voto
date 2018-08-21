package bjl.infrastructure.persistence.hibernate.transfer;

import bjl.domain.model.transfer.ITransferRepository;
import bjl.domain.model.transfer.Transfer;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjin on 2017/9/14.
 */
@Repository("transferRepository")
public class TransferRepository extends AbstractHibernateGenericRepository<Transfer,String>
        implements ITransferRepository<Transfer,String>{
}
