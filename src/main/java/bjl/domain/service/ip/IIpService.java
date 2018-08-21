package bjl.domain.service.ip;

import bjl.domain.model.ip.Ip;

/**
 * Created by zhangjin on 2017/11/1.
 */
public interface IIpService {

    void create(String parent, String ip);

    Ip getIp(String ip);
}
