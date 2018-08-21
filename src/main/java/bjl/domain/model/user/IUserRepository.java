package bjl.domain.model.user;

import bjl.domain.model.account.Account;
import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pengyi on 2016/4/15.
 */
public interface IUserRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    Integer getMaxSerial();

    User searchByAccount(Account account);

    Object[] sum(List<Criterion> criterionList);


}
