package bjl.application.ratio;

import bjl.application.ratio.command.EditRatioCommand;
import bjl.application.ratio.representation.RatioRepresentation;
import bjl.core.mapping.IMappingService;
import bjl.domain.model.ratio.Ratio;
import bjl.domain.service.ratio.IRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjin on 2018/5/2
 */
@Service("ratioAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RatioAppService implements IRatioAppService{

    @Autowired
    private IRatioService ratioService;
    @Autowired
    private IMappingService mappingService;

    @Override
    public RatioRepresentation getByAccount(String accountId) {
        Ratio ratio = ratioService.getByAccount(accountId);

        if(ratio != null){
            return mappingService.map(ratio, RatioRepresentation.class,false);
        }
        return null;
    }

    @Override
    public Ratio edit(EditRatioCommand command) {
        return ratioService.edit(command);
    }

    @Override
    public void update(Ratio ratio) {
        ratioService.update(ratio);
    }
}
