package bjl.domain.service.scoredetailed;

import bjl.application.scoredetailed.command.CreateScoreDetailedCommand;
import bjl.domain.model.scoredetailed.IScoreDetailedRepository;
import bjl.domain.model.scoredetailed.ScoreDetailed;
import bjl.domain.model.user.IUserRepository;
import bjl.domain.model.user.User;
import bjl.domain.service.userManager.IUserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangjin on 2017/12/26.
 */
@Service("scoreDetailedService")
public class ScoreDetailedService implements IScoreDetailedService{

    @Autowired
    private IScoreDetailedRepository<ScoreDetailed, String> scoreDetailedRepository;
    @Autowired
    private IUserRepository<User,String> userRepository;
    @Autowired
    private IUserManagerService userManagerService;

    /**
     * 用户积分变更
     * @param command
     * @return
     */
    @Override
    public User create(CreateScoreDetailedCommand command) {

        try {
            User user = userManagerService.searchByUsername(command.getUserId());

            if (user == null){
                return null;
            }
            BigDecimal newScore = user.getScore().add(command.getScore());
            if(newScore.compareTo(BigDecimal.valueOf(0)) <0 ){
                //用户积分不足
                return null;
            }
            //先更新用户积分
//            user.setTotalScore(newScore);
            user.setScore(newScore);
//            user.setDateScore(user.getDateScore().add(command.getScore()));
            userRepository.save(user);
            //增加用户积分变更记录
            ScoreDetailed scoreDetailed = new ScoreDetailed();
            scoreDetailed.setCreateDate(new Date());
            scoreDetailed.setActionType(command.getActionType());
            scoreDetailed.setScore(command.getScore());
            scoreDetailed.setNewScore(newScore);
            scoreDetailed.setUser(user);
            scoreDetailedRepository.save(scoreDetailed);
            return user;

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("更新用户【"+command.getUserId()+"】积分异常..........");
        }
        return null;
    }
}
