package bjl.interfaces.gamebet;

import bjl.application.account.representation.AccountRepresentation;
import bjl.application.gamedetailed.IGameDetailedAppService;
import bjl.application.robot.IRobotAppService;
import bjl.core.upload.IFileUploadService;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.robot.Robot;
import bjl.interfaces.shared.web.BaseController;
import bjl.tcp.GlobalVariable;
import bjl.websocket.EventProcess;
import bjl.websocket.command.GameStatus;
import bjl.websocket.command.WSMessage;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjin on 2017/12/21.
 */
@Controller
@RequestMapping("/game_bet")
public class GameBetController extends BaseController{

    @Autowired
    private IFileUploadService fileUploadService;
    @Autowired
    private IGameDetailedAppService gameDetailedAppService;
    @Autowired
    private IRobotAppService robotAppService;

    @RequestMapping(value = "/list")
    public ModelAndView list(Integer roomType,String text){

        AccountRepresentation sessionUser = (AccountRepresentation) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");

        if(sessionUser == null){
            return new ModelAndView("redirect:/login_hf_889");
        }else {
            if(!sessionUser.getRoles().get(0).equals("admin") && !sessionUser.getRoles().get(0).equals("filipino") &&
                    !sessionUser.getRoles().get(0).equals("vietnam") && !sessionUser.getRoles().get(0).equals("macao")){
                return new ModelAndView("redirect:/login_hf_889");
            }
        }


        List<WSMessage> list = new ArrayList<>();
        BigDecimal[] total = new BigDecimal[]{BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0)
                ,BigDecimal.valueOf(0), BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0)};
        //当前鞋局
        GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(roomType);
        if(gameStatus == null){
            GameDetailed gameDetailed = gameDetailedAppService.getMax(roomType);
            if(gameDetailed == null){
                gameStatus = new GameStatus(1,1,-1);
            }else {
                gameStatus = new GameStatus(gameDetailed.getBoots(),gameDetailed.getGames()+1,0);
            }
            GlobalVariable.getGameStatusMap().put(roomType,gameStatus);

        }
        //台面分数
        BigDecimal bankScore = BigDecimal.valueOf(0);
        BigDecimal playerScore = BigDecimal.valueOf(0);
        BigDecimal bankPirScore = BigDecimal.valueOf(0);
        BigDecimal playPirScore = BigDecimal.valueOf(0);
        BigDecimal drawScore = BigDecimal.valueOf(0);
        //遍历玩家下注情况
        if (GlobalVariable.getBetMap().get(roomType) != null) {
            for (WSMessage wsMessage : GlobalVariable.getBetMap().get(roomType).values()) {
                //人数+1
                total[0] = total[0].add(BigDecimal.valueOf(1));
                if(wsMessage.getVirtual() == 2){
                    //统计各项之和
                    for (int i = 1; i < 9; i++) {
                        if (i == 1) {
                            total[i] = total[i].add(wsMessage.getScore()[i - 1]);
                            playerScore = playerScore.add(wsMessage.getScore()[i - 1].multiply(BigDecimal.valueOf(1).subtract(wsMessage.getRatio())));
                        } else if (i == 2) {
                            total[i] = total[i].add(wsMessage.getScore()[i - 1]);
                            bankScore = bankScore.add(wsMessage.getScore()[i - 1].multiply(BigDecimal.valueOf(1).subtract(wsMessage.getRatio())));
                        } else if (i == 3) {
                            total[i] = total[i].add(wsMessage.getScore()[i - 1]);
                            playPirScore = playPirScore.add(wsMessage.getScore()[i - 1].multiply(BigDecimal.valueOf(1).subtract(wsMessage.gettRatio())));
                        }else if (i == 4) {
                            total[i] = total[i].add(wsMessage.getScore()[i - 1]);
                            bankPirScore = bankPirScore.add(wsMessage.getScore()[i - 1].multiply(BigDecimal.valueOf(1).subtract(wsMessage.gettRatio())));
                        }else if (i == 5) {
                            total[i] = total[i].add(wsMessage.getScore()[i - 1]);
                            drawScore = drawScore.add(wsMessage.getScore()[i - 1].multiply(BigDecimal.valueOf(1).subtract(wsMessage.gettRatio())));
                        }else {
                            total[i] = total[i].add(wsMessage.getScore()[i - 1]);
                        }
                    }
                } else {
                    //统计各项之和
                    for (int i = 1; i < 9; i++) {
                        total[i] = total[i].add(wsMessage.getScore()[i - 1]);
                    }
                }

                list.add(wsMessage);
            }
        }

        if (playerScore.compareTo(bankScore) >= 0) {
            playerScore = playerScore.subtract(bankScore);
            bankScore = BigDecimal.valueOf(0);
            if (playerScore.compareTo(BigDecimal.valueOf(1000)) < 0) {
                playerScore = BigDecimal.valueOf(0);
            }
        } else {
            bankScore = bankScore.subtract(playerScore);
            playerScore = BigDecimal.valueOf(0);
            if (bankScore.compareTo(BigDecimal.valueOf(1000)) < 0) {
                bankScore = BigDecimal.valueOf(0);
            }
        }

        for(int i=0;i<total.length;i++){
            total[i] = total[i].setScale(0,BigDecimal.ROUND_HALF_UP);
        }

        bankScore = bankScore.setScale(0,BigDecimal.ROUND_HALF_UP);
        playerScore = playerScore.setScale(0,BigDecimal.ROUND_HALF_UP);
        bankPirScore = bankPirScore.setScale(0,BigDecimal.ROUND_HALF_UP);
        playPirScore = playPirScore.setScale(0,BigDecimal.ROUND_HALF_UP);
        drawScore = drawScore.setScale(0,BigDecimal.ROUND_HALF_UP);

        if (roomType == 1) {
            return new ModelAndView("hall/filipino", "bets", list).addObject("total", total)
                    .addObject("playerScore", playerScore).addObject("bankScore", bankScore)
                    .addObject("bankPirScore", bankPirScore).addObject("playPirScore", playPirScore)
                    .addObject("drawScore", drawScore)
                    .addObject("gameStatus", gameStatus).addObject("text", text);
        } else if (roomType == 2) {
            return new ModelAndView("hall/vietnam", "bets", list).addObject("total", total)
                    .addObject("playerScore", playerScore).addObject("bankScore", bankScore)
                    .addObject("bankPirScore", bankPirScore).addObject("playPirScore", playPirScore)
                    .addObject("drawScore", drawScore)
                    .addObject("gameStatus", gameStatus).addObject("text", text);
        } else {
            return new ModelAndView("hall/macao", "bets", list).addObject("total", total)
                    .addObject("playerScore", playerScore).addObject("bankScore", bankScore)
                    .addObject("bankPirScore", bankPirScore).addObject("playPirScore", playPirScore)
                    .addObject("drawScore", drawScore)
                    .addObject("gameStatus", gameStatus).addObject("text", text);
        }

    }

