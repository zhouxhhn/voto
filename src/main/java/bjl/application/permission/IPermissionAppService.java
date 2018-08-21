package bjl.application.permission;


import bjl.application.permission.command.CreatePermissionCommand;
import bjl.application.permission.command.EditPermissionCommand;
import bjl.application.permission.command.ListPermissionCommand;
import bjl.application.permission.representation.PermissionRepresentation;
import bjl.application.shared.command.SharedCommand;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by pengyi on 2016/3/30.
 */
public interface IPermissionAppService {

    Pagination<PermissionRepresentation> pagination(ListPermissionCommand command);

    Pagination<PermissionRepresentation> paginationJSON(ListPermissionCommand command);

    List<PermissionRepresentation> list(ListPermissionCommand command);

    PermissionRepresentation searchByID(String id);

    PermissionRepresentation searchByName(String name);

    PermissionRepresentation create(CreatePermissionCommand command);

    PermissionRepresentation edit(EditPermissionCommand command);

    void updateStatus(SharedCommand command);

}
