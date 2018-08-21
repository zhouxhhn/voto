package bjl.application.role;

import bjl.application.role.command.CreateRoleCommand;
import bjl.application.role.command.EditRoleCommand;
import bjl.application.role.command.ListRoleCommand;
import bjl.application.role.representation.RoleRepresentation;
import bjl.application.shared.command.SharedCommand;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.role.Role;
import bjl.domain.service.role.IRoleService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pengyi on 2016/3/30.
 */
@Service("roleAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RoleAppService implements IRoleAppService {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMappingService mappingService;

    @Override
    @Transactional(readOnly = true)
    public Pagination<RoleRepresentation> paginationJSON(ListRoleCommand command) {
        command.verifyPage();
        command.setName(command.getRoleName());
        Pagination<Role> pagination = roleService.pagination(command);
        List<RoleRepresentation> data = mappingService.mapAsList(pagination.getData(), RoleRepresentation.class);
        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<RoleRepresentation> pagination(ListRoleCommand command) {
        command.verifyPage();
        command.verifyPageSize(25);
        Pagination<Role> pagination = roleService.pagination(command);
        List<RoleRepresentation> data = mappingService.mapAsList(pagination.getData(), RoleRepresentation.class);
        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleRepresentation> list(ListRoleCommand command) {
        return mappingService.mapAsList(roleService.list(command), RoleRepresentation.class);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleRepresentation searchByID(String id) {
        return mappingService.map(roleService.searchByID(id), RoleRepresentation.class, false);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleRepresentation searchByName(String name) {
        return mappingService.map(roleService.searchByName(name), RoleRepresentation.class, false);
    }

    @Override
    public RoleRepresentation create(CreateRoleCommand command) {
        return mappingService.map(roleService.create(command), RoleRepresentation.class, false);
    }

    @Override
    public RoleRepresentation edit(EditRoleCommand command) {
        return mappingService.map(roleService.edit(command), RoleRepresentation.class, false);
    }

    @Override
    public void updateStatus(SharedCommand command) {
        roleService.updateStatus(command);
    }
}
