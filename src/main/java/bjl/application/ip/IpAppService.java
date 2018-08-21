package bjl.application.ip;

import bjl.domain.model.ip.Ip;
import bjl.domain.service.ip.IIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangjin on 2017/11/1.
 */
@Service("ipAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class IpAppService implements IIpAppService {

    @Autowired
    private IIpService ipService;

    @Override
    public void create(String parent, String ip) {
        ipService.create(parent,ip);
    }

    @Override
    public Ip getIp(String ip) {
        return ipService.getIp(ip);
    }
}
