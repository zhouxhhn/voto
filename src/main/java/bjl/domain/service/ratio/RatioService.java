package bjl.domain.service.ratio;

import bjl.application.agent.IAgentAppService;
import bjl.application.agent.IAgentConfigAppService;
import bjl.application.ratio.command.EditRatioCommand;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.bank.Bank;
import bjl.domain.model.ratio.IRatioRepository;
import bjl.domain.model.ratio.Ratio;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangjin on 2018/5/2
 */
@Service("ratioService")
public class RatioService implements IRatioService{

    @Autowired
    private IRatioRepository<Ratio,String> ratioRepository;
    @Autowired
    private IAgentAppService agentAppService;
    @Autowired
    private IAgentConfigAppService agentConfigAppService;

    @Override
    public Ratio create(Account account) {

        Ratio ratio = new Ratio();
        ratio.setSecondFact(BigDecimal.valueOf(0));
        ratio.setFirstFact(BigDecimal.valueOf(0));
        ratio.setCreateDate(new Date());
        ratio.setAccount(account);
        ratioRepository.save(ratio);
        return ratio;
    }

    @Override
    public Ratio getByAccount(String accountId) {

        List<Criterion> criterionList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("account","account");
        criterionList.add(Restrictions.eq("account.id",accountId));
        List<Ratio> list = ratioRepository.list(criterionList,null,null,null,map);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public Ratio edit(EditRatioCommand command) {

        Ratio ratio = ratioRepository.getById(command.getId());
        if(ratio != null){

            if(command.getUserR() == null || command.getFirstFact() == null || command.getSecondFact() == null || command.getCompanyFact() == null){
                return null;
            }

            if(BigDecimal.valueOf(100).compareTo(command.getCompanyFact().add(command.getFirstFact()).add(command.getSecondFact())) <0){
                return null;
            }
            AgentConfig parentConfig = agentConfigAppService.getByAccount(agentAppService.getAgent(ratio.getAccount()));
            if(parentConfig != null && command.getUserR() != null && command.getUserR().compareTo(parentConfig.getValueR()) >0){
                return null;
            }
            ratio.getAccount().setR(command.getUserR());
            ratio.setFirstFact(command.getFirstFact());
            ratio.setSecondFact(command.getSecondFact());
            ratio.setCompanyFact(command.getCompanyFact());
            ratioRepository.update(ratio);
            return ratio;
        }
        return null;
    }

    @Override
    public void update(Ratio ratio) {
        ratioRepository.save(ratio);
    }
}
