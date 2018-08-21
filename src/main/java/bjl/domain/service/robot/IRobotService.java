package bjl.domain.service.robot;

import bjl.application.robot.command.ListRobotCommand;
import bjl.domain.model.robot.Robot;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangjin on 2018/4/26
 */
public interface IRobotService {

    Pagination<Robot> pagination(ListRobotCommand command);

    List<Robot> list(Integer hallType);

    Robot win(BigDecimal winScore, String id);

    Robot reOpen(BigDecimal changeScore,String id);

    int checkName(String name);

    void create(Robot robot);

    Robot delete(String id);

    Robot getById(String id);

    void update(Robot robot);

    void reset();

    void updateById(String id, BigDecimal score);

}
