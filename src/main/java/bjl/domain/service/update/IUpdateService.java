package bjl.domain.service.update;

import bjl.application.update.command.EditUpdateCommand;
import bjl.domain.model.update.Update;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zhangjin on 2017/12/23.
 */
public interface IUpdateService {

    Update get();

    boolean updateFile(MultipartFile file, EditUpdateCommand command);
}
