package bjl.application.gamedetailed;

import com.alibaba.fastjson.JSONObject;

import bjl.application.agent.command.CountGameDetailedCommand;
import bjl.application.gamedetailed.command.CreateGameDetailedCommand;
import bjl.application.gamedetailed.command.ListGameDetailedCommand;
import bjl.application.gamedetailed.command.TotalGameDetailedCommand;
import bjl.application.gamedetailed.representation.GameDetailedRepresentation;
import bjl.constants.VotoContants;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.user.User;
import bjl.domain.service.bettable.IBetTableService;
import bjl.domain.service.gamedetailed.IGameDetailedService;
import bjl.domain.service.scoretable.IScoreTableService;
import bjl.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhangjin on 2018/1/3.
 */
@Service("gameDetailedAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameDetailedAppService implements IGameDetailedAppService{

    @Autowired
    private IGameDetailedService gameDetailedService;
    @Autowired
    private IMappingService mappingService;
    @Autowired
    private IBetTableService betTableService;
    @Autowired
    private IScoreTableService scoreTableService;

    @Override
    public User save(CreateGameDetailedCommand command) {
        return gameDetailedService.save(command);
    }

    @Override
    public User update(GameDetailed gameDetailed, BigDecimal changeScore) {
        return gameDetailedService.update(gameDetailed,changeScore);
    }

    @Override
    public User update(CreateGameDetailedCommand command, BigDecimal changeScore) {
        return gameDetailedService.update(command,changeScore);
    }

    @Override
    public Map<String, Object> apiList(String[] strings) {

        BigDecimal[] total = new BigDecimal[]{BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0)
                ,BigDecimal.valueOf(0), BigDecimal.valueOf(0)};
        List<Object[]> playList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();

        //人数
        total[0] = BigDecimal.valueOf(0);
        //投注表
        if("1".equals(strings[4])){

            List<Object[]> list = betTableService.get(Integer.valueOf(strings[1]),Integer.valueOf(strings[2]),Integer.valueOf(strings[3]),strings[0]);

            total[0] = BigDecimal.valueOf(list.size());

            for(Object[] objects : list){

                    //添加玩家数据

                    playList = list;
                    //合计
                    for(int i= 1;i<objects.length;i++){
                        total[i] = total[i].add((BigDecimal) objects[i]);
                    }

            }
        }else {
            //分数表
            List<Object[]> list = scoreTableService.get(Integer.valueOf(strings[1]),Integer.valueOf(strings[2]),Integer.valueOf(strings[3]),strings[0]);

            total[0] = BigDecimal.valueOf(list.size());

            for(Object[] objects : list){

                //添加玩家数据

                playList = list;
                //合计
                for(int i= 1;i<objects.length;i++){
                    total[i] = total[i].add((BigDecimal) objects[i]);
                }

            }
        }
        map.put("total",total);
        map.put("list",playList);
        return map;
    }

    @Override
    public Pagination<GameDetailedRepresentation> pagination(ListGameDetailedCommand command,String flag) {

        command.verifyPage();
        command.verifyPageSize(18);
        if(flag != null && VotoContants.EXPORT_EXCEL.equals(flag)){
            command.setPage(1);
            command.setPageSize(10000000);
        }

        Pagination<GameDetailed> pagination = gameDetailedService.pagination(command);
        List<GameDetailedRepresentation> data = mappingService.mapAsList(pagination.getData(),GameDetailedRepresentation.class);

        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    public TotalGameDetailedCommand total(ListGameDetailedCommand command) {
        return gameDetailedService.total(command);
    }

    @Override
    public GameDetailed getMax(Integer roomType) {

        List<GameDetailed> list = gameDetailedService.getMax(roomType);
        if(list.size() == 0){
            return null;
        }else {
            return list.get(0);
        }

    }

    @Override
    public List<CountGameDetailedCommand> list(Date date) {

        return gameDetailedService.list(date);
    }

    @Override
    public List<GameDetailed> getLast(Integer roomType,Integer boots, Integer games) {
        return gameDetailedService.getLast(roomType,boots,games);
    }

    @Override
    public JSONObject list(ListGameDetailedCommand listGameDetailedCommand) {
        return gameDetailedService.list(listGameDetailedCommand);
    }
}
