package bjl.domain.service.profitrecord;

import bjl.domain.model.profitrecord.IProfitRecordRepository;
import bjl.domain.model.profitrecord.ProfitRecord;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/30
 */
@Service("profitRecordService")
public class ProfitRecordService implements IProfitRecordService{

    @Autowired
    private IProfitRecordRepository<ProfitRecord, String> profitRecordRepository;

    @Override
    public void create(Integer init, Integer profit) {
        ProfitRecord profitRecord = new ProfitRecord();
        profitRecord.setInit(init);
        profitRecord.setProfit(profit);
        profitRecord.setCreateDate(new Date());
        profitRecordRepository.save(profitRecord);
    }


    @Override
    public ProfitRecord getByTime() {

        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        List<ProfitRecord> list = profitRecordRepository.list(null,orderList,null,null,null,1);
        if(list.size() < 1){
            return null;
        }
        return list.get(0);
    }
}
