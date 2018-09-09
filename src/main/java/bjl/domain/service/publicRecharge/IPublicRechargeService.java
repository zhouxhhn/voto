package bjl.domain.service.publicRecharge;

import bjl.application.notice.command.ListNoticeCommand;
import bjl.domain.model.activity.Activity;
import bjl.domain.model.publicRecharge.PublicRecharge;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by zhangjin on 2018/6/21
 */
public interface IPublicRechargeService {

    Pagination<PublicRecharge> pagination(ListNoticeCommand command);

    void create(String title, String content, String image);

    void delete(String id);

    List<PublicRecharge> list();
}
