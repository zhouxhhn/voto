package bjl.application.auth;


import bjl.application.account.representation.AccountRepresentation;
import bjl.application.auth.command.LoginCommand;
import bjl.application.permission.representation.PermissionRepresentation;

import java.util.List;

/**
 * Created by pengyi on 2016/4/5.
 */
public interface IAuthAppService {
    AccountRepresentation searchByAccountName(String userName);

    List<PermissionRepresentation> findAllPermission();

    AccountRepresentation login(LoginCommand command);
}
