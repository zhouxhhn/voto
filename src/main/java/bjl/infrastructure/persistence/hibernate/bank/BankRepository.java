package bjl.infrastructure.persistence.hibernate.bank;

import bjl.domain.model.bank.Bank;
import bjl.domain.model.bank.BankDtl;
import bjl.domain.model.bank.IBankRepository;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangjin on 2017/9/6.
 */
@Repository
public class BankRepository extends AbstractHibernateGenericRepository<Bank, String>
        implements IBankRepository<Bank, String> {

    @Override
    public Bank searchByUser(User user) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("account", user.getAccount()));
        return (Bank) criteria.uniqueResult();
    }

    @Override
    public Bank searchByNo(String bankAccountNo) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("bankAccountNo", bankAccountNo));
        return (Bank) criteria.uniqueResult();
    }

    /**
     * 获取用户银行详细信息
     * @param userId
     * @return
     */
    @Override
    public List<BankDtl> getBankDtl(String userId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT a.create_date as createDate,a.money,a.type,a.status from ");
        sql.append("(SELECT r.create_date,r.money,'1' as type,r.is_success as status from t_recharge as r where r.user_id='"+userId+"'  UNION ALL ");
        sql.append("SELECT w.create_date,w.money,'2' as type,w.success as status from t_withdrawa as w where w.user_id ='"+userId+"' ) as a ORDER BY create_date desc limit 50");

        Query query = getSession().createSQLQuery(sql.toString()).addScalar("createDate").addScalar("money").addScalar("type").addScalar("status").setResultTransformer(Transformers.aliasToBean(BankDtl.class));
        List<BankDtl> list = query.list();
        return list;
    }
}
