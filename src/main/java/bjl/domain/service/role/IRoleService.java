package bjl.domain.service.role;

import bjl.application.role.command.CreateRoleCommand;
import bjl.application.role.command.EditRoleCommand;
import bjl.application.role.command.ListRoleCommand;
import bjl.application.shared.command.SharedCommand;
import bjl.domain.model.role.Role;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by pengyi on 2016/3/30
 */
public interface IRoleService {

    Pagination<Role> pagination(ListRoleCommand command);

    List<Role> list(ListRoleCommand command);

    Role searchByID(String id);

    Role searchByName(String name);

    Role create(CreateRoleCommand command);

    Role edit(EditRoleCommand command);

    void updateStatus(SharedCommand command);

    List<Role> searchByIDs(List<String> ids);
}
