package bjl.domain.service.scoretable;

import bjl.core.util.CoreDateUtils;
import bjl.domain.model.scoretable.IScoreTableRepository;
import bjl.domain.model.scoretable.ScoreTable;
import bjl.websocket.command.WSMessage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhangjin on 2018/4/28
 */
@Service("scoreTableService")
public class ScoreTableService implements IScoreTableService{


    @Autowired
    private IScoreTableRepository<ScoreTable,String> scoreTableRepository;

    /**
     * 创建投注表数据
     * @param hallType 大厅
     * @param boots 靴
     * @param games 鞋
     * @param map 数据
     */
    @Override
    public void create(Integer hallType, Integer boots, Integer games, Map<String, WSMessage> map) {

        if(map == null || map.size() < 1){
            return;
        }
        ScoreTable betTable = new ScoreTable();
        betTable.setBoots(boots);
        betTable.setGames(games);
        betTable.setHallType(hallType);
        betTable.setCreateDate(new Date());
        List<Object[]> list = new ArrayList<>();
        for(WSMessage wsMessage : map.values()){
            Object[] objects = new Object[4];
            objects[0] = wsMessage.getName();
            objects[1] = wsMessage.getScore()[5];
            objects[2] = wsMessage.getScore()[6];
            objects[3] = wsMessage.getScore()[7];
            list.add(objects);
        }
        betTable.setBetInfo(list);
        scoreTableRepository.save(betTable);

    }

    /**
     * 获取表格数据
     * @param hallType 大厅
     * @param boots 靴
     * @param games 鞋
     * @return
     */
    @Override
    public List<Object[]> get(Integer hallType, Integer boots, Integer games, String dateStr) {

        List<Criterion> criterionList = new ArrayList<>();
        criterionList.add(Restrictions.eq("hallType",hallType));
        criterionList.add(Restrictions.eq("boots",boots));
        criterionList.add(Restrictions.eq("games",games));

        Date date = CoreDateUtils.parseDate(dateStr, "yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);   //设置当前日期
        c.add(Calendar.DATE, 1); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
        Date after = c.getTime(); //结果
        criterionList.add(Restrictions.ge("createDate",date));
        criterionList.add(Restrictions.lt("createDate",after));

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));

        List<ScoreTable> list = scoreTableRepository.list(criterionList,orderList);
        if(list.size()<1){
            return new ArrayList<>();
        }else {
            return list.get(0).getBetInfo();
        }

    }
}
