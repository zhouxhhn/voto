package bjl.application.personal;

import bjl.application.personal.command.ListPersonalCommand;
import bjl.application.personal.reprensentation.PersonalRepresentation;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.service.personal.IPersonalService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Service("personalAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PersonalAppService implements IPersonalAppService{

    @Autowired
    private IPersonalService personalService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public Pagination<PersonalRepresentation> pagination(ListPersonalCommand command) {
        command.verifyPage();
        command.setPageSize(18);

        Pagination<GameDetailed> pagination = personalService.pagination(command);
        List<PersonalRepresentation> data = mappingService.mapAsList(pagination.getData(),PersonalRepresentation.class);

        return new Pagination<>(data,pagination.getCount(),command.getPage(),command.getPageSize());
    }
}
