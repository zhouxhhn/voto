package bjl.domain.service.financialSummary;



import bjl.application.financialSummary.command.ListFinancialSummaryCommand;
import bjl.application.gamedetailed.command.CreateGameDetailedCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.core.util.CoreDateUtils;
import bjl.core.util.CoreStringUtils;

import bjl.domain.model.financialSummary.FinancialSummary;
import bjl.domain.model.financialSummary.IFinancialSummaryRepository;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.gamedetailed.IGameDetailedRepository;
import bjl.domain.service.userManager.IUserManagerService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dyp on 2017-12-20.
 */
@Service("financialSummaryService")
public class FinancialSummaryService  implements IFinancialSummaryService{
    @Autowired
    private IGameDetailedRepository<GameDetailed,String> gameDetailedRepository;
    @Autowired
    private IUserManagerService userManagerService;

    @Autowired
    private IFinancialSummaryRepository<FinancialSummary,String> financialSummaryRepository;




    @Override
    public Pagination<FinancialSummary> pagination(ListFinancialSummaryCommand command) {
//        List<Order> orderList = new ArrayList<>();
//        orderList.add(Order.desc("createDate"));

        return financialSummaryRepository.pagination(command.getPage(),command.getPageSize(),criteria(command),null);
    }


    public TotalGameDetailedCommand total(ListFinancialSummaryCommand command) {
        return gameDetailedRepository.total(criteria(command),null);
    }


