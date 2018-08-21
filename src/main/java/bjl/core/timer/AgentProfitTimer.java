package bjl.core.timer;

import bjl.application.agent.IAgentAppService;
import bjl.application.agent.IAgentConfigAppService;
import bjl.application.agent.IAgentProfitAppService;
import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.agent.command.EditAgentConfigCommand;
import bjl.application.phonebet.command.CountPhoneBetCommand;
import bjl.application.ratio.IRatioAppService;
import bjl.application.ratio.command.EditRatioCommand;
import bjl.application.ratio.representation.RatioRepresentation;
import bjl.application.recharge.IRechargeAppService;
import bjl.application.upDownPoint.IUpDownPointAppService;
import bjl.application.upDownPoint.command.SumUpDownPoint;
import bjl.application.userManager.IUserManagerAppService;
import bjl.core.util.ServiceUtil;
import bjl.domain.model.account.Account;
import bjl.domain.model.agent.AgentConfig;
import bjl.domain.model.agent.AgentProfit;
import bjl.domain.model.profitrecord.ProfitRecord;
import bjl.domain.model.systemconfig.SystemConfig;
import bjl.domain.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 代理收益计算类
 * Created by zhangjin on 2018/1/12.
 */
public class AgentProfitTimer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void profit(){

        logger.info("开始计算代理收益");

        SystemConfig config = ServiceUtil.serviceUtil.getSystemConfigAppService().get();
        IUpDownPointAppService upDownPointAppService = ServiceUtil.serviceUtil.getUpDownPointAppService();
        IAgentAppService agentAppService = ServiceUtil.serviceUtil.getAgentAppService();
        IAgentProfitAppService agentProfitAppService = ServiceUtil.serviceUtil.getAgentProfitAppService();
        IAgentConfigAppService agentConfigAppService = ServiceUtil.serviceUtil.getAgentConfigAppService();
        IRechargeAppService rechargeAppService = ServiceUtil.serviceUtil.getRechargeAppService();
        IUserManagerAppService userManagerAppService = ServiceUtil.serviceUtil.getUserManagerAppService();
        IRatioAppService ratioAppService = ServiceUtil.serviceUtil.getRatioAppService();

        ProfitRecord profitRecord = ServiceUtil.serviceUtil.getProfitRecordAppService().getByTime();
        Date date = null;
        if(profitRecord != null){
            date = profitRecord.getCreateDate();
        }
        //前一天玩家的洗码量
        List<CountGameDetailedCommand> listWT = ServiceUtil.serviceUtil.getGameDetailedAppService().list(date); //微投洗码
        logger.info("微投游戏玩家人数[{}]",listWT.size());
        List<CountGameDetailedCommand> listDT = ServiceUtil.serviceUtil.getPhoneBetAppService().count(date);//电投洗码量
        logger.info("电投游戏玩家人数[{}]",listDT.size());
        listWT.addAll(listDT);

        //玩家R点数,用户计算玩家返水
        BigDecimal nR = config.getPlayerR().divide(BigDecimal.valueOf(100));
        //充值手续费率
        BigDecimal rechargeFee = config.getRechargeFee().divide(BigDecimal.valueOf(100));
        //上分手续费率
        BigDecimal upScoreFee = config.getUpScoreFee().divide(BigDecimal.valueOf(100));

        Integer init = 0;
        Integer profit = 0;

