package bjl.domain.model.role;


import bjl.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by pengyi on 2016/3/30.
 */
public interface IRoleRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
    Role searchByName(String name);
}
