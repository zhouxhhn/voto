package bjl.domain.model.recharge;

import bjl.domain.model.account.Account;
import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by pengyi on 16-7-9.
 */
public interface IRechargeRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    BigDecimal total(List<Criterion> criterionList, Map<String, String> aliasMap);

    T byOrderNo(String orderNo);

    Map<String, BigDecimal> sum(Date date);

    List<Object[]> list(String username);
}
