package bjl.application.personal;

import bjl.application.personal.command.ListPersonalCommand;
import bjl.application.personal.reprensentation.PersonalRepresentation;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by zhangjin on 2018/1/15.
 */
public interface IPersonalAppService {

    Pagination<PersonalRepresentation> pagination(ListPersonalCommand command);
}
