package bjl.application.triratna;

import bjl.application.triratna.command.ListITriratnaCommand;
import bjl.application.triratna.command.TotalTriratna;
import bjl.application.triratna.representation.TriratnaRepresentation;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by zhangjin on 2018/1/15.
 */
public interface ITriratnaAppService {

    Pagination<TriratnaRepresentation> pagination(ListITriratnaCommand command);

    TotalTriratna total(ListITriratnaCommand command);
}
