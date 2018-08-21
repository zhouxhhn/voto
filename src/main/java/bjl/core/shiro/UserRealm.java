package bjl.core.shiro;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.auth.IAuthAppService;
import bjl.core.enums.EnableStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pengyi on 2016/4/5 0005.
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IAuthAppService authAppService;

    /**
     * 权限验证
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ExtendSimplePrincipalCollection principalCollection = (ExtendSimplePrincipalCollection) principals;
        String userName = (String) principalCollection.getPrimaryPrincipal();
        AccountRepresentation user = authAppService.searchByAccountName(userName);
        SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
        authenticationInfo.setRoles(this.getAllRoles(user));
        authenticationInfo.setStringPermissions(this.getAllPermissions(user));
        return authenticationInfo;
    }

    /**
     * 获取用户角色
     *
     * @param user
     * @return
     */
    private Set<String> getAllRoles(AccountRepresentation user) {
        Set<String> roles = new HashSet<>();
        if (null != user.getRoles()) {
            roles.addAll(user.getRoles());
        }
        return roles;
    }

    /**
     * 获取用户所有权限
     *
     * @param user
     * @return
     */
    private Set<String> getAllPermissions(AccountRepresentation user) {
        Set<String> permissions = new HashSet<>();
        if (null != user.getPermissions()) {
            permissions.addAll(user.getPermissions());
        }
        return permissions;
    }

    /**
     * 登陆验证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AccountRepresentation user = authAppService.searchByAccountName(token.getPrincipal().toString());

        if (null == user) {
            throw new UnknownAccountException(); //用户不存在
        } else {
            if (user.getStatus() == EnableStatus.DISABLE) {
                throw new LockedAccountException(); //用户被禁用
            }
        }

        //处理session
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表
        for (Session session : sessions) {
            //清除该用户以前登录时保存的session
            if (user.getUserName().equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
                sessionManager.getSessionDAO().delete(session);
            }
        }

        return new ExtendSimpleAuthenticationInfo(
                user.getUserName(),
                user.getPassword(),
                new ExtendSimpleByteSource(user.getSalt()),
                getName());
    }
}
