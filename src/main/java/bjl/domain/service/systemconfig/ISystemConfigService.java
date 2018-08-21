package bjl.domain.service.systemconfig;

import bjl.application.systemconfig.command.EditSystemConfigCommand;
import bjl.domain.model.systemconfig.SystemConfig;

/**
 * Created by zhangjin on 2018/1/17
 */
public interface ISystemConfigService {


    SystemConfig edit(EditSystemConfigCommand command);

    SystemConfig get();
}
