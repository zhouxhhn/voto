package bjl.domain.service.systemconfig;

import bjl.application.systemconfig.command.EditSystemConfigCommand;
import bjl.application.update.command.EditUpdateCommand;
import bjl.domain.model.systemconfig.ISystemConfigRepository;
import bjl.domain.model.systemconfig.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangjin on 2018/1/17
 */
@Service("systemConfigService")
public class SystemConfigService implements ISystemConfigService{

    @Autowired
    private ISystemConfigRepository<SystemConfig, String> systemConfigRepository;

    @Override
    public SystemConfig edit(EditSystemConfigCommand command) {

        List<SystemConfig> list = systemConfigRepository.findAll();
        SystemConfig systemConfig;
        if(list != null && list.size()>0){
            systemConfig = list.get(0);
        }else {
            systemConfig = new SystemConfig();
            systemConfig.setCreateDate(new Date());
        }
        systemConfig.setRechargeRatio(command.getRechargeRatio());
        systemConfig.setBankerPlayerMax(command.getBankerPlayerMax());
        systemConfig.setBankerPlayerMix(command.getBankerPlayerMix());
        systemConfig.setPlayerR(command.getPlayerR());
        systemConfig.setPrimeScore(command.getPrimeScore());
        systemConfig.setTriratnaMax(command.getTriratnaMax());
        systemConfig.setTriratnaMix(command.getTriratnaMix());
        systemConfig.setRechargeFee(command.getRechargeFee());
        systemConfig.setUpScoreFee(command.getUpScoreFee());
        systemConfig.setPump_1(command.getPump_1());
        systemConfig.setPump_2(command.getPump_2());
        systemConfig.setPump_3(command.getPump_3());
        systemConfig.setPump_4(command.getPump_4());
        systemConfig.setPump_5(command.getPump_5());
        systemConfig.setOpenPump(command.getOpenPump());

        systemConfigRepository.save(systemConfig);
        return systemConfig;
    }

    @Override
    public SystemConfig get() {

        List<SystemConfig> list = systemConfigRepository.findAll();
        if(list == null || list.size()<1){
            SystemConfig systemConfig = new SystemConfig();
            systemConfig.setRechargeRatio(BigDecimal.valueOf(1));
            systemConfig.setBankerPlayerMax(BigDecimal.valueOf(7500));
            systemConfig.setBankerPlayerMix(BigDecimal.valueOf(10));
            systemConfig.setPlayerR(BigDecimal.valueOf(0.01));
            systemConfig.setPrimeScore(BigDecimal.valueOf(1000));
            systemConfig.setTriratnaMax(BigDecimal.valueOf(950));
            systemConfig.setTriratnaMix(BigDecimal.valueOf(1));
            systemConfig.setRechargeFee(BigDecimal.valueOf(0.5));
            systemConfig.setUpScoreFee(BigDecimal.valueOf(0.9));
            systemConfig.setPump_1(BigDecimal.valueOf(0.5));
            systemConfig.setPump_2(BigDecimal.valueOf(0.3));
            systemConfig.setPump_3(BigDecimal.valueOf(0.2));
            systemConfig.setPump_4(BigDecimal.valueOf(0.1));
            systemConfig.setPump_5(BigDecimal.valueOf(0.05));
            systemConfig.setOpenPump(1);

            return systemConfig;
        }else {
            return list.get(0);
        }
    }
}
