package bjl.application.role;


import bjl.application.role.command.CreateRoleCommand;
import bjl.application.role.command.EditRoleCommand;
import bjl.application.role.command.ListRoleCommand;
import bjl.application.role.representation.RoleRepresentation;
import bjl.application.shared.command.SharedCommand;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by pengyi on 2016/3/30.
 */
public interface IRoleAppService {

    Pagination<RoleRepresentation> paginationJSON(ListRoleCommand command);

    Pagination<RoleRepresentation> pagination(ListRoleCommand command);

    List<RoleRepresentation> list(ListRoleCommand command);

    RoleRepresentation searchByID(String id);

    RoleRepresentation searchByName(String name);

    RoleRepresentation create(CreateRoleCommand command);

    RoleRepresentation edit(EditRoleCommand command);

    void updateStatus(SharedCommand command);
}
