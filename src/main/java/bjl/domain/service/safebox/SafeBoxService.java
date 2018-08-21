package bjl.domain.service.safebox;

import bjl.core.common.PasswordHelper;
import bjl.core.util.CoreStringUtils;
import bjl.domain.model.safebox.ISafeBoxRepository;
import bjl.domain.model.safebox.SafeBox;
import bjl.domain.model.user.IUserRepository;
import bjl.domain.model.user.User;
import bjl.domain.service.userManager.IUserManagerService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangjin on 2017/12/27.
 */
@Service("safeBoxService")
public class SafeBoxService implements ISafeBoxService {

    @Autowired
    private ISafeBoxRepository<SafeBox, String> safeBoxRepository;
    @Autowired
    private IUserManagerService userManagerService;
    @Autowired
    private IUserRepository<User, String> userRepository;

    /**
     * 获取用户保险箱信息
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject get(JSONObject jsonObject) {

        String userId = jsonObject.getString("userid");
        if(CoreStringUtils.isEmpty(userId)){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","账号不存在");
            return jsonObject;
        }
        //账号是否存在
        User user = userManagerService.searchByUsername(userId);
        if(user == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","账号不存在");
            return jsonObject;
        }
        //验证密码
        String password = jsonObject.getString("pwd");
        if (CoreStringUtils.isEmpty(password) || !PasswordHelper.equalsBankPwd(user.getAccount(), password)) {
            jsonObject.put("code", 1);
            jsonObject.put("errmsg", "密码错误");
            return jsonObject;
        }
        jsonObject.remove("pwd");
        jsonObject.put("paysum",user.getScore());
        jsonObject.put("boxsum",user.getBankScore());
        jsonObject.put("totalsum",user.getBankScore().add(user.getScore()));
        jsonObject.put("minsum",1);
        jsonObject.put("code", 0);
        jsonObject.put("errmsg", "打开保险箱成功");

        return jsonObject;
    }

    /**
     * 向保险箱中存
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject access(JSONObject jsonObject, Integer type) {

        String userId = jsonObject.getString("userid");
        if(CoreStringUtils.isEmpty(userId)){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","账号不存在");
            return jsonObject;
        }
        //账号是否存在
        User user = userManagerService.searchByUsername(userId);
        if(user == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","账号不存在");
            return jsonObject;
        }
        BigDecimal bigDecimal = jsonObject.getBigDecimal("sum");
        if(bigDecimal == null){
            jsonObject.put("code",1);
            jsonObject.put("errmsg","金额异常");
            return jsonObject;
        }

        //更新积分
        if(type == 1){
            //存积分
            if(user.getScore().compareTo(bigDecimal) < 0){
                jsonObject.put("code",1);
                jsonObject.put("errmsg","积分不足");
                return jsonObject;
            }
            user.setScore(user.getScore().subtract(bigDecimal));
            user.setTotalScore(user.getTotalScore().subtract(bigDecimal));
            user.setBankScore(user.getBankScore().add(bigDecimal));
            jsonObject.put("errmsg","向保险箱存款成功");
        }else {
            //取积分
            if(user.getBankScore().compareTo(bigDecimal) < 0){
                jsonObject.put("code",1);
                jsonObject.put("errmsg","积分不足");
                return jsonObject;
            }
            user.setBankScore(user.getBankScore().subtract(bigDecimal));
            user.setScore(user.getScore().add(bigDecimal));
            user.setTotalScore(user.getTotalScore().add(bigDecimal));
            jsonObject.put("errmsg","从保险箱取款成功");
        }
        userRepository.update(user);

        //返回信息
        jsonObject.put("paysum",user.getScore());
        jsonObject.put("boxsum",user.getBankScore());
        jsonObject.put("totalsum",user.getBankScore().add(user.getScore()));
        jsonObject.put("code",0);

        return jsonObject;
    }
}
