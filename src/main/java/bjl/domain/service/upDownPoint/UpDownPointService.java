package bjl.domain.service.upDownPoint;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.application.upDownPoint.command.CreateUpDownPoint;
import bjl.application.upDownPoint.command.SumUpDownPoint;
import bjl.application.upDownPoint.command.UpDownPointCommand;
import bjl.application.userManager.command.ModifyUserCommand;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.upDownPoint.IUpDownPointRepository;
import bjl.domain.model.upDownPoint.UpDownPoint;
import bjl.domain.model.user.IUserRepository;
import bjl.domain.model.user.User;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.apache.shiro.SecurityUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by weixing on 2018/1/14.
 */
@Service("upDownPointService")
public class UpDownPointService implements IUpDownPointService{
    @Autowired
    private IUserRepository<User,String> userRepository;
    @Autowired
    private IUpDownPointRepository<UpDownPoint,String> upDownPointRepository;


    @Override
    public void create(ModifyUserCommand command) {
        if(command.getUpDownPoint()!=null){
            User user1 = userRepository.getById(command.getId());
            AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
            UpDownPoint upDownPoint=new UpDownPoint();
            upDownPoint.setCreateDate(new Date());
            upDownPoint.setOperationUser(sessionUser.getUserName());
            upDownPoint.setUserName(user1.getAccount().getUserName());
            upDownPoint.setName(user1.getAccount().getName());
            upDownPoint.setUpDownPoint(command.getUpDownPoint());
            if(command.getUpDownPoint().compareTo(BigDecimal.valueOf(0))>=0){
                upDownPoint.setUpDownPointType(1);//上分
            }else{
                upDownPoint.setUpDownPointType(2);//下分
            }
            upDownPointRepository.save(upDownPoint);
        }
    }

    public Pagination<UpDownPoint> pagination(UpDownPointCommand command) {

            List<Criterion> criterionList = new ArrayList<>();
            if(!CoreStringUtils.isEmpty(command.getUserName())){
                criterionList.add(Restrictions.like("userName", command.getUserName(), MatchMode.ANYWHERE));
            }
            if(!CoreStringUtils.isEmpty(command.getOperationUser())){
                criterionList.add(Restrictions.like("operationUser", command.getOperationUser(),MatchMode.ANYWHERE));
            }
            if(command.getUpDownPointType() != null && command.getUpDownPointType() != 0){
                criterionList.add(Restrictions.eq("upDownPointType", command.getUpDownPointType()));
            }
            if ((!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss"))
                    || (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss"))) {
                criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")));
            }
            if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")) {
                criterionList.add(Restrictions.lt("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")));
            }
            List<Order> orderList = new ArrayList<>();
            orderList.add(Order.desc("createDate"));

            return upDownPointRepository.pagination(command.getPage(),command.getPageSize(),criterionList,orderList);
        }

    @Override
    public Map<String, BigDecimal> sum(Date date) {

        return upDownPointRepository.sum(date);
    }


}