//    @RequestMapping(value = "/lottery")
//    public ModelAndView lottery(@RequestParam MultipartFile lotteryImg, MultipartFile roadImg,MultipartFile sceneImg, String lotteryVal){
//
//
//
//        if(CoreStringUtils.isEmpty(lotteryVal)){
//            return new ModelAndView("redirect:/game_bet/list");
//        }
//        String[] strings = lotteryVal.split(",");
//        if(lotteryImg == null || roadImg == null || sceneImg==null ){
//            return new ModelAndView("redirect:/game_bet/list");
//        }
//        GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[0]));
//        if(gameStatus == null || gameStatus.getStatus() == 0){
//            return new ModelAndView("redirect:/game_bet/list","roomType",strings[0])
//                    .addObject("text","游戏未开始");
//        } else if (gameStatus.getStatus() == 3){
//            return new ModelAndView("redirect:/game_bet/list","roomType",strings[0])
//                    .addObject("text","已经开过奖");
//        }
//
//        JSONObject jsonObject = new EventProcess().eventMessage(lotteryVal);
//        System.out.println(jsonObject);
//        fileUploadService.uploadLottery(lotteryImg,roadImg,sceneImg,strings);
//        return new ModelAndView("redirect:/game_bet/list","roomType",strings[0])
//                .addObject("text","开奖成功");
//    }

    @RequestMapping(value = "/lotteryUpload")
    @ResponseBody
    public String lottery(@RequestParam MultipartFile fileLotty,MultipartFile fileRoad,MultipartFile fileScene, String lotteryVal, Integer type){

        JSONObject jsonObject = new JSONObject();

        if(CoreStringUtils.isEmpty(lotteryVal)){
            jsonObject.put("success",1);
            return jsonObject.toJSONString();
        }
        String[] strings = lotteryVal.split(",");
        if(type == 2){
            StringBuilder sb = new StringBuilder(lotteryVal);
            lotteryVal = sb.replace(2, 3, "11").toString();
            strings[1] = "11";
        }
        int count = 3;
        if(fileLotty == null){
            count--;
        }
        if(fileRoad == null){
            count--;
        }
        if(fileScene == null){
            count--;
        }
        if( count< 2){
            jsonObject.put("success",2);
            return jsonObject.toJSONString();
        }

        GameStatus gameStatus = GlobalVariable.getGameStatusMap().get(Integer.valueOf(strings[0]));
        if(type == 1){
            if(gameStatus == null || gameStatus.getStatus() == 0){
                jsonObject.put("success",3);
                return jsonObject.toJSONString();
            } else if (gameStatus.getStatus() == 1){
                jsonObject.put("success",4);
                return jsonObject.toJSONString();
            }else if (gameStatus.getStatus() == 3 || gameStatus.getStatus() == 4){
                jsonObject.put("success",5);
                return jsonObject.toJSONString();
            }
        }else {
            if(gameStatus == null || (gameStatus.getStatus() != 3 && gameStatus.getStatus() != 4)){
                jsonObject.put("success",6);
                return jsonObject.toJSONString();
            }
        }

        //开奖事件
        jsonObject = new EventProcess().eventMessage(lotteryVal);
        System.out.println(jsonObject);
        fileUploadService.uploadLottery(fileLotty,fileRoad,fileScene,strings);
        return jsonObject.toJSONString();
    }
}
