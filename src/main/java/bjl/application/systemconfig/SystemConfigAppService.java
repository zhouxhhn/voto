package bjl.application.systemconfig;

import bjl.application.systemconfig.command.EditSystemConfigCommand;
import bjl.domain.model.systemconfig.SystemConfig;
import bjl.domain.service.systemconfig.ISystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjin on 2018/1/17
 */
@Service("systemConfigAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SystemConfigAppService implements ISystemConfigAppService {

    @Autowired
    private ISystemConfigService systemConfigService;

    @Override
    public SystemConfig edit(EditSystemConfigCommand command) {
        return systemConfigService.edit(command);
    }

    @Override
    public SystemConfig get() {
        return systemConfigService.get();
    }
}
