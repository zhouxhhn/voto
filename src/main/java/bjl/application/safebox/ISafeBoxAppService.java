package bjl.application.safebox;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhangjin on 2017/12/27.
 */
public interface ISafeBoxAppService {

    JSONObject get(JSONObject jsonObject);

    JSONObject access(JSONObject jsonObject, Integer type);
}