        if (listWT.size() > 0) {
            //当前统计时间内的所有玩家上分
            Map<String, BigDecimal> addScoreMap = upDownPointAppService.sum(date);
            //当前统计时间内的所有玩家充值
            Map<String, BigDecimal> rechargeMap = rechargeAppService.sum(date);
            //遍历每个玩家的直接代理
            for (CountGameDetailedCommand command : listWT) {

                if(command.getUser().getVirtual() == 2){
                    try {

                        //第三方上分
                        BigDecimal addScore = addScoreMap.get(command.getUser().getAccount().getUserName());
                        if(addScore == null){
                            addScore = BigDecimal.valueOf(0);
                        }
                        //充值上分
                        BigDecimal rechargeScore = rechargeMap.get(command.getUser().getAccount().getUserName());
                        if(rechargeScore == null){
                            rechargeScore = BigDecimal.valueOf(0);
                        }

                        //查询玩家直接上级
                        Account parent = agentAppService.getAgent(command.getUser().getAccount());
                        //玩家码粮
                        BigDecimal R = (command.getUser().getAccount().getR() == null ? nR : command.getUser().getAccount().getR()).divide(BigDecimal.valueOf(100));
                        BigDecimal returnScore = command.getPlayerLose().multiply(R).setScale(0,BigDecimal.ROUND_HALF_UP);

                        if (parent == null) {
                            //玩家没有上级
                            //计算返水 即普通玩家码粮
                            AgentProfit agentProfit = new AgentProfit();
                            BigDecimal temp = BigDecimal.valueOf(0);
                            agentProfit.setReturnScore(returnScore);
                            agentProfit.setSecondFee(temp);
                            agentProfit.setFirstFee(temp);
                            agentProfit.setComRatio(BigDecimal.valueOf(100));
                            agentProfit.setComGrain(agentProfit.getReturnScore());
                            agentProfit.setComFee(addScore.multiply(upScoreFee).add(rechargeScore.multiply(rechargeFee)));
                            agentProfit.setComIncome(BigDecimal.valueOf(-1).multiply(command.getUser().getScore().add(command.getUser().getBankScore()).subtract(command.getUser().getPrimeScore())).subtract(agentProfit.getComGrain()).subtract(agentProfit.getComFee()));
                            agentProfit.setSubBalance(BigDecimal.valueOf(0));
                            agentProfit.setSupBalance(BigDecimal.valueOf(0));
                            agentProfit.setPlayBalance(command.getUser().getScore().add(command.getUser().getBankScore()).subtract(command.getUser().getPrimeScore()).add(command.getPlayerLose().multiply(R)));
                            agentProfit.setSupIncome(temp);
                            agentProfit.setSubIncome(temp);
                            agentProfit.setSubGrain(temp);
                            agentProfit.setSupGrain(temp);
                            agentProfit.setSubPay(temp);
                            agentProfit.setSupPay(temp);
                            agentProfit.setSubRatio(temp);
                            agentProfit.setSupRatio(temp);
                            agentProfit.setPlay(command.getUser().getAccount());
                            agentProfit.setLoseScore(command.getPlayerLose()); //真实转码数
                            agentProfit.setIntervalScore(command.getUser().getScore().add(command.getUser().getBankScore()).subtract(command.getUser().getPrimeScore()));
                            agentProfit.setPlace(command.getType());
                            agentProfit.setPlay(command.getUser().getAccount());
                            agentProfit.setAddScore(addScore);
                            agentProfit.setRechargeScore(rechargeScore);
                            agentProfitAppService.create(agentProfit);

                            continue;
                        }

                        AgentConfig parentConfig = agentConfigAppService.getByAccount(parent);
                        if (parentConfig == null) {
                            continue;
                        }

                        AgentProfit agentProfit = new AgentProfit();
                        agentProfit.setPlace(command.getType());
                        agentProfit.setPlay(command.getUser().getAccount());
                        //真是转码数
                        agentProfit.setLoseScore(command.getPlayerLose()); //真实转码数
                        //上下数  玩家剩余分-初始分
                        agentProfit.setIntervalScore(command.getUser().getScore().add(command.getUser().getBankScore()).subtract(command.getUser().getPrimeScore()));
                        //计算返水 即普通玩家码粮
                        agentProfit.setReturnScore(returnScore);

                        //先计算直接代理收益
                        //1 玩家洗码打折
//                        agentProfit.setDiscount(discount(agentProfit.getLoseScore(), agentProfit.getIntervalScore()));
                        agentProfit.setDiscount(command.getPlayerLose());

                        //第三方上分
                        agentProfit.setAddScore(addScore);
                        //银行卡上分
                        agentProfit.setRechargeScore(rechargeScore);
                        //二级代理默认占比0
                        agentProfit.setSubRatio(BigDecimal.valueOf(0));

                        AgentConfig f = null;
                        AgentConfig s = null;

                        //一级实际占比
                        BigDecimal firstFact = BigDecimal.valueOf(0);
                        //二级实际占比
                        BigDecimal secondFact = BigDecimal.valueOf(0);
                        //计算码粮
                        if ("secondAgent".equals(parent.getRoles().get(0).getName())) {

                            agentProfit.setPlayR(command.getUser().getAccount().getR());
                            agentProfit.setSubR(parentConfig.getValueR());
                            agentProfit.setSubHigh(parentConfig.getHighRatio());

                            //查询一级代理
                            Account top = agentAppService.getAgent(parent);
                            AgentConfig parentSup = agentConfigAppService.getByAccount(top);

                            if(parentSup != null){
                                agentProfit.setSupHigh(parentSup.getHighRatio());
                                agentProfit.setSupR(parentSup.getValueR());
                            }

                            BigDecimal[] bigDecimals = profit(parentSup, parentConfig, agentProfit.getDiscount(), R, parent, top,command.getUser().getAccount(),ratioAppService);
                            agentProfit.setSubGrain(bigDecimals[0]);
                            agentProfit.setSupGrain(bigDecimals[1]);
                            secondFact = bigDecimals[2];
                            firstFact = bigDecimals[3];


                        } else if ("firstAgent".equals(parent.getRoles().get(0).getName())) {

                            if("user".equals(command.getUser().getAccount().getRoles().get(0).getName())){
                                agentProfit.setPlayR(command.getUser().getAccount().getR());
                                agentProfit.setSupHigh(parentConfig.getHighRatio());
                                agentProfit.setSupR(parentConfig.getValueR());
                            } else {
                                AgentConfig subConfig = agentConfigAppService.getByAccount(command.getUser().getAccount());
                                if(subConfig != null){
                                    agentProfit.setSubR(subConfig.getValueR());
                                    agentProfit.setSubHigh(subConfig.getHighRatio());
                                }
                                agentProfit.setSupHigh(parentConfig.getHighRatio());
                                agentProfit.setSupR(parentConfig.getValueR());
                            }

                            BigDecimal[] bigDecimals = profit(parentConfig, null, agentProfit.getDiscount(), R,null, parent, command.getUser().getAccount(),ratioAppService);
                            agentProfit.setSubGrain(bigDecimals[0]);
                            agentProfit.setSupGrain(bigDecimals[1]);
                            secondFact = bigDecimals[2];
                            firstFact = bigDecimals[3];
                        }

                        //付下级
                        if ("secondAgent".equals(parent.getRoles().get(0).getName())) {

                            s = parentConfig;
                            agentProfit.setSecondAgent(parent);

                            //二级代理占比
                            agentProfit.setSubRatio(secondFact);

                            //该代理是二级代理，则查询上一级代理
                            Account top = agentAppService.getAgent(parent);
                            agentProfit.setFirstAgent(top);
                            AgentConfig parentSup = agentConfigAppService.getByAccount(top);

                            if (parentSup != null) {
                                agentProfit.setSupRatio(firstFact);
                                //公司应付码粮和公司占比
                                agentProfit.setComRatio(BigDecimal.valueOf(100).subtract(agentProfit.getSupRatio()).subtract(agentProfit.getSubRatio()));
                                agentProfit.setComGrain(agentProfit.getSupGrain());
                            } else {
                                //公司应付码粮和公司占比
                                agentProfit.setComGrain(agentProfit.getSubGrain());
                                agentProfit.setComRatio(BigDecimal.valueOf(100).subtract(agentProfit.getSubRatio()));
                            }

                            f = parentSup;
                            //付下级
                            BigDecimal[] bigDecimals = free(rechargeFee,upScoreFee,rechargeScore,addScore,parentConfig, parent, top,command.getUser().getAccount(),agentConfigAppService,secondFact,firstFact);

                            //收益
                            //BigDecimal[] bigDecimals1 = grain(parentSup, parentConfig, agentProfit.getDiscount(), agentProfit.getIntervalScore(), parent, top);
                            //手续费
                            agentProfit.setSecondFee(bigDecimals[0]);
                            agentProfit.setFirstFee(bigDecimals[1]);
                            //付下级
//                            agentProfit.setSubPay(bigDecimals[0].subtract(bigDecimals[2]));
//                            agentProfit.setSupPay(bigDecimals[1].subtract(bigDecimals[3]));
                            //收益
                            // 二级收益： -（上下数*二级实际占比）+ 二级码粮 - 二级手续费 -转码数*玩家R值
                            BigDecimal subIncome = BigDecimal.valueOf(-1).multiply(agentProfit.getIntervalScore().multiply(agentProfit.getSubRatio().divide(BigDecimal.valueOf(100)))).add(agentProfit.getSubGrain()).subtract(agentProfit.getSecondFee()).subtract(agentProfit.getDiscount().multiply(R));
                            agentProfit.setSubIncome(subIncome.setScale(0,BigDecimal.ROUND_HALF_UP));
                            // 一级收益： -（上下数*二级实际占比）+ 一级码粮 - 一级手续费 -二级码粮
                            BigDecimal supIncome = BigDecimal.valueOf(-1).multiply(agentProfit.getIntervalScore().multiply(agentProfit.getSupRatio().divide(BigDecimal.valueOf(100)))).add(agentProfit.getSupGrain()).subtract(agentProfit.getFirstFee()).subtract(agentProfit.getSubGrain());
                            agentProfit.setSupIncome(supIncome.setScale(0,BigDecimal.ROUND_HALF_UP));

                        } else if ("firstAgent".equals(parent.getRoles().get(0).getName())) {
                            f = parentConfig;
                            s = null;
                            agentProfit.setFirstAgent(parent);

                            //一级代理占比
                            agentProfit.setSupRatio(command.getUser().getAccount().getRatio() == null ? BigDecimal.valueOf(0) : command.getUser().getAccount().getRatio());
                            //公司应付码粮和公司占比
                            agentProfit.setComRatio(BigDecimal.valueOf(100).subtract(agentProfit.getSupRatio()));
                            agentProfit.setComGrain(agentProfit.getSupGrain());

                            //付下级
                            BigDecimal[] bigDecimals = free(rechargeFee,upScoreFee,rechargeScore,addScore,parentConfig,  null, parent,command.getUser().getAccount(),agentConfigAppService,secondFact,firstFact);

                            //收益
                            //BigDecimal[] bigDecimals1 = grain(parentConfig, null, agentProfit.getDiscount(),agentProfit.getIntervalScore(), null, parent);
                            //手续费
                            agentProfit.setSecondFee(bigDecimals[0]);
                            agentProfit.setFirstFee(bigDecimals[1]);
                            //付下级
//                            agentProfit.setSubPay(bigDecimals[0].subtract(bigDecimals[2]));
//                            agentProfit.setSupPay(bigDecimals[1].subtract(bigDecimals[3]));
                            //收益
                            // 二级收益： -（上下数*二级实际占比）+ 二级码粮 - 二级手续费 -转码数*玩家R值
                            BigDecimal subIncome = BigDecimal.valueOf(-1).multiply(agentProfit.getIntervalScore().multiply(agentProfit.getSubRatio().divide(BigDecimal.valueOf(100)))).add(agentProfit.getSubGrain()).subtract(agentProfit.getSecondFee()).subtract(agentProfit.getDiscount().multiply(R));
                            agentProfit.setSubIncome(subIncome.setScale(0,BigDecimal.ROUND_HALF_UP));
                            // 一级收益： -（上下数*二级实际占比）+ 一级码粮 - 一级手续费 -二级码粮
                            BigDecimal supIncome = BigDecimal.valueOf(-1).multiply(agentProfit.getIntervalScore().multiply(agentProfit.getSupRatio().divide(BigDecimal.valueOf(100)))).add(agentProfit.getSupGrain()).subtract(agentProfit.getFirstFee()).subtract(agentProfit.getSubGrain());
                            agentProfit.setSupIncome(supIncome.setScale(0,BigDecimal.ROUND_HALF_UP));
                        }

                        //公司手续费
                        agentProfit.setComFee(rechargeScore.multiply(rechargeFee).multiply(agentProfit.getComRatio().divide(BigDecimal.valueOf(100)))
                                .add(addScore.multiply(upScoreFee).multiply(agentProfit.getComRatio().divide(BigDecimal.valueOf(100)))).setScale(0,BigDecimal.ROUND_HALF_UP));
                        //公司收益 :  -（上下数*公司实际占成）- 一级码粮 - 公司手续费
                        agentProfit.setComIncome((BigDecimal.valueOf(-1).multiply(agentProfit.getIntervalScore()).multiply(agentProfit.getComRatio().divide(BigDecimal.valueOf(100))).subtract(agentProfit.getComGrain()).subtract(agentProfit.getComFee())).setScale(0,BigDecimal.ROUND_HALF_UP));


                        //验证代理剩余授权分数是否足够
                        if( s == null){
                            agentProfit.setSubPay(BigDecimal.valueOf(0));
                            agentProfit.setSubGrain(BigDecimal.valueOf(0));
                            agentProfit.setSubIncome(BigDecimal.valueOf(0));
                            agentProfit.setSubBalance(BigDecimal.valueOf(0));
                        } else {
                            //更新代理余额
//                            s.getUser().setScore(s.getUser().getScore().add(agentProfit.getSubIncome()));
//                            userManagerAppService.update(s.getUser());
                            //代理余额
                            agentProfit.setSubBalance(s.getUser().getScore().add(agentProfit.getSubIncome()));
                        }
                        if(f == null ){
                            agentProfit.setSupPay(BigDecimal.valueOf(0));
                            agentProfit.setSupGrain(BigDecimal.valueOf(0));
                            agentProfit.setSupIncome(BigDecimal.valueOf(0));
                            agentProfit.setSupBalance(BigDecimal.valueOf(0));
                        }else {
                            //更新代理余额
//                            f.getUser().setScore(f.getUser().getScore().add(agentProfit.getSupIncome()));
//                            userManagerAppService.update(f.getUser());
                            //代理余额
                            agentProfit.setSupBalance(f.getUser().getScore().add(agentProfit.getSupIncome()));

                        }
                        agentProfit.setPlayBalance(command.getUser().getScore().add(command.getUser().getBankScore()).setScale(0,BigDecimal.ROUND_HALF_UP));
                        //保存代理收益记录
                        agentProfitAppService.create(agentProfit);
                        //更新代理配置
                        agentConfigAppService.update(f);
                        agentConfigAppService.update(s);

                        logger.info("玩家[{}]统计成功", command.getUser().getAccount().getName());
                    } catch (Exception e) {
                        profit = 1;
                        logger.info("玩家[{}]统计失败", command.getUser().getAccount().getName());
                        e.printStackTrace();
                    }
                } else {
                    logger.info("玩家[{}]为虚拟玩家不计入统计", command.getUser().getAccount().getName());
                }
            }
        }

