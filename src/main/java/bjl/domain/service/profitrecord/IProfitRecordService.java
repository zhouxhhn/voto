package bjl.domain.service.profitrecord;

import bjl.domain.model.profitrecord.ProfitRecord;


/**
 * Created by zhangjin on 2018/1/30
 */
public interface IProfitRecordService {

    void create(Integer init, Integer profit);

    ProfitRecord getByTime();
}
