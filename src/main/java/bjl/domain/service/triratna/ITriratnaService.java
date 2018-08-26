package bjl.domain.service.triratna;

import bjl.application.triratna.command.ListITriratnaCommand;
import bjl.application.triratna.command.TotalTriratna;
import bjl.application.triratna.representation.TriratnaRepresentation;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by zhangjin on 2018/1/15.
 */
public interface ITriratnaService {

    Pagination<TriratnaRepresentation> pagination(ListITriratnaCommand command,String flag);

    TotalTriratna total(ListITriratnaCommand command);
}
