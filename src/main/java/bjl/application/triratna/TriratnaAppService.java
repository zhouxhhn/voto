package bjl.application.triratna;

import bjl.application.triratna.command.ListITriratnaCommand;
import bjl.application.triratna.command.TotalTriratna;
import bjl.application.triratna.representation.TriratnaRepresentation;
import bjl.domain.service.triratna.ITriratnaService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Service("triratnaAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TriratnaAppService implements ITriratnaAppService{

    @Autowired
    private ITriratnaService triratnaService;

    @Override
    public Pagination<TriratnaRepresentation> pagination(ListITriratnaCommand command,String flag) {
        command.verifyPage();
        command.setPageSize(18);

        return triratnaService.pagination(command,flag);
    }

    @Override
    public TotalTriratna total(ListITriratnaCommand command) {
        return triratnaService.total(command);
    }
}