        //重置初始分
        List<User> list = userManagerAppService.listAll();


        for(User user : list){
            try {
                user.setDateScore(user.getPrimeScore());
                user.setPrimeScore(user.getScore().add(user.getBankScore()));
                userManagerAppService.update(user);
                logger.info("玩家[{}]重置初始分成功", user.getAccount().getName());
            }catch (Exception e){
                e.printStackTrace();
                logger.info("玩家[{}]统计失败", user.getAccount().getName());
                init = 1;
            }
        }

        //重置机器人初始分
        try {
            ServiceUtil.serviceUtil.getRobotAppService().reset();
            logger.info("重置机器人初始分成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.info("重置机器人初始分发生异常");
        }

        //保存
        ServiceUtil.serviceUtil.getProfitRecordAppService().create(init,profit);

    }

    /**
     * 洗码打折
     * @param bigDecimal 原转码数
     * @return 打折后的转码数
     */
    private BigDecimal discount(BigDecimal bigDecimal,BigDecimal score){

        if(bigDecimal.compareTo(BigDecimal.valueOf(10000)) >=0){

            if(score.compareTo(BigDecimal.valueOf(0)) < 0){
                if((bigDecimal.divide(score,1,BigDecimal.ROUND_HALF_UP).abs()).compareTo(BigDecimal.valueOf(1.5)) < 0){
                    return bigDecimal;
                }
            }
        } else {
            return bigDecimal;
        }

        String a = bigDecimal.intValue()+"";
        Integer low = Integer.valueOf(a.substring(a.length()-1,a.length()));
        Integer high = Integer.valueOf(a.substring(0,1));

        return bigDecimal.multiply(BigDecimal.valueOf(9).subtract(BigDecimal.valueOf(low).add(BigDecimal.valueOf(high)).divide(BigDecimal.valueOf(10)))).divide(BigDecimal.valueOf(10)).setScale(0,BigDecimal.ROUND_HALF_UP);

    }

