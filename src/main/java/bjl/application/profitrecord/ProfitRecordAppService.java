package bjl.application.profitrecord;

import bjl.domain.model.profitrecord.ProfitRecord;
import bjl.domain.service.profitrecord.IProfitRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjin on 2018/1/30
 */
@Service("profitRecordAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProfitRecordAppService implements IProfitRecordAppService{

    @Autowired
    private IProfitRecordService profitRecordService;

    @Override
    public void create(Integer init, Integer profit) {
        profitRecordService.create(init,profit);
    }

    @Override
    public ProfitRecord getByTime() {
        return profitRecordService.getByTime();
    }
}
