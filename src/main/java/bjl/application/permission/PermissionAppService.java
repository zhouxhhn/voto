package bjl.application.permission;

import bjl.application.permission.command.CreatePermissionCommand;
import bjl.application.permission.command.EditPermissionCommand;
import bjl.application.permission.command.ListPermissionCommand;
import bjl.application.permission.representation.PermissionRepresentation;
import bjl.application.shared.command.SharedCommand;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.permission.Permission;
import bjl.domain.service.permission.IPermissionService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pengyi on 2016/3/30.
 */
@Service("permissionAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PermissionAppService implements IPermissionAppService {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IMappingService mappingService;

    @Override
    @Transactional(readOnly = true)
    public Pagination<PermissionRepresentation> pagination(ListPermissionCommand command) {
        command.verifyPage();
        command.verifyPageSize(25);
        Pagination<Permission> pagination = permissionService.pagination(command);
        List<PermissionRepresentation> data = mappingService.mapAsList(pagination.getData(), PermissionRepresentation.class);
        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    public Pagination<PermissionRepresentation> paginationJSON(ListPermissionCommand command) {
        command.verifyPage();
        command.setName(command.getPermissionName());
        Pagination<Permission> pagination = permissionService.pagination(command);
        List<PermissionRepresentation> data = mappingService.mapAsList(pagination.getData(), PermissionRepresentation.class);
        return new Pagination<PermissionRepresentation>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionRepresentation> list(ListPermissionCommand command) {
        return mappingService.mapAsList(permissionService.list(command), PermissionRepresentation.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionRepresentation searchByID(String id) {
        return mappingService.map(permissionService.searchByID(id), PermissionRepresentation.class, false);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionRepresentation searchByName(String name) {
        return mappingService.map(permissionService.searchByName(name), PermissionRepresentation.class, false);
    }

    @Override
    public PermissionRepresentation create(CreatePermissionCommand command) {
        PermissionRepresentation permission = mappingService.map(permissionService.create(command), PermissionRepresentation.class, false);
        return permission;
    }

    @Override
    public PermissionRepresentation edit(EditPermissionCommand command) {
        PermissionRepresentation permission = mappingService.map(permissionService.edit(command), PermissionRepresentation.class, false);
        return permission;
    }

    @Override
    public void updateStatus(SharedCommand command) {
        permissionService.updateStatus(command);
    }
}