    /**
     * 收益计算
     *
     * @param firstConfig 一级代理配置
     * @param secondConfig 二级代理配置
     * @param decimal 转码数
     * @param interval 上下数
     * @param sub 二级代理
     * @param sup 一级代理
     * @return
     */
    private BigDecimal[] grain(AgentConfig firstConfig,AgentConfig secondConfig,BigDecimal decimal,BigDecimal interval,Account sub,Account sup){

        BigDecimal[] bigDecimals = new BigDecimal[]{BigDecimal.valueOf(0.0),BigDecimal.valueOf(0.0)};

        if(sub != null){

            //首先算二级代理的实际R值   R点数*(最高占成-所占成)/最高占成
            BigDecimal secondFactR;
            if(secondConfig.getHighRatio() == null || secondConfig.getHighRatio().compareTo(BigDecimal.valueOf(0)) == 0 || secondConfig.getFactRatio().compareTo(BigDecimal.valueOf(0)) == 0){
                secondFactR = secondConfig.getValueR().divide(BigDecimal.valueOf(100));
                bigDecimals[0] = decimal.multiply(secondFactR).setScale(0,BigDecimal.ROUND_HALF_UP);
            }else {
                secondFactR = (secondConfig.getValueR().multiply(secondConfig.getHighRatio().subtract(secondConfig.getFactRatio())).divide(secondConfig.getHighRatio(),4,BigDecimal.ROUND_HALF_UP)).divide(BigDecimal.valueOf(100));
                //二级收益为
                BigDecimal secondGrain = BigDecimal.valueOf(-1).multiply(interval.multiply(secondConfig.getFactRatio().divide(BigDecimal.valueOf(100)))).add(decimal.multiply(secondFactR));
                bigDecimals[0] = secondGrain.setScale(0,RoundingMode.HALF_UP);
            }

            if(sup != null){
                //再计算一级代理的实际R值   R点数*(最高占成-所占成)/最高占成
                if(firstConfig.getHighRatio() == null || firstConfig.getHighRatio().compareTo(BigDecimal.valueOf(0)) == 0){
                    bigDecimals[1] = decimal.multiply(firstConfig.getValueR().divide(BigDecimal.valueOf(100)).subtract(secondFactR)).setScale(0,BigDecimal.ROUND_HALF_UP);
                } else {
                    BigDecimal firstFactR = (firstConfig.getValueR().multiply(firstConfig.getHighRatio().subtract(firstConfig.getFactRatio()).subtract(secondConfig.getFactRatio())).divide(firstConfig.getHighRatio(),4,BigDecimal.ROUND_HALF_UP)).divide(BigDecimal.valueOf(100)).subtract(secondFactR);
                    //一级收益为
                    BigDecimal firstGrain = BigDecimal.valueOf(-1).multiply(interval.multiply(firstConfig.getFactRatio().divide(BigDecimal.valueOf(100)))).add(decimal.multiply(firstFactR));
                    bigDecimals[1] = firstGrain.setScale(0,RoundingMode.HALF_UP);
                }
            }
        } else {
            //二级代理为空
            if(sup != null){
                if(firstConfig.getHighRatio() == null || firstConfig.getHighRatio().compareTo(BigDecimal.valueOf(0)) == 0 || firstConfig.getFactRatio().compareTo(BigDecimal.valueOf(0)) == 0){
                    bigDecimals[1] = decimal.multiply(firstConfig.getValueR().divide(BigDecimal.valueOf(100))).setScale(0,BigDecimal.ROUND_HALF_UP);
                } else {
                    BigDecimal firstFactR = (firstConfig.getValueR().multiply(firstConfig.getHighRatio().subtract(firstConfig.getFactRatio()).subtract(secondConfig.getFactRatio())).divide(firstConfig.getHighRatio(),4,BigDecimal.ROUND_HALF_UP)).divide(BigDecimal.valueOf(100));
                    //一级收益为
                    BigDecimal firstGrain = BigDecimal.valueOf(-1).multiply(interval.multiply(firstConfig.getFactRatio().divide(BigDecimal.valueOf(100)))).add(decimal.multiply(firstFactR));
                    bigDecimals[1] = firstGrain.setScale(0,RoundingMode.HALF_UP);
                }
            }

        }
        return bigDecimals;
    }


