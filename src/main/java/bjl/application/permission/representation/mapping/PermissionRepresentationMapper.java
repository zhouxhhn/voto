package bjl.application.permission.representation.mapping;

import bjl.application.permission.representation.PermissionRepresentation;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.permission.Permission;
import ma.glasnost.orika.CustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by pengyi on 2016/3/30 0030.
 */
@Component
public class PermissionRepresentationMapper extends CustomMapper<Permission, PermissionRepresentation> {

    @Autowired
    private IMappingService mappingService;
}
