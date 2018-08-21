package bjl.application.financialSummary.representation.mapper;

import bjl.application.financialSummary.representation.FinancialSummaryRepresentation;
import bjl.domain.model.gamedetailed.GameDetailed;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class FinancialSummaryRepresentationMapper extends CustomMapper<GameDetailed,FinancialSummaryRepresentation>{


    public void mapAtoB(GameDetailed gameDetailed, FinancialSummaryRepresentation representation, MappingContext context) {

        representation.setBoot(gameDetailed.getBoots());
        representation.setBureau(gameDetailed.getGames());
        representation.setBankerScore(gameDetailed.getBanker());
        representation.setPlayerScore(gameDetailed.getPlayer());

        // 庄、 闲占成
        representation.setBankerProportion(gameDetailed.getUser().getBankerPlayerProportion());
        representation.setPlayerProportion(gameDetailed.getUser().getBankerPlayerProportion());




        //庄-闲>0  庄台面分： 庄-闲      ：闲台面分 0
        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) > 0) {
            representation.setBankerMesaScore(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()));
            representation.setPlayerMesaScore(new BigDecimal(0));
        }
        //庄-闲<0  庄台面分：0      ：闲台面分 闲-庄
        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) <= 0) {
            representation.setBankerMesaScore(new BigDecimal(0));
            representation.setPlayerMesaScore(gameDetailed.getPlayer().subtract(gameDetailed.getBanker()));
        }


        //开奖结果
        representation.setResult((gameDetailed.getLottery()[0] == 1 ? "庄 " : "") + (gameDetailed.getLottery()[1] == 1 ? "闲 " : "")
                + (gameDetailed.getLottery()[2] == 1 ? "庄对 " : "") + (gameDetailed.getLottery()[3] == 1 ? "闲对 " : "") + (gameDetailed.getLottery()[4] == 1 ? "和" : ""));




        //1.台面分盈亏
        // 庄-闲>0  庄台面  开奖为庄  庄-闲+庄        开奖非庄     庄-闲-庄
        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) > 0) {
            if ("庄".equals(gameDetailed.getLottery()[0] == 1 ? "庄 " : "")) {
                representation.setMesaWinLoss(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()).add(gameDetailed.getBanker()));
            } else {
                representation.setMesaWinLoss(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()).subtract(gameDetailed.getPlayer()));
            }
        }
        //2.台面分盈亏
        // 庄-闲<0  闲台面 开奖为闲  闲-庄+闲        开奖为闲      闲-庄-庄
        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) <= 0) {
            if ("闲".equals(gameDetailed.getLottery()[0] == 1 ? "闲 " : "")) {
                representation.setMesaWinLoss(gameDetailed.getPlayer().subtract(gameDetailed.getBanker()).add(gameDetailed.getPlayer()));
            } else {
                representation.setMesaWinLoss(gameDetailed.getPlayer().subtract(gameDetailed.getBanker()).subtract(gameDetailed.getBanker()));
            }
        }




        //  台面洗码
        //1. 庄-闲>0  庄台面    庄-闲--闲-庄对-闲对-和
        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) > 0) {
            if (!"庄".equals(gameDetailed.getLottery()[0] == 1 ? "庄 " : "")) {
                representation.setMesaWashCode(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()).subtract(gameDetailed.getPlayer()).subtract(gameDetailed.getBankPair())
                        .subtract(gameDetailed.getPlayerPair()).subtract(gameDetailed.getDraw()));
            }
        }
        //台面洗码
        // 2.庄-闲<0  闲台面    闲-庄-庄-庄对-闲对-和
        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) >= 0) {
            if (!"闲".equals(gameDetailed.getLottery()[0] == 1 ? "闲 " : "")) {
                representation.setMesaWashCode(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()).subtract(gameDetailed.getBanker()).subtract(gameDetailed.getBankPair())
                        .subtract(gameDetailed.getPlayerPair()).subtract(gameDetailed.getDraw()));
            }
        }



        representation.setZeroProfit(BigDecimal.valueOf(20));