    /**
     * 码粮计算
     * @param firstConfig 一级代理配置
     * @param secondConfig 二级代理配置
     * @param decimal 转码数
     * @param R 玩家R值
     * @param sub 下级
     * @param sup 上级
     * @param play 玩家
     * @return
     */
    private BigDecimal[] profit(AgentConfig firstConfig,AgentConfig secondConfig,BigDecimal decimal,BigDecimal R,Account sub,Account sup,Account play, IRatioAppService ratioAppService){

        BigDecimal[] bigDecimals = new BigDecimal[]{BigDecimal.valueOf(0.0),BigDecimal.valueOf(0.0),BigDecimal.valueOf(0),BigDecimal.valueOf(0)};
        //获取占比
        RatioRepresentation representation = ratioAppService.getByAccount(play.getId());

        if(sub != null){

            //首先算二级代理的实际R值
            BigDecimal secondFactRatio = representation.getSecondFact() == null ? BigDecimal.valueOf(0) : representation.getSecondFact();
            BigDecimal firstFactRatio = representation.getFirstFact() == null ? BigDecimal.valueOf(0) : representation.getFirstFact();
            bigDecimals[2] = secondFactRatio;
            bigDecimals[3] = firstFactRatio;
            BigDecimal comFactRatio = BigDecimal.valueOf(100).subtract(secondFactRatio).subtract(firstFactRatio).subtract(BigDecimal.valueOf(10));

            BigDecimal secondFactR;
            if(secondConfig.getHighRatio().compareTo(BigDecimal.valueOf(0)) == 0){
                secondFactR = secondConfig.getValueR().divide(BigDecimal.valueOf(100));
            }else {
                // 二级R点数/(二级实际占成)*（一级实际占比+公司实际占成-10%）
                secondFactR = secondConfig.getValueR().divide(secondConfig.getHighRatio(),4,BigDecimal.ROUND_HALF_UP).multiply(firstFactRatio.add(comFactRatio)).divide(BigDecimal.valueOf(100));
            }
            //二级码粮
            bigDecimals[0] = decimal.multiply(secondFactR).setScale(0,BigDecimal.ROUND_HALF_UP);

            if(sup != null){
                BigDecimal firstFactR;
                if(firstConfig.getHighRatio().compareTo(BigDecimal.valueOf(0)) == 0 ){
                    firstFactR = firstConfig.getValueR().divide(BigDecimal.valueOf(100));
                }else {
                    //再计算一级代理的实际R值   R点数/一级实际占比*（公司实际占比-10%）
                    firstFactR = firstConfig.getValueR().divide(firstConfig.getHighRatio(),4,BigDecimal.ROUND_HALF_UP).multiply(comFactRatio).divide(BigDecimal.valueOf(100));
                }
                //一级码粮为
                bigDecimals[1] = decimal.multiply(firstFactR).setScale(0,BigDecimal.ROUND_HALF_UP);

            }

        } else {

            BigDecimal firstFactRatio;
            if("user".equals(play.getRoles().get(0).getName())){
                //如果是玩家
                firstFactRatio = representation.getFirstFact() == null ? BigDecimal.valueOf(0) : representation.getFirstFact();
                //再计算一级代理的实际R值
            }else {
                //如果是二级代理
                AgentConfig agentConfig = ServiceUtil.serviceUtil.getAgentConfigAppService().getByAccount(play);

                if(agentConfig != null){
                    firstFactRatio = agentConfig.getFactRatio() == null ? BigDecimal.valueOf(0) : agentConfig.getFactRatio();
                }else {
                    firstFactRatio = BigDecimal.valueOf(0);
                }
            }

            bigDecimals[3] = firstFactRatio;
            BigDecimal comFactRatio = BigDecimal.valueOf(100).subtract(firstFactRatio).subtract(BigDecimal.valueOf(10));
            if(firstConfig.getHighRatio().compareTo(BigDecimal.valueOf(0)) == 0  ){
                bigDecimals[1] = decimal.multiply(firstConfig.getValueR().divide(BigDecimal.valueOf(100))).setScale(0,BigDecimal.ROUND_HALF_UP);
            }else {
                BigDecimal firstFactR = firstConfig.getValueR().divide(firstConfig.getHighRatio(),4,BigDecimal.ROUND_HALF_UP).multiply(comFactRatio).divide(BigDecimal.valueOf(100));
                bigDecimals[1] = decimal.multiply(firstFactR).setScale(0,BigDecimal.ROUND_HALF_UP);
            }

        }
        return bigDecimals;

    }

