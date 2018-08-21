package bjl.domain.service.safebox;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhangjin on 2017/12/27.
 */
public interface ISafeBoxService {

    JSONObject get(JSONObject jsonObject);

    JSONObject access(JSONObject jsonObject, Integer type);
}
