package bjl.application.userManager;

import bjl.application.financialSummary.command.ListFinancialSummaryCommand;
import bjl.application.userManager.command.CreateUserCommand;
import bjl.application.userManager.command.ListUserCommand;
import bjl.application.userManager.command.ModifyUserCommand;

import bjl.application.userManager.command.RegisterUserCommand;
import bjl.application.userManager.representation.UserManagerRepresentation;
import bjl.core.mapping.MappingService;
import bjl.domain.model.account.Account;
import bjl.domain.model.user.User;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dyp on 2017-12-14.
 */
@Service("userManagerAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UserManagerAppService implements IUserManagerAppService{

    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private MappingService mappingService;

    @Override
    public Pagination<UserManagerRepresentation> pagination(ListUserCommand command) {
        command.verifyPage();
        command.verifyPageSize(18);
        Pagination<User> pagination=userManagerService.pagination(command);
        List<UserManagerRepresentation> data=mappingService.mapAsList(pagination.getData(),UserManagerRepresentation.class);
        return  new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }


    //查看全部用户

    @Override
    public List<User> list(ListUserCommand command) {
        List<User>  userList = userManagerService.list(command);

        return userList;
    }


    //增加用户
    @Override
    public  void create(CreateUserCommand command) {
      userManagerService.create(command);

    }

    //注册用户
    @Override
    public JSONObject register(RegisterUserCommand command) {
        return userManagerService.register(command);
    }

    @Override
    public JSONObject login(RegisterUserCommand command) {
        return userManagerService.login(command);
    }

    @Override
    public JSONObject info(JSONObject jsonObject) {
        return userManagerService.info(jsonObject);
    }


    //删除用户
    @Override
    public void delete(User user) {
        userManagerService.delete(user);
    }


    //修改用户
    @Override
    public void update(ModifyUserCommand user) {

       userManagerService.update(user);
    }

    //批量修改用户

    @Override
    public void updateALL(ModifyUserCommand command) {

        userManagerService.listUpdate(command);

    }

    @Override
    public Object[] sum(ListUserCommand command) {
        return userManagerService.sum(command);
    }

    @Override
    public User setTop(String userId) {
        return userManagerService.setTop(userId);
    }

    @Override
    public User setVirtual(String id) {
        return userManagerService.setVirtual(id);
    }

    @Override
    public User setPrintScreen(String id) {
        return userManagerService.setPrintScreen(id);
    }

    @Override
    public User update(User user) {
        return userManagerService.update(user);
    }

    @Override
    public User searchByAccount(Account account) {
        return userManagerService.searchByAccount(account);
    }

    @Override
    public User searchByUsername(String username) {
        return userManagerService.searchByUsername(username);
    }

    @Override
    public List<User> listAll() {
        return userManagerService.listAll();
    }

    @Override
    public User changeScore(String id, BigDecimal score, Integer type) {
        return userManagerService.changeScore(id,score,type);
    }

    @Override
    public User getById(String id) {
        return userManagerService.getById(id);
    }
}