    /**
     * 手续费计算
     *
     * @param rechargeFee 充值费率
     * @param upFee 上分费率
     * @param rechargeScore 充值金额
     * @param addScore 上分金额
     * @param secondConfig 二级代理配置
     * @param sub 二级
     * @param sup 一级
     * @param play 玩家
     * @param agentConfigAppService 查询接口对象
     * @return 手续费
     */
    private BigDecimal[] free(BigDecimal rechargeFee,BigDecimal upFee,BigDecimal rechargeScore,BigDecimal addScore, AgentConfig secondConfig,
                              Account sub, Account sup, Account play, IAgentConfigAppService agentConfigAppService,BigDecimal secondFact,BigDecimal firstFact){

        BigDecimal[] bigDecimals = new BigDecimal[]{BigDecimal.valueOf(0),BigDecimal.valueOf(0)};

        if(sub != null){
            //先计算二级的付

            BigDecimal secondFactRatio = secondFact;
            //下级手续费
            bigDecimals[0] = rechargeScore.multiply(rechargeFee).multiply(secondFactRatio.divide(BigDecimal.valueOf(100)))
                    .add(addScore.multiply(upFee).multiply(secondFactRatio.divide(BigDecimal.valueOf(100)))).setScale(0,BigDecimal.ROUND_HALF_UP);

            if(sup != null){
                //计算一级手续费
                BigDecimal firstFactRatio = firstFact;

                //手续费
                bigDecimals[1] = rechargeScore.multiply(rechargeFee).multiply(firstFactRatio.divide(BigDecimal.valueOf(100)))
                        .add(addScore.multiply(upFee).multiply(firstFactRatio.divide(BigDecimal.valueOf(100)))).setScale(0,BigDecimal.ROUND_HALF_UP);
            }

        }else {
            //二级代理为空，直接计算一级代理
            if(sup != null){
                BigDecimal firstFactRatio;
                //玩家R值
                if("user".equals(play.getRoles().get(0).getName())){
                    firstFactRatio = firstFact;
                } else {
                    AgentConfig agentConfig = agentConfigAppService.getByAccount(play);
                    if(agentConfig != null){
                        firstFactRatio = agentConfig.getFactRatio() != null ? agentConfig.getFactRatio() : BigDecimal.valueOf(0);
                    }else {
                        firstFactRatio = BigDecimal.valueOf(0);
                    }
                }
                //手续费
                bigDecimals[1] = rechargeScore.multiply(rechargeFee).multiply(firstFactRatio.divide(BigDecimal.valueOf(100)))
                        .add(addScore.multiply(upFee).multiply(firstFactRatio.divide(BigDecimal.valueOf(100)))).setScale(0,BigDecimal.ROUND_HALF_UP);
            }
        }
        return bigDecimals;
    }

