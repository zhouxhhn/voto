package bjl.application.systemconfig;

import bjl.application.systemconfig.command.EditSystemConfigCommand;
import bjl.domain.model.systemconfig.SystemConfig;

/**
 * Created by zhangjin on 2018/1/17
 */
public interface ISystemConfigAppService {

    SystemConfig edit(EditSystemConfigCommand command);

    SystemConfig get();
}
