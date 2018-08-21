package bjl.application.ip;

import bjl.domain.model.ip.Ip;

/**
 * Created by zhangjin on 2017/11/1.
 */
public interface IIpAppService {

    void create(String parent, String ip);

    Ip getIp(String ip);
}
