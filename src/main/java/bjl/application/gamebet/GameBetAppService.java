package bjl.application.gamebet;

import bjl.application.chat.command.CreateChatCommand;
import bjl.application.scoredetailed.IScoreDetailedAppService;
import bjl.application.scoredetailed.command.CreateScoreDetailedCommand;
import bjl.domain.model.scoredetailed.IScoreDetailedRepository;
import bjl.domain.model.scoredetailed.ScoreDetailed;
import bjl.domain.model.user.User;
import bjl.domain.service.gamebet.IGameBetService;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.tcp.GlobalVariable;
import bjl.websocket.command.WSMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangjin on 2017/12/28.
 */
@Service("gameBetAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameBetAppService implements IGameBetAppService{

    @Autowired
    private IGameBetService gameBetService;
    @Autowired
    private IScoreDetailedAppService scoreDetailedAppService;
    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private IScoreDetailedRepository<ScoreDetailed, String> scoreDetailedRepository;

    @Override
    public Object[] gameBet(CreateChatCommand command) {

        Object[] objects = new Object[]{0,0};
        String[] strings = command.getText().split(":");
        if(strings.length <2){
            //下注失败
            objects[0] = 1;
            return objects;
        }
        if(!strings[1].matches("\\d+")){
            //不是数字类型
            objects[0] = 1;
            return objects;
        }
        BigDecimal betScore;
        BigDecimal totalScore;
        try {
            betScore = BigDecimal.valueOf(Integer.valueOf(strings[1]));
            totalScore = BigDecimal.valueOf(Integer.valueOf(strings[1]));

        }catch (NumberFormatException e){
            System.out.println("下注资金转换异常,"+e.getMessage());
            objects[0] = 1;
            return objects;
        }

        if(betScore.compareTo(BigDecimal.valueOf(0)) < 0){
            //下注失败
            objects[0] = 1;
            return objects;
        }


        switch (strings[0]){
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
                break;
            case "6":
                totalScore = betScore.multiply(BigDecimal.valueOf(2));
                break;
            case "7":
                totalScore = betScore.multiply(BigDecimal.valueOf(3));
        }
        //判断用户
        User user = userManagerService.searchByUsername(command.getUserid());

        if(user == null){
            objects[0] = 2;
            return objects;
        }
        //判断用户积分是否足够
        if(user.getScore().compareTo(totalScore) <0){
            objects[0] = 3;
            return objects;
        }
        //检查玩家限红
        Object type = limitRed(strings,user,betScore);

        if(0 != (Integer) type){
            objects[0] = type;
            return objects;
        }
        //扣除用户积分
        user.setScore(user.getScore().subtract(totalScore));
//        user.setPrimeScore(user.getScore().subtract(totalScore));
        userManagerService.update(user);
        //再添加积分明细
        ScoreDetailed scoreDetailed = new ScoreDetailed();
        scoreDetailed.setCreateDate(new Date());
        scoreDetailed.setUser(user);
        scoreDetailed.setNewScore(user.getScore());
        scoreDetailed.setScore(totalScore.multiply(BigDecimal.valueOf(-1)));
        scoreDetailed.setActionType(1);
        scoreDetailedRepository.save(scoreDetailed);
        objects[1] = user;

        return objects;
    }

    private Object limitRed(String[] strings,User user,BigDecimal betScore){

        //玩家是否达到最小限红
        if("1".equals(strings[0]) || "3".equals(strings[0])){  //庄闲最小限红
            if(betScore.compareTo(user.getBankerPlayerMix())<0){
                return 4;
            }
        }else {   //三宝最小限红
            if(betScore.compareTo(user.getTriratnaMix())<0){
                return 4;
            }
        }

        switch (strings[0]){
            case "1":
                //是否大于最大限红
                if(betScore.compareTo(user.getBankerPlayerMax()) >0){
                    return 5;
                }
                break;
            case "2":
                //是否大于最大限红
                if(betScore.compareTo(user.getTriratnaMax()) >0){
                    return 5;
                }
                break;
            case "3":
                //是否大于最大限红
                if(betScore.compareTo(user.getBankerPlayerMax()) >0){
                    return 5;
                }
                break;
            case "4":
                //是否大于最大限红
                if(betScore.compareTo(user.getTriratnaMax()) >0){
                    return 5;
                }
                break;
            case "5":
                //是否大于最大限红
                if(betScore.compareTo(user.getTriratnaMax()) >0){
                    return 5;
                }
                break;

            case "6":
                if(betScore.compareTo(user.getTriratnaMax()) >0){
                    return 5;
                }
                if(betScore.compareTo(user.getTriratnaMax()) >0){
                    return 5;
                }
                break;
            case "7":
                if(betScore.compareTo(user.getTriratnaMax()) >0){
                    return 5;
                }
                if(betScore.compareTo(user.getTriratnaMax()) >0){
                    return 5;
                }
                if(betScore.compareTo(user.getTriratnaMax()) >0){
                    return 5;
                }
                break;
        }
        return 0;
    }
    @Override
    public Object[] repeatBet(CreateChatCommand command, BigDecimal changeScore) {

        Object[] objects = new Object[]{0,0,0,0};
        String[] strings = command.getText().split(":");
        if(strings.length <2){
            //下注失败
            objects[0] = 1;
            return objects;
        }
        if(!strings[1].matches("\\d+")){
            //不是数字类型
            objects[0] = 1;
            return objects;
        }
        BigDecimal betScore;
        try {
            betScore = BigDecimal.valueOf(Integer.valueOf(strings[1]));

        }catch (NumberFormatException e){
            System.out.println("下注资金转换异常,"+e.getMessage());
            objects[0] = 1;
            return objects;
        }

        if(betScore.compareTo(BigDecimal.valueOf(0)) < 0){
            //下注失败
            objects[0] = 1;
            return objects;
        }
        //判断用户
        User user = userManagerService.searchByUsername(command.getUserid());
        if(user == null){
            objects[0] = 2;
            return objects;
        }
        //判断玩家资金是否足够
        if(user.getScore().compareTo(changeScore)<0){
            objects[0] = 3;
            return objects;
        }

        //检查玩家限红
        Object type = limitRed(strings,user,betScore);
        if(0 != (Integer) type){
            objects[0] = type;
            return objects;
        }

        //扣除用户积分
        user.setScore(user.getScore().subtract(changeScore));
//        user.setPrimeScore(user.getScore().subtract(changeScore));
        userManagerService.update(user);
        //再添加积分明细
        ScoreDetailed scoreDetailed = new ScoreDetailed();
        scoreDetailed.setUser(user);
        scoreDetailed.setCreateDate(new Date());
        scoreDetailed.setNewScore(user.getScore());
        scoreDetailed.setScore(changeScore.multiply(BigDecimal.valueOf(-1)));
        scoreDetailed.setActionType(1);
        scoreDetailedRepository.save(scoreDetailed);
        objects[1] = user.getScore();
        objects[2] = user.getPrimeScore();
        objects[3] = user;
        return objects;
    }

    @Override
    public User revokeBet(String username, BigDecimal betScore) {
        CreateScoreDetailedCommand scoreDetailedCommand = new CreateScoreDetailedCommand(username,betScore,1);
        User user = scoreDetailedAppService.create(scoreDetailedCommand);
        if(user != null){
            return user;
        }
        return null;
    }

    @Override
    public Object[] editBet(String[] strings, String username, BigDecimal changeScore) {
        Object[] objects = new Object[]{0,0,0};

        User user = userManagerService.searchByUsername(username);
        if(user == null){
            objects[0] = 2;
            return objects;
        }

        //判断玩家资金是否足够
        if(user.getScore().compareTo(changeScore)<0){
            objects[0] = 3;
            return objects;
        }
        //玩家限红
        for(int i=3;i<8;i++){
            switch (i){
                case 3:
                    if(Integer.valueOf(strings[i]) != 0){
                        if(BigDecimal.valueOf(Integer.valueOf(strings[i])).compareTo(user.getBankerPlayerMix())<0){
                            objects[0] = 4;
                            return objects;
                        }
                        if(BigDecimal.valueOf(Integer.valueOf(strings[i])).compareTo(user.getBankerPlayerMax())>0){
                            objects[0] = 5;
                            return objects;
                        }
                    }
                    break;
                case 4:
                    if(Integer.valueOf(strings[i]) != 0){
                        if(BigDecimal.valueOf(Integer.valueOf(strings[i])).compareTo(user.getBankerPlayerMix())<0){
                            objects[0] = 4;
                            return objects;
                        }
                        if(BigDecimal.valueOf(Integer.valueOf(strings[i])).compareTo(user.getBankerPlayerMax())>0){
                            objects[0] = 5;
                            return objects;
                        }
                    }
                    break;
                case 5:
                    if(Integer.valueOf(strings[i]) != 0){
                        if(BigDecimal.valueOf(Integer.valueOf(strings[i])).compareTo(user.getTriratnaMix())<0){
                            objects[0] = 4;
                            return objects;
                        }
                        if(BigDecimal.valueOf(Integer.valueOf(strings[i])).compareTo(user.getTriratnaMax())>0){
                            objects[0] = 5;
                            return objects;
                        }
                    }
                    break;
                case 6:
                    if(Integer.valueOf(strings[i]) != 0){
                        if(BigDecimal.valueOf(Integer.valueOf(strings[i])).compareTo(user.getTriratnaMix())<0){
                            objects[0] = 4;
                            return objects;
                        }
                        if(BigDecimal.valueOf(Integer.valueOf(strings[i])).compareTo(user.getTriratnaMax())>0){
                            objects[0] = 5;
                            return objects;
                        }
                    }
                    break;
                case 7:
                    if(Integer.valueOf(strings[i]) != 0){
                        if(BigDecimal.valueOf(Integer.valueOf(strings[i])).compareTo(user.getTriratnaMix())<0){
                            objects[0] = 4;
                            return objects;
                        }
                        if(BigDecimal.valueOf(Integer.valueOf(strings[i])).compareTo(user.getTriratnaMax())>0){
                            objects[0] = 5;
                            return objects;
                        }
                    }
                    break;
            }
        }
        //扣除用户积分
        user.setScore(user.getScore().subtract(changeScore));
//        user.setPrimeScore(user.getScore().subtract(changeScore));
        userManagerService.update(user);
        //再添加积分明细
        ScoreDetailed scoreDetailed = new ScoreDetailed();
        scoreDetailed.setUser(user);
        scoreDetailed.setCreateDate(new Date());
        scoreDetailed.setNewScore(user.getScore());
        scoreDetailed.setScore(changeScore.multiply(BigDecimal.valueOf(-1)));
        scoreDetailed.setActionType(1);
        scoreDetailedRepository.save(scoreDetailed);
        objects[1] = user.getScore();
        objects[2] = user.getPrimeScore();
        return objects;
    }
}
