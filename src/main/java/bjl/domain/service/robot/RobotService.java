package bjl.domain.service.robot;

import bjl.application.robot.command.ListRobotCommand;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.robot.IRobotRepository;
import bjl.domain.model.robot.Robot;
import bjl.domain.service.account.IAccountService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import bjl.tcp.GlobalVariable;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangjin on 2018/4/26
 */
@Service("robotService")
public class RobotService implements IRobotService{

    @Autowired
    private IRobotRepository<Robot,String> robotRepository;
    @Autowired
    private IAccountService accountService;


    @Override
    public Pagination<Robot> pagination(ListRobotCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        if(!CoreStringUtils.isEmpty(command.getName())){
            criterionList.add(Restrictions.like("name",command.getName(), MatchMode.ANYWHERE));
        }
        if(command.getHallType() != null && command.getHallType() != 0){
            criterionList.add(Restrictions.eq("hallType",command.getHallType()));
        }

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return robotRepository.pagination(command.getPage(),command.getPageSize(),criterionList,orderList);
    }

    /**
     * 取出指定厅的所有机器人
     * @param hallType 大厅类型
     * @return 机器人列表
     */
    @Override
    public List<Robot> list(Integer hallType) {

        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("hallType",hallType));

        return robotRepository.list(criterionList,null);
    }

    /**
     * 机器人输赢积分
     * @param winScore 赢的积分
     * @param id
     * @return
     */
    @Override
    public Robot win(BigDecimal winScore,String id) {

        Robot robot = robotRepository.getById(id);
        if(robot != null){
            //如果赢钱
            if(winScore.compareTo(BigDecimal.valueOf(0)) > 0){
                robot.setScore(robot.getScore().add(winScore));
                robotRepository.update(robot);
            }
            return robot;
        }
        return null;
    }

    /**
     * 重开积分改变
     * @param changeScore 改变的积分
     * @param id
     * @return
     */
    @Override
    public Robot reOpen(BigDecimal changeScore, String id) {
        Robot robot = robotRepository.getById(id);
        if(robot != null){
            //如果积分有改变
            if(changeScore.compareTo(BigDecimal.valueOf(0)) != 0){
                robot.setScore(robot.getScore().add(changeScore));
                robotRepository.update(robot);
            }
            return robot;
        }
        return null;
    }

    /**
     *  检查机器人昵称是否存在
     * @param name 昵称
     * @return
     */
    @Override
    public int checkName(String name) {
        //是否和玩家同名
        if(accountService.searchByName(name) != null){
           return 1;
        }
        //是否和机器人同名
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("name",name));
        List<Robot> list = robotRepository.list(criterionList,null);
        if(list.size()> 0){
            return 2;
        }
        return 0;
    }

    /**
     * 创建机器人
     * @param robot
     */
    @Override
    public void create(Robot robot) {
        robot.setCreateDate(new Date());
        robot.setPrimeScore(robot.getScore());
        if(robot.getBankPlayRatio() == null){
            robot.setBankPlayRatio(BigDecimal.valueOf(0));
        }
        if(robot.getTriratnaRatio() == null){
            robot.setTriratnaRatio(BigDecimal.valueOf(0));
        }
        robotRepository.save(robot);
        //添加机器人
        if(GlobalVariable.getRobotMap().get(robot.getHallType()) == null){
            Map<String,Robot> map = new HashMap<>();
            map.put(robot.getId(),robot);
            GlobalVariable.getRobotMap().put(robot.getHallType(),map);
        }else {
            GlobalVariable.getRobotMap().get(robot.getHallType()).put(robot.getId(),robot);
        }
    }

    /**
     * 删除机器人
     * @param id 机器人ID
     * @return Robot
     */
    @Override
    public Robot delete(String id) {

        Robot robot = robotRepository.getById(id);
        if(robot != null){
            robotRepository.delete(robot);
            //从内存中删除机器人
            Map<String,Robot> map = GlobalVariable.getRobotMap().get(robot.getHallType());
            if(GlobalVariable.getRobotMap().get(robot.getHallType()) != null){
                map.remove(id);
            }
            return robot;
        }
        return null;
    }

    @Override
    public Robot getById(String id) {
        return robotRepository.getById(id);
    }

    @Override
    public void update(Robot robot) {
        robotRepository.update(robot);
    }

    /**
     * 重置机器人初始分
     */
    @Override
    public void reset() {

        List<Robot> robotList = robotRepository.findAll();
        for(Robot robot : robotList){
            robot.setPrimeScore(robot.getScore());
            robotRepository.update(robot);
        }
    }

    @Override
    public void updateById(String id, BigDecimal score) {

        Robot robot = robotRepository.getById(id);
        if(robot != null){
            robot.setScore(score);
            robotRepository.update(robot);
        }
    }

}