//        // 零数利润
//        //  1. 庄-闲>0  庄台面
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) >= 0) {
//            //取余
//            BigDecimal[] ys =gameDetailed.getBanker().subtract(gameDetailed.getPlayer()).divideAndRemainder(BigDecimal.valueOf(100));
//           //除不能整除100  庄*0.95  (庄-闲)*0.95
//            if(ys[1].compareTo(BigDecimal.valueOf(0))>=0) {
//                //庄台面 对冲之后/100的余数
//                if ("庄".equals(gameDetailed.getLottery()[0] == 1 ? "庄 " : "")) {
//                   representation.setZeroProfit(ys[1].multiply(BigDecimal.valueOf(0.95)));
//                }
//                if ("闲".equals(gameDetailed.getLottery()[0] == 1 ? "闲 " : "")) {
//                  //-(对冲之后取余的数)
//                    representation.setZeroProfit(BigDecimal.valueOf(0).subtract(ys[1]));
//                }
//            }else{
//                representation.setZeroProfit(BigDecimal.valueOf(0));
//            }
//        }
//        //  零数利润
//        //  2. 庄-闲>0  闲台面
//        if (gameDetailed.getBanker().compareTo(gameDetailed.getPlayer()) < 0) {
//            //庄闲对冲取余
//            BigDecimal[] dc=(gameDetailed.getBanker().subtract(gameDetailed.getPlayer())).divideAndRemainder(BigDecimal.valueOf(100));
//            if(dc[1].compareTo(BigDecimal.valueOf(0))>=0){
//                if ("庄".equals(gameDetailed.getLottery()[0] == 1 ? "庄 " : "")) {
//                    representation.setZeroProfit(BigDecimal.valueOf(0).subtract(dc[1]));
//                }
//                if("闲".equals(gameDetailed.getLottery()[0] == 1 ? "闲 " : "")){
//                //对冲之后/100的余数
//                    representation.setZeroProfit(dc[1]);
//                }
//            }else{
//                representation.setZeroProfit(BigDecimal.valueOf(0));
//            }
//        }

        //1.对冲利润
        //台面分为庄
        if(gameDetailed.getBanker().compareTo(gameDetailed.getPlayer())>=0){
            //  庄台面 开庄  对冲利润 庄-闲

            if("庄".equals(gameDetailed.getLottery()[0] == 1 ? "庄 " : "")){
                representation.setHedgeProfit(gameDetailed.getBanker().subtract(gameDetailed.getPlayer()));
            }else {
                representation.setHedgeProfit(BigDecimal.valueOf(0));
            }
        }
        //2.对冲利润
        //台面分为闲
        if(gameDetailed.getBanker().compareTo(gameDetailed.getPlayer())>0){
            //对冲为闲 开闲 对冲利润 闲-庄
            if("闲".equals(gameDetailed.getLottery()[0] == 1 ? "闲 " : "")){
                representation.setHedgeProfit(gameDetailed.getPlayer().subtract(gameDetailed.getBanker()));
            }else {
                representation.setHedgeProfit(BigDecimal.valueOf(0));
            }
        }




        //占成利润

        if(gameDetailed.getBanker().compareTo(gameDetailed.getPlayer())>=0){
            if("庄".equals(gameDetailed.getLottery()[0] == 1 ? "庄 " : "")){
//                庄*庄占成*0.95-闲*闲占成*1
                representation.setProportionProfit(gameDetailed.getBanker().multiply(new BigDecimal(0.95)).multiply(gameDetailed.getUser().getBankerPlayerProportion())
                        .subtract(gameDetailed.getPlayer().multiply(gameDetailed.getUser().getBankerPlayerProportion())));
            }else if("闲".equals(gameDetailed.getLottery()[0] == 1 ? "闲 " : "")){
//                闲*闲占成*1-庄*庄占成*0.95
                representation.setProportionProfit(gameDetailed.getPlayer().multiply(gameDetailed.getUser().getBankerPlayerProportion())
                        .subtract(gameDetailed.getBanker().multiply(new BigDecimal(0.95)).multiply(gameDetailed.getUser().getBankerPlayerProportion())));
            }else{
//             0 - (闲*闲占成*1-庄*庄占成*0.95)
                representation.setProportionProfit(BigDecimal.valueOf(0).subtract(gameDetailed.getPlayer().multiply(gameDetailed.getUser().getBankerPlayerProportion()))
                        .subtract(gameDetailed.getBanker().multiply(new BigDecimal(0.95)).multiply(gameDetailed.getUser().getBankerPlayerProportion())));
            }
        }

        //利润汇总

        representation.setProfitSummary(representation.getZeroProfit().add(representation.getHedgeProfit()).add(representation.getProportionProfit()));
    }
}
