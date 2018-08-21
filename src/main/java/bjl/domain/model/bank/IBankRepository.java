package bjl.domain.model.bank;

import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangjin on 2017/9/6.
 */
public interface IBankRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    Bank searchByUser(User user);

    Bank searchByNo(String bankAccountNo);

    List<BankDtl> getBankDtl(String userId);
}
