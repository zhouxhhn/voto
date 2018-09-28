package bjl.application.upDownPoint;


import bjl.application.upDownPoint.command.CreateUpDownPoint;
import bjl.application.upDownPoint.command.SumUpDownPoint;
import bjl.application.upDownPoint.command.UpDownPointCommand;
import bjl.application.userManager.command.ModifyUserCommand;
import bjl.domain.model.account.Account;
import bjl.domain.model.upDownPoint.UpDownPoint;
import bjl.domain.model.upDownPoint.UpDownPointAgent;
import bjl.domain.service.account.IAccountService;
import bjl.domain.service.upDownPoint.IUpDownPointService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by dyp on 2017-12-26.
 */
@Service("upDownPointAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UpDownPointAppService implements IUpDownPointAppService{

    @Autowired
    private IUpDownPointService upDownPointService;

    @Autowired
    private IAccountService accountService;

    @Override
    public Pagination<UpDownPointAgent> pagination(UpDownPointCommand command) {
        if(command.getFirstAgent() !=null && !"".equals(command.getFirstAgent().trim())){
            return agentPagination(command);
        }
        command.verifyPage();
        command.verifyPageSize(18);

        Pagination<UpDownPoint> pagination = upDownPointService.pagination(command);
        List<UpDownPointAgent> agentList = new ArrayList<>();
        Pagination<UpDownPointAgent> upDownPointAgentPagination  =
            new Pagination<>(null,pagination.getCount(),pagination.getPage(),pagination.getPageSize());

        if(pagination != null && pagination.getData()!=null && !pagination.getData().isEmpty()){
            List<UpDownPoint> list = pagination.getData();

            for (int i = 0,size = list.size(); i < size; i++) {
                UpDownPoint upDownPoint =  list.get(i);
                UpDownPointAgent agent = new UpDownPointAgent();
                BeanUtils.copyProperties(upDownPoint,agent);
                String name = upDownPoint.getName();
                Account account = accountService.searchByName(name);
                if(account == null){
                    agentList.add(agent);
                    continue;
                }

                Account parent = account.getParent();
                while(parent != null && parent.getParent() != null){
                    parent = parent.getParent();
                }
                String parentName = null;
                if(parent != null){
                    parentName = parent.getName();
                }
                agent.setFirtAngent(parentName);
                Account referee  = account.getReferee();
                String refereeName = null;
                if(referee != null){
                    refereeName = referee.getName();
                }
                agent.setRefree(refereeName);
                //没有代理又没有推荐人，就是公司
                if(parentName == null && refereeName == null){
                    agent.setCompany("公司");
                }
                agentList.add(agent);
            }
        }
        upDownPointAgentPagination.setData(agentList);

        return upDownPointAgentPagination;
    }

    private Pagination<UpDownPointAgent> agentPagination(UpDownPointCommand command) {
        //找不到该一级代理名称
        Account firstAgent = accountService.searchByName(command.getFirstAgent());
        if(firstAgent == null){
            return new Pagination(null,0,1,18);
        }
        int page = 1;
        if(command.getPage() != null){
            page = command.getPage();
        }
        command.verifyPage();
        command.verifyPageSize(Integer.MAX_VALUE);
        UpDownPointCommand agentCommand = new UpDownPointCommand();
        BeanUtils.copyProperties(command,agentCommand);
        agentCommand.setPage(1);
        agentCommand.setPageSize(Integer.MAX_VALUE);
        Pagination<UpDownPoint> pagination = upDownPointService.pagination(agentCommand);
        List<UpDownPointAgent> agentList = new ArrayList<>();
        Pagination<UpDownPointAgent> upDownPointAgentPagination  =
            new Pagination<>(null,pagination.getCount(),1,pagination.getPageSize());

        if(pagination != null && pagination.getData()!=null && !pagination.getData().isEmpty()){
            List<UpDownPoint> list = pagination.getData();

            for (int i = 0,size = list.size(); i < size; i++) {
                UpDownPoint upDownPoint =  list.get(i);
                UpDownPointAgent agent = new UpDownPointAgent();
                BeanUtils.copyProperties(upDownPoint,agent);
                String name = upDownPoint.getName();
                Account account = accountService.searchByName(name);
                if(account == null){
                    continue;
                }

                Account parent = account.getParent();
                while(parent != null && parent.getParent() != null){
                    parent = parent.getParent();
                }
                String parentName = null;
                if(parent != null){
                    parentName = parent.getName();
                    if (!firstAgent.getToken().equals(parent.getToken())) {
                       continue;
                    }
                }else{
                    continue;
                }

                agent.setFirtAngent(parentName);
                Account referee  = account.getReferee();
                String refereeName = null;
                if(referee != null){
                    refereeName = referee.getName();
                }
                agent.setRefree(refereeName);
                //没有代理又没有推荐人，就是公司
                if(parentName == null && refereeName == null){
                    agent.setCompany("公司");
                }
                agentList.add(agent);
            }
        }
        upDownPointAgentPagination.setCount(agentList.size());
        if(agentList.size()>18){

            if(agentList.size() >page*18){
                agentList = agentList.subList((page-1)*18,page*18);
            }else{
                agentList = agentList.subList((page-1)*18,agentList.size());
            }
        }
        upDownPointAgentPagination.setPageSize(18);
        upDownPointAgentPagination.setPage(command.getPage());
        upDownPointAgentPagination.setData(agentList);
        return upDownPointAgentPagination;
    }

    public Pagination<UpDownPointAgent> export(UpDownPointCommand command) {
        //找不到该一级代理名称
        Account firstAgent = null;
        if(command.getFirstAgent() !=null && !"".equals(command.getFirstAgent().trim())){
            firstAgent = accountService.searchByName(command.getFirstAgent());
            if(firstAgent == null){
                return new Pagination(null,0,1,18);
            }
        }


        command.verifyPage();
        command.verifyPageSize(Integer.MAX_VALUE);

        Pagination<UpDownPoint> pagination = upDownPointService.pagination(command);
        List<UpDownPointAgent> agentList = new ArrayList<>();
        Pagination<UpDownPointAgent> upDownPointAgentPagination  =
            new Pagination<>(null,pagination.getCount(),pagination.getPage(),pagination.getPageSize());

        if(pagination != null && pagination.getData()!=null && !pagination.getData().isEmpty()){
            List<UpDownPoint> list = pagination.getData();

            for (int i = 0,size = list.size(); i < size; i++) {
                UpDownPoint upDownPoint =  list.get(i);
                UpDownPointAgent agent = new UpDownPointAgent();
                BeanUtils.copyProperties(upDownPoint,agent);
                String name = upDownPoint.getName();
                Account account = accountService.searchByName(name);
                if(account == null){
                    continue;
                }

                Account parent = account.getParent();
                while(parent != null && parent.getParent() != null){
                    parent = parent.getParent();
                }
                String parentName = null;
                if(parent != null){
                    parentName = parent.getName();
                    if(command.getFirstAgent() !=null && !"".equals(command.getFirstAgent().trim())){
                        if (!firstAgent.getToken().equals(parent.getToken())) {
                            continue;
                        }
                    }
                }else{
                    if(command.getFirstAgent() !=null && !"".equals(command.getFirstAgent().trim())){
                        continue;
                    }

                }

                agent.setFirtAngent(parentName);
                Account referee  = account.getReferee();
                String refereeName = null;
                if(referee != null){
                    refereeName = referee.getName();
                }
                agent.setRefree(refereeName);
                //没有代理又没有推荐人，就是公司
                if(parentName == null && refereeName == null){
                    agent.setCompany("公司");
                }
                agentList.add(agent);
            }
        }

        upDownPointAgentPagination.setData(agentList);
        return upDownPointAgentPagination;
    }



    @Override
    public Map<String, BigDecimal> sum(Date date) {
        return upDownPointService.sum(date);
    }

    @Override
    public void create(ModifyUserCommand command) {
        upDownPointService.create(command);

    }
}
