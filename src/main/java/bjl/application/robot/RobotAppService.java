package bjl.application.robot;

import bjl.application.robot.command.ListRobotCommand;
import bjl.domain.model.robot.Robot;
import bjl.domain.service.robot.IRobotService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangjin on 2018/4/26
 */
@Service("robotAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RobotAppService implements IRobotAppService{

    @Autowired
    private IRobotService robotService;

    @Override
    public Pagination<Robot> pagination(ListRobotCommand command) {
        command.verifyPage();
        command.setPageSize(18);

        return robotService.pagination(command);
    }

    @Override
    public List<Robot> list(Integer hallType) {
        return robotService.list(hallType);
    }

    @Override
    public Robot win(BigDecimal winScore,String id) {
        return robotService.win(winScore,id);
    }

    @Override
    public Robot reOpen(BigDecimal changeScore, String id) {
        return robotService.reOpen(changeScore,id);
    }

    @Override
    public int checkName(String name) {
        return robotService.checkName(name);
    }

    @Override
    public void create(Robot robot) {
        robotService.create(robot);
    }

    @Override
    public Robot delete(String id) {
        return robotService.delete(id);
    }

    @Override
    public Integer bet(String id,Integer score) {

        Robot robot = robotService.getById(id);

        if(robot != null){

            //机器人积分是否足够
            if( robot.getScore().intValue() - score >=0){
                robot.setScore(robot.getScore().subtract(BigDecimal.valueOf(score)));
                //更新机器人积分
                robotService.update(robot);
                return robot.getScore().intValue();
            }
        }
        return null;
    }

    @Override
    public void reset() {
        robotService.reset();
    }

    @Override
    public void updateById(String id, BigDecimal score) {
        robotService.updateById(id,score);
    }
}
