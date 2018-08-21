package bjl.domain.service.permission;


import bjl.application.permission.command.CreatePermissionCommand;
import bjl.application.permission.command.EditPermissionCommand;
import bjl.application.permission.command.ListPermissionCommand;
import bjl.application.shared.command.SharedCommand;
import bjl.domain.model.permission.Permission;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by pengyi on 2016/3/30.
 */
public interface IPermissionService {

    Pagination<Permission> pagination(ListPermissionCommand command);

    List<Permission> list(ListPermissionCommand command);

    List<Permission> searchByIDs(List<String> ids);

    Permission searchByID(String id);

    Permission searchByName(String name);

    Permission create(CreatePermissionCommand command);

    Permission edit(EditPermissionCommand command);

    void updateStatus(SharedCommand command);
}
