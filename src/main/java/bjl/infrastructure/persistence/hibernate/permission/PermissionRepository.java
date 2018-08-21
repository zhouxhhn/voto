package bjl.infrastructure.persistence.hibernate.permission;


import bjl.domain.model.permission.IPermissionRepository;
import bjl.domain.model.permission.Permission;
import bjl.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi on 2016/3/30.
 */
@Repository("permissionRepository")
public class PermissionRepository extends AbstractHibernateGenericRepository<Permission, String>
        implements IPermissionRepository<Permission, String> {
    @Override
    public Permission searchByName(String name) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("name", name));
        return (Permission) criteria.uniqueResult();
    }
}
