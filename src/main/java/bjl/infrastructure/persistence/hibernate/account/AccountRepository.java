package bjl.infrastructure.persistence.hibernate.account;


import bjl.domain.model.account.Account;
import bjl.domain.model.account.IAccountRepository;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi on 2016/3/30.
 */
@Repository("accountRepository")
public class AccountRepository extends AbstractHibernateGenericRepository<Account, String>
        implements IAccountRepository<Account, String> {
    @Override
    public Account searchByAccountName(String userName) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("userName", userName));
        return (Account) criteria.uniqueResult();
    }

    @Override
    public Account searchByToken(String token) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("token", token));
        return (Account) criteria.uniqueResult();
    }

    @Override
    public Account searchByName(String name) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        return (Account) criteria.uniqueResult();
    }
}
