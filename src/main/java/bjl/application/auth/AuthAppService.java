package bjl.application.auth;

import bjl.application.account.IAccountAppService;
import bjl.application.account.representation.AccountRepresentation;
import bjl.application.auth.command.LoginCommand;
import bjl.application.permission.IPermissionAppService;
import bjl.application.permission.command.ListPermissionCommand;
import bjl.application.permission.representation.PermissionRepresentation;
import bjl.core.enums.EnableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pengyi on 2016/4/5.
 */
@Service("authAppService")
public class AuthAppService implements IAuthAppService {

    @Autowired
    private IAccountAppService accountAppService;

    @Autowired
    private IPermissionAppService permissionAppService;

    @Override
    public AccountRepresentation searchByAccountName(String userName) {
        return accountAppService.searchByAccountName(userName);
    }

    @Override
    public List<PermissionRepresentation> findAllPermission() {
        ListPermissionCommand command = new ListPermissionCommand();
        command.setStatus(EnableStatus.ENABLE);
        return permissionAppService.list(command);
    }

    @Override
    public AccountRepresentation login(LoginCommand command) {
        return accountAppService.login(command);
    }
}