    /**
     * 付下级计算
     *
     * @param firstConfig 一级代理配置
     * @param secondConfig 二级代理配置
     * @param decimal 转码数
     * @param sub 二级代理
     * @param sup 一级代理
     * @return 付下级
     */
    private BigDecimal[] paySub(BigDecimal rechargeFee,BigDecimal upFee,BigDecimal rechargeScore,BigDecimal addScore, AgentConfig firstConfig, AgentConfig secondConfig,
                                BigDecimal decimal,Account sub, Account sup, BigDecimal R, Account play, IAgentConfigAppService agentConfigAppService){

        //{下级付，上级付，下级手续费，上级手续费，下级占比，上级占比}
        BigDecimal[] bigDecimals = new BigDecimal[]{BigDecimal.valueOf(0).setScale(2),BigDecimal.valueOf(0.00).setScale(2),
                BigDecimal.valueOf(0.0),BigDecimal.valueOf(0.0),BigDecimal.valueOf(0.0),BigDecimal.valueOf(0.0)};

        if(sub != null){

            //先计算下级的付
            bigDecimals[0] = decimal.multiply(R).multiply(BigDecimal.valueOf(-1)).setScale(0,BigDecimal.ROUND_HALF_UP);
            BigDecimal secondFactRatio = play.getRatio() == null ? BigDecimal.valueOf(0) : play.getRatio();
            //下级手续费
            bigDecimals[2] = rechargeScore.multiply(rechargeFee).multiply(secondFactRatio.divide(BigDecimal.valueOf(100)))
                    .add(addScore.multiply(upFee).multiply(secondFactRatio.divide(BigDecimal.valueOf(100)))).setScale(0,BigDecimal.ROUND_HALF_UP);

            bigDecimals[4] = secondFactRatio.divide(BigDecimal.valueOf(100));

            if(sup != null){
                BigDecimal firstFactRatio = secondConfig.getFactRatio() == null ? BigDecimal.valueOf(0) : secondConfig.getFactRatio();

                //再计算上级付
                if(secondConfig.getHighRatio().compareTo(BigDecimal.valueOf(0)) == 0 || secondFactRatio.compareTo(BigDecimal.valueOf(0)) == 0){
                    //没有占比,则直接付码粮
                    bigDecimals[1] = decimal.multiply(secondConfig.getValueR().divide(BigDecimal.valueOf(100))).multiply(BigDecimal.valueOf(-1)).setScale(0,RoundingMode.HALF_UP);
                }else {
                    BigDecimal factR = secondConfig.getValueR().multiply(secondConfig.getHighRatio().subtract(secondFactRatio)).divide(secondConfig.getHighRatio(),4,BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100));
                    bigDecimals[1] = decimal.multiply(factR).multiply(BigDecimal.valueOf(-1)).setScale(0,RoundingMode.HALF_UP);
                }
                //手续费
                bigDecimals[3] = rechargeScore.multiply(rechargeFee).multiply(firstFactRatio.divide(BigDecimal.valueOf(100)))
                        .add(addScore.multiply(upFee).multiply(firstFactRatio.divide(BigDecimal.valueOf(100)))).setScale(0,BigDecimal.ROUND_HALF_UP);

                bigDecimals[5] = firstFactRatio.divide(BigDecimal.valueOf(100));

            }
        }else {
            //二级代理为空，直接计算一级代理
            if(sup != null){
                BigDecimal playR;
                //玩家R值
                if("user".equals(play.getRoles().get(0).getName())){
                    playR = R;
                } else {
                    AgentConfig parentSup = agentConfigAppService.getByAccount(play);
                    if(parentSup != null && parentSup.getValueR() != null){
                        playR = parentSup.getValueR().divide(BigDecimal.valueOf(100));
                    }else {
                        playR = R;
                    }
                }
                BigDecimal firstFactRatio = play.getRatio() == null ? BigDecimal.valueOf(0) : play.getRatio();

                //计算付
                bigDecimals[1] = decimal.multiply(playR).multiply(BigDecimal.valueOf(-1)).setScale(0,BigDecimal.ROUND_HALF_UP);
                //手续费
                bigDecimals[3] = rechargeScore.multiply(rechargeFee).multiply(firstFactRatio.divide(BigDecimal.valueOf(100)))
                        .add(addScore.multiply(upFee).multiply(firstFactRatio.divide(BigDecimal.valueOf(100)))).setScale(0,BigDecimal.ROUND_HALF_UP);

                bigDecimals[5] = firstFactRatio.divide(BigDecimal.valueOf(100));
            }
        }
        return bigDecimals;
    }

}
