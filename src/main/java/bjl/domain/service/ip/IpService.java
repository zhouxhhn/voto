package bjl.domain.service.ip;

import bjl.core.util.CoreStringUtils;
import bjl.domain.model.ip.IIpRepository;
import bjl.domain.model.ip.Ip;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2017/11/1.
 */
@Service("ipService")
public class IpService implements IIpService{


    @Autowired
    private IIpRepository<Ip,String> ipRepository;

    /**
     * 创建ip
     * @param parent
     * @param ip
     */
    @Override
    public void create(String parent, String ip) {

        if(CoreStringUtils.isEmpty(ip) || CoreStringUtils.isEmpty(parent)){
            return;
        }

        Ip ipObject = this.getIp(ip);
        if(ipObject != null){
            ipObject.setParentId(parent);
            ipObject.setCreateDate(new Date());
            ipRepository.update(ipObject);
        }else {
            ipObject = new Ip();
            ipObject.setIp(ip);
            ipObject.setCreateDate(new Date());
            ipObject.setParentId(parent);
            ipRepository.save(ipObject);
        }
    }

    @Override
    public Ip getIp(String ip) {
        List<Criterion> criterionList = new ArrayList<>();
        if (!CoreStringUtils.isEmpty(ip)) {
            criterionList.add(Restrictions.like("ip", ip, MatchMode.ANYWHERE));
        }else {
            return null;
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        List<Ip> list = ipRepository.list(criterionList,orderList);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
