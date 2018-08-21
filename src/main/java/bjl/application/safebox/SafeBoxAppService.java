package bjl.application.safebox;

import bjl.domain.service.safebox.ISafeBoxService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by zhangjin on 2017/12/27.
 */
@Service("safeBoxAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SafeBoxAppService implements ISafeBoxAppService{

    @Autowired
    private ISafeBoxService safeBoxService;

    @Override
    public JSONObject get(JSONObject jsonObject) {
        return safeBoxService.get(jsonObject);
    }

    @Override
    public JSONObject access(JSONObject jsonObject, Integer type) {
        return safeBoxService.access(jsonObject,type);
    }
}