    @Override
    public void create(List<CreateGameDetailedCommand> createGameDetailedCommands) {



//
//        // 庄、 闲占成
//        financialSummary.setBankerProportion(gameDetailed.getUser().getBankerPlayerProportion());
//        financialSummary.setPlayerProportion(gameDetailed.getUser().getBankerPlayerProportion());
//
//
//        //庄-闲>0  庄台面分： 庄-闲      ：闲台面分 0
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) > 0) {
//            financialSummary.setBankerMesaScore(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()));
//            financialSummary.setPlayerMesaScore(new BigDecimal(0));
//        }
//        //庄-闲<0  庄台面分：0      ：闲台面分 闲-庄
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) <= 0) {
//            financialSummary.setBankerMesaScore(new BigDecimal(0));
//            financialSummary.setPlayerMesaScore(gameDetailed.getPlayer().subtract(gameDetailed.getBanker()));
//        }
//
//
//        //开奖结果
//        financialSummary.setResult((gameDetailed.getLottery()[0] == 1 ? "庄 " : "") + (gameDetailed.getLottery()[1] == 1 ? "闲 " : "")
//                + (gameDetailed.getLottery()[2] == 1 ? "庄对 " : "") + (gameDetailed.getLottery()[3] == 1 ? "闲对 " : "") + (gameDetailed.getLottery()[4] == 1 ? "和" : ""));
//
//
//        //1.台面分盈亏
//        // 庄-闲>0  庄台面  开奖为庄  庄-闲+庄        开奖非庄     庄-闲-庄
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) > 0) {
//            if ("庄".equals(gameDetailed.getLottery()[0] == 1 ? "庄 " : "")) {
//                financialSummary.setMesaWinLoss(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()).add(gameDetailed.getBanker()));
//            } else {
//                financialSummary.setMesaWinLoss(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()).subtract(gameDetailed.getPlayer()));
//            }
//        }
//        //2.台面分盈亏
//        // 庄-闲<0  闲台面 开奖为闲  闲-庄+闲        开奖为闲      闲-庄-庄
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) <= 0) {
//            if ("闲".equals(gameDetailed.getLottery()[0] == 1 ? "闲 " : "")) {
//                financialSummary.setMesaWinLoss(gameDetailed.getPlayer().subtract(gameDetailed.getBanker()).add(gameDetailed.getPlayer()));
//            } else {
//                financialSummary.setMesaWinLoss(gameDetailed.getPlayer().subtract(gameDetailed.getBanker()).subtract(gameDetailed.getBanker()));
//            }
//        }
//
//
//        //  台面洗码
//        //1. 庄-闲>0  庄台面    庄-闲--闲-庄对-闲对-和
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) > 0) {
//            if (!"庄".equals(gameDetailed.getLottery()[0] == 1 ? "庄 " : "")) {
//                financialSummary.setMesaWashCode(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()).subtract(gameDetailed.getPlayer()).subtract(gameDetailed.getBankPair())
//                        .subtract(gameDetailed.getPlayerPair()).subtract(gameDetailed.getDraw()));
//            }
//        }
//        //台面洗码
//        // 2.庄-闲<0  闲台面    闲-庄-庄-庄对-闲对-和
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) >= 0) {
//            if (!"闲".equals(gameDetailed.getLottery()[0] == 1 ? "闲 " : "")) {
//                financialSummary.setMesaWashCode(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()).subtract(gameDetailed.getBanker()).subtract(gameDetailed.getBankPair())
//                        .subtract(gameDetailed.getPlayerPair()).subtract(gameDetailed.getDraw()));
//            }
//        }
//
//        // 零数利润
//        //  1. 庄-闲>0  庄台面
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) >= 0) {
//            //取余
//            BigDecimal[] ys =gameDetailed.getBanker().subtract(gameDetailed.getPlayer()).divideAndRemainder(BigDecimal.valueOf(100));
//            //除不能整除100  庄*0.95  (庄-闲)*0.95
//            if(ys[1].compareTo(BigDecimal.valueOf(0))>=0) {
//                //庄台面 对冲之后/100的余数
//                if ("庄".equals(gameDetailed.getLottery()[0] == 1 ? "庄 " : "")) {
//                    financialSummary.setZeroProfit(ys[1].multiply(BigDecimal.valueOf(0.95)));
//                }
//                if ("闲".equals(gameDetailed.getLottery()[0] == 1 ? "闲 " : "")) {
//                    //-(对冲之后取余的数)
//                    financialSummary.setZeroProfit(BigDecimal.valueOf(0).subtract(ys[1]));
//                }
//            }else{
//                financialSummary.setZeroProfit(BigDecimal.valueOf(0));
//            }
//        }
//        //  零数利润
//        //  2. 庄-闲>0  闲台面
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) < 0) {
//            //庄闲对冲取余
//            BigDecimal[] dc=(gameDetailed.getBanker().subtract(gameDetailed.getPlayer())).divideAndRemainder(BigDecimal.valueOf(100));
//            if(dc[1].compareTo(BigDecimal.valueOf(0))>=0){
//                if ("庄".equals(gameDetailed.getLottery()[0] == 1 ? "庄 " : "")) {
//                    financialSummary.setZeroProfit(BigDecimal.valueOf(0).subtract(dc[1]));
//                }
//                if("闲".equals(gameDetailed.getLottery()[0] == 1 ? "闲 " : "")){
//                    //对冲之后/100的余数
//                    financialSummary.setZeroProfit(dc[1]);
//                }
//            }else{
//                financialSummary.setZeroProfit(BigDecimal.valueOf(0));
//            }
//        }
//
//
//        //1.对冲利润
//        //台面分为庄
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) >= 0) {
//            financialSummary.setHedgeProfit(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()));
//
//        }
//        //2.对冲利润
//        //台面分为闲
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) > 0) {
//            financialSummary.setHedgeProfit(gameDetailed.getPlayer().subtract(gameDetailed.getBanker()));
//        }
//
//
//        //占成利润
//
//        financialSummary.setProportionProfit(gameDetailed.getPlayer().subtract(gameDetailed.getBanker()).multiply(gameDetailed.getUser().getBankerPlayerProportion()));
//
//
//        //利润汇总
//
//        financialSummary.setProfitSummary(gameDetailed.getPlayer().subtract(gameDetailed.getBanker()));


        FinancialSummary financialSummary = count(createGameDetailedCommands);
        if(financialSummary != null){
            financialSummaryRepository.save(financialSummary);
        }

    }

    @Override
    public void update(List<CreateGameDetailedCommand> createGameDetailedCommands) {

        FinancialSummary financialSummary = count(createGameDetailedCommands);
        if(financialSummary != null){

            List<Criterion> criterionList = new ArrayList<>();
            //房间类型
            criterionList.add(Restrictions.eq("hallType",createGameDetailedCommands.get(0).getHallType()));
            //鞋数
            criterionList.add(Restrictions.eq("boot",createGameDetailedCommands.get(0).getBoots()));
            //局数
            criterionList.add(Restrictions.eq("bureau",createGameDetailedCommands.get(0).getGames()));
            //时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date date = calendar.getTime(); //当天零时零分了零秒的时间

            calendar.add(Calendar.DATE, 1); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
            Date after = calendar.getTime(); //后一天零时零分了零秒的时间
            criterionList.add(Restrictions.ge("createDate",date));
            criterionList.add(Restrictions.lt("createDate",after));

            List<FinancialSummary> list = financialSummaryRepository.list(criterionList,null);
            if(list.size() > 0){
                financialSummaryRepository.delete(list.get(0));
                financialSummaryRepository.save(financialSummary);
            }
        }

    }

    private FinancialSummary count(List<CreateGameDetailedCommand> createGameDetailedCommands) {

        if(createGameDetailedCommands.size()<1){
            return null;
        }

        BigDecimal bankScore = BigDecimal.valueOf(0);  //庄投注分
        BigDecimal playScore = BigDecimal.valueOf(0);   //闲投注分
        BigDecimal bankPro = BigDecimal.valueOf(0);    //庄个人占成
        BigDecimal playPro = BigDecimal.valueOf(0);    //闲个人占成

        FinancialSummary financialSummary = new FinancialSummary();

        int sum = 0;

        for(CreateGameDetailedCommand command : createGameDetailedCommands){

            if(command.getVirtual() != 1){
                bankScore = bankScore.add(command.getBanker());
                playScore = playScore.add(command.getPlayer());
                bankPro = bankPro.add(command.getBankMesa());
                playPro = playPro.add(command.getPlayMesa());
                sum++;

            }
        }

        if(sum<1){
            return null;
        }

        BigDecimal bankMesa; //庄台面分
        BigDecimal playMesa; //闲台面分

        if(bankScore.compareTo(playScore) >=0){
            bankMesa = bankScore.subtract(bankPro).subtract(playScore.subtract(playPro));
            playMesa = BigDecimal.valueOf(0);
            if(createGameDetailedCommands.get(0).getLottery()[0] == 1){
                //庄闲盈利
                financialSummary.setMesaWinLoss(bankMesa.multiply(BigDecimal.valueOf(0.95)).setScale(0, BigDecimal.ROUND_HALF_UP));
                //庄闲洗码
                financialSummary.setMesaWashCode(BigDecimal.valueOf(0));
                //零数利润
                financialSummary.setZeroProfit(zero(bankMesa).multiply(BigDecimal.valueOf(0.95)).setScale(0, BigDecimal.ROUND_HALF_UP));
                //对冲利润
                financialSummary.setHedgeProfit((bankScore.subtract(bankPro).subtract(bankMesa)).multiply(BigDecimal.valueOf(0.95)).setScale(0, BigDecimal.ROUND_HALF_UP));
                //占成利润
                financialSummary.setProportionProfit(bankPro.multiply(BigDecimal.valueOf(0.95)).setScale(0, BigDecimal.ROUND_HALF_UP));


            }else if (createGameDetailedCommands.get(0).getLottery()[1] == 1){
                financialSummary.setMesaWinLoss(bankMesa.multiply(BigDecimal.valueOf(-1)).setScale(0, BigDecimal.ROUND_HALF_UP));
                financialSummary.setMesaWashCode(bankMesa.setScale(0, BigDecimal.ROUND_HALF_UP));
                financialSummary.setZeroProfit(zero(bankMesa).multiply(BigDecimal.valueOf(-1)).setScale(0, BigDecimal.ROUND_HALF_UP));
                financialSummary.setHedgeProfit(BigDecimal.valueOf(0));
                financialSummary.setProportionProfit(bankPro.multiply(BigDecimal.valueOf(-1)).setScale(0, BigDecimal.ROUND_HALF_UP));

            }else {
                financialSummary.setMesaWinLoss(BigDecimal.valueOf(0));
                financialSummary.setMesaWashCode(BigDecimal.valueOf(0));
                financialSummary.setZeroProfit(BigDecimal.valueOf(0));
                financialSummary.setHedgeProfit(BigDecimal.valueOf(0));
                financialSummary.setProportionProfit(BigDecimal.valueOf(0));
            }
        }else {
            bankMesa = BigDecimal.valueOf(0);
            playMesa = playScore.subtract(playPro).subtract(bankScore.subtract(bankPro));
            if(createGameDetailedCommands.get(0).getLottery()[0] == 1){
                //庄闲盈利
                financialSummary.setMesaWinLoss(playMesa.multiply(BigDecimal.valueOf(-1)).setScale(0, BigDecimal.ROUND_HALF_UP));
                //庄闲洗码
                financialSummary.setMesaWashCode(playMesa.setScale(0, BigDecimal.ROUND_HALF_UP));
                //零数利润
                financialSummary.setZeroProfit(zero(playMesa).multiply(BigDecimal.valueOf(-1)).setScale(0, BigDecimal.ROUND_HALF_UP));
                //占成利润
                financialSummary.setProportionProfit(playPro.multiply(BigDecimal.valueOf(-1)).setScale(0, BigDecimal.ROUND_HALF_UP));


            } else if (createGameDetailedCommands.get(0).getLottery()[1] == 1){
                financialSummary.setMesaWinLoss(playMesa);
                financialSummary.setMesaWashCode(BigDecimal.valueOf(0));
                financialSummary.setZeroProfit(zero(playMesa).setScale(0, BigDecimal.ROUND_HALF_UP));
                financialSummary.setProportionProfit(playPro.setScale(0, BigDecimal.ROUND_HALF_UP));

            } else {
                financialSummary.setMesaWinLoss(BigDecimal.valueOf(0));
                financialSummary.setMesaWashCode(BigDecimal.valueOf(0));
                financialSummary.setZeroProfit(BigDecimal.valueOf(0));
                financialSummary.setProportionProfit(BigDecimal.valueOf(0));
            }
            //对冲利润
            financialSummary.setHedgeProfit(BigDecimal.valueOf(0));
        }

        //开奖结果
        financialSummary.setResult((createGameDetailedCommands.get(0).getLottery()[0] == 1 ? "庄 " : "") + (createGameDetailedCommands.get(0).getLottery()[1] == 1 ? "闲 " : "")
                + (createGameDetailedCommands.get(0).getLottery()[2] == 1 ? "庄对 " : "") + (createGameDetailedCommands.get(0).getLottery()[3] == 1 ? "闲对 " : "")
                + (createGameDetailedCommands.get(0).getLottery()[4] == 1 ? "和" : ""));

        //利润汇总
        financialSummary.setProfitSummary(financialSummary.getZeroProfit().add(financialSummary.getHedgeProfit()).add(financialSummary.getProportionProfit()));


        financialSummary.setCreateDate(new Date());
        financialSummary.setBoot(createGameDetailedCommands.get(0).getBoots());
        financialSummary.setBureau(createGameDetailedCommands.get(0).getGames());
        financialSummary.setHallType(createGameDetailedCommands.get(0).getGames());
        financialSummary.setBankerScore(bankScore);
        financialSummary.setPlayerScore(playScore);
        financialSummary.setBankerMesaScore(bankMesa.setScale(0,BigDecimal.ROUND_HALF_UP));
        financialSummary.setPlayerMesaScore(playMesa.setScale(0,BigDecimal.ROUND_HALF_UP));
        financialSummary.setBankerProportion(bankPro.setScale(0,BigDecimal.ROUND_HALF_UP));
        financialSummary.setPlayerProportion(playPro.setScale(0,BigDecimal.ROUND_HALF_UP));

        return financialSummary;

    }

    /**
     * 计算零数
     * @param bigDecimal
     * @return
     */
    private BigDecimal zero(BigDecimal bigDecimal){
        Integer old = bigDecimal.intValue();
        //取余
        Integer a = old%100;
        if(a>0){
            return BigDecimal.valueOf(100-a);
        }
        return BigDecimal.valueOf(0);
    }

    //获取数据库中FinancialSummary对象
    @Override
    public List<FinancialSummary> list(String[] strings) {
        List<Criterion> criterionList=new ArrayList<>();
        criterionList.add(Restrictions.eq("boot",Integer.valueOf(strings[0])));
        criterionList.add(Restrictions.eq("bureau",Integer.valueOf(strings[1])));
        //开始时间
        Date start = CoreDateUtils.parseDate(strings[2],"yy-MM-dd");
        //结束时间
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,1);
        Date end=calendar.getTime();

        criterionList.add(Restrictions.ge("createDate",start));
        criterionList.add(Restrictions.lt("createDate",end));

        List<Order> orderList=new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        return financialSummaryRepository.list(criterionList,orderList);
    }

    @Override

    public List<GameDetailed> apiList(String[] strings) {

        List<Criterion> criterionList = new ArrayList<>();
        //鞋数
        criterionList.add(Restrictions.eq("boots",Integer.valueOf(strings[2])));
        //局数
        criterionList.add(Restrictions.eq("games",Integer.valueOf(strings[3])));
        //时间
        Date date = CoreDateUtils.parseDate(strings[0], "yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());   //设置当前日期
        c.add(Calendar.DATE, 1); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
        Date after = c.getTime(); //结果

        criterionList.add(Restrictions.ge("createDate",date));
        criterionList.add(Restrictions.lt("createDate",after));
        return gameDetailedRepository.list(criterionList,null);
    }


    private List<Criterion> criteria(ListFinancialSummaryCommand command) {

        List<Criterion> criterionList = new ArrayList<>();
        if(command.getBoots() != null){
            criterionList.add(Restrictions.eq("boot",command.getBoots()));//靴
        }
        if(command.getGames() != null){
            criterionList.add(Restrictions.eq("bureau",command.getGames()));//局
        }
        if(!CoreStringUtils.isEmpty(command.getStartDate()) || !CoreStringUtils.isEmpty(command.getEndDate())){
            if ((!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss"))){
                criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy-MM-dd HH:mm:ss")));
            }
            if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")) {
                criterionList.add(Restrictions.lt("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy-MM-dd HH:mm:ss")));
            }
        }
        return criterionList;
    }

    //根据时间段查询
    public List<GameDetailed> findByDate(String userId, String startDate, String endDate) {
        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("userId",userId));
        criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(startDate, "yyyy/MM/dd HH:mm")));
        criterionList.add(Restrictions.le("createDate", CoreDateUtils.parseDate(endDate, "yyyy/MM/dd HH:mm")));
//        List<Order> orderList=new ArrayList<>();
//        orderList.add(Order.desc("createDate"));
        return gameDetailedRepository.list(criterionList,null);
    }
    //根据牌局查询
    public List<GameDetailed> findByBoot(GameDetailed gameDetailed){
        List<Criterion> criterionList = new ArrayList<>();
        if(!CoreStringUtils.isEmpty(gameDetailed.getUser().getId())) {
            criterionList.add(Restrictions.eq("userId",gameDetailed.getUser().getId()));
            criterionList.add(Restrictions.eq("boot", gameDetailed.getBoots()));
            criterionList.add(Restrictions.eq("bureau", gameDetailed.getGames()));
        }
//        List<Order> orderList=new ArrayList<>();
//        orderList.add(Order.desc("createDate"));
        return gameDetailedRepository.list(criterionList,null);
    }


    @Override
    public Object[] sum(ListFinancialSummaryCommand command) {
        return financialSummaryRepository.sum(criteria(command));
    }
}
