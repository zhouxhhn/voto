package bjl.domain.service.transfer;

import bjl.application.account.IAccountAppService;
import bjl.application.transfer.command.CreateTransferCommand;
import bjl.application.transfer.command.ListTransferCommand;
import bjl.application.upDownPoint.command.UpDownPointCommand;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.account.Account;
import bjl.domain.model.scoredetailed.IScoreDetailedRepository;
import bjl.domain.model.scoredetailed.ScoreDetailed;
import bjl.domain.model.transfer.ITransferRepository;
import bjl.domain.model.transfer.Transfer;
import bjl.domain.model.user.IUserRepository;
import bjl.domain.model.user.User;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangjin on 2017/12/26.
 */
@Service("transferService")
public class TransferService implements ITransferService{

    @Autowired
    private ITransferRepository<Transfer, String> transferRepository;
    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private IUserRepository<User, String> userRepository;
    @Autowired
    private IAccountAppService accountAppService;
    @Autowired
    private IScoreDetailedRepository<ScoreDetailed, String> scoreDetailedRepository;

    /**
     * 创建转账记录
     * @param command
     * @return
     */
    @Override
    public Transfer create(CreateTransferCommand command) {

        User transferUser = userManagerService.searchByUsername(command.getTransfer());
        User receiveUser = userManagerService.searchByUsername(command.getReceive());
        if(receiveUser == null || transferUser == null){
            //用户不存在
            return null;
        }
        Account account = transferUser.getAccount();

        if( !"admin".equals(account.getRoles().get(0).getName())){
            if(transferUser.getScore().compareTo(command.getScore()) < 0){
                //积分不足
                return new Transfer(transferUser.getScore());
            }
            //如果不是管理员 则扣除转账用户的积分
            transferUser.setScore(transferUser.getScore().subtract(command.getScore()));
            transferUser.setPrimeScore(transferUser.getPrimeScore().subtract(command.getScore()));
            userRepository.update(transferUser);
            //添加积分明细
            ScoreDetailed scoreDetailed = new ScoreDetailed();
            scoreDetailed.setActionType(6);
            scoreDetailed.setScore(command.getScore());
            scoreDetailed.setNewScore(transferUser.getScore());
            scoreDetailed.setUser(transferUser);
            scoreDetailed.setCreateDate(new Date());
            scoreDetailedRepository.save(scoreDetailed);
        }
        //创建转账记录
        Transfer transfer = new Transfer();
        transfer.setTransfer(transferUser.getAccount());
        transfer.setReceive(receiveUser.getAccount());
        transfer.setScore(command.getScore());
        transfer.setTransferScore(transferUser.getScore());
        transfer.setReceiveScore(receiveUser.getScore());
        transfer.setStatus(1);
        transfer.setCreateDate(new Date());
        transferRepository.save(transfer);

        return transfer;
    }

    /**
     * 获取转账记录
     * @return
     */
    @Override
    public List<Transfer> list(String userId) {

        if(CoreStringUtils.isEmpty(userId)){
            return null;
        }
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();

        criterionList.add(Restrictions.or(Restrictions.eq("transfer.userName",userId),
                Restrictions.eq("receive.userName", userId)));
        aliasMap.put("transfer","transfer");
        aliasMap.put("receive","receive");

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return transferRepository.list(criterionList,orderList,null,null,aliasMap,30);
    }


    @Override
    public Pagination<Transfer> pagination(ListTransferCommand command) {

        List<Criterion> criterionList = new ArrayList<>();

        if(!CoreStringUtils.isEmpty(command.getTransferName())){
            Account account = accountAppService.searchByName(command.getTransferName());
            criterionList.add(Restrictions.eq("transfer",account));
        }
        if(!CoreStringUtils.isEmpty(command.getReceiveName())){
            Account account = accountAppService.searchByName(command.getReceiveName());
                criterionList.add(Restrictions.eq("receive",account));
        }

        if(command.getStatus() != null && command.getStatus() != 0){
            criterionList.add(Restrictions.eq("status",command.getStatus()));
        }

        if ((!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss"))) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")) {
            criterionList.add(Restrictions.lt("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")));
        }

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return transferRepository.pagination(command.getPage(),command.getPageSize(),criterionList,orderList);
    }

    /**
     * 允许转账
     * @param id
     * @return
     */
    @Override
    public Transfer pass(String id) {

        Transfer transfer = transferRepository.getById(id);
        if(transfer != null){
            User receiveUser = userManagerService.searchByUsername(transfer.getReceive().getUserName());
            if(receiveUser != null){
                //增加收款用户积分
                receiveUser.setScore(receiveUser.getScore().add(transfer.getScore()));
                receiveUser.setPrimeScore(receiveUser.getPrimeScore().add(transfer.getScore()));
                userRepository.update(receiveUser);

                //添加积分明细
                ScoreDetailed scoreDetailed = new ScoreDetailed();
                scoreDetailed.setActionType(6);
                scoreDetailed.setScore(transfer.getScore());
                scoreDetailed.setNewScore(receiveUser.getScore());
                scoreDetailed.setUser(receiveUser);
                scoreDetailed.setCreateDate(new Date());
                scoreDetailedRepository.save(scoreDetailed);

                //更新转账记录
                transfer.setStatus(2); //允许转账
                transfer.setReceiveScore(receiveUser.getScore());
                transferRepository.update(transfer);

                return transfer;
            }
        }
        return null;
    }

    /**
     * 拒绝转账
     * @param id
     * @return
     */
    @Override
    public Transfer refuse(String id) {

        Transfer transfer = transferRepository.getById(id);

        if(transfer != null){
            User transferUser = userManagerService.searchByUsername(transfer.getTransfer().getUserName());
            if(transferUser != null){

                //返回转账人积分
                transferUser.setScore(transferUser.getScore().add(transfer.getScore()));
                transferUser.setPrimeScore(transferUser.getPrimeScore().add(transfer.getScore()));
                userRepository.update(transferUser);

                //添加积分明细
                ScoreDetailed scoreDetailed = new ScoreDetailed();
                scoreDetailed.setActionType(6);
                scoreDetailed.setScore(transfer.getScore());
                scoreDetailed.setNewScore(transferUser.getScore());
                scoreDetailed.setUser(transferUser);
                scoreDetailed.setCreateDate(new Date());
                scoreDetailedRepository.save(scoreDetailed);

                //更新转账记录
                transfer.setStatus(3); //拒绝转账
                transferRepository.update(transfer);

                return transfer;
            }
        }
        return null;
    }

}
