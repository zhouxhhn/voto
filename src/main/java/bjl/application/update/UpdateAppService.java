package bjl.application.update;

import bjl.application.update.command.EditUpdateCommand;
import bjl.application.update.repensentation.ApiUpdateRepresentation;
import bjl.core.mapping.MappingService;
import bjl.domain.model.update.Update;
import bjl.domain.service.update.IUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zhangjin on 2017/12/23.
 */
@Service("updateAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UpdateAppService implements IUpdateAppService{

    @Autowired
    private IUpdateService updateService;
    @Autowired
    private MappingService mappingService;

    @Override
    public Update get() {
        return updateService.get();
    }

    @Override
    public boolean updateFile(MultipartFile file, EditUpdateCommand command) {

        return updateService.updateFile(file,command);
    }

    @Override
    public ApiUpdateRepresentation apiGet() {

        return mappingService.map(updateService.get(),ApiUpdateRepresentation.class,false);
    }
}
