package bjl.application.update;

import bjl.application.update.command.EditUpdateCommand;
import bjl.application.update.repensentation.ApiUpdateRepresentation;
import bjl.domain.model.update.Update;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zhangjin on 2017/12/23.
 */
public interface IUpdateAppService {

    Update get();

    boolean updateFile(MultipartFile file, EditUpdateCommand command);

    ApiUpdateRepresentation apiGet();
}
