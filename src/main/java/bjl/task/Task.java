package bjl.task;

import bjl.application.spreadprofit.ISpreadProfitAppService;
import bjl.core.upload.IFileUploadService;
import bjl.interfaces.shared.api.BaseApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 定时任务
 * Created by zhangjin on 2018/3/21
 */
@Component
public class Task extends BaseApiController {

    @Autowired
    private ISpreadProfitAppService spreadProfitAppService;
    @Autowired
    private IFileUploadService fileUploadService;

//    /**
//     * 每天凌晨1秒的时候执行
//     */
//    @Scheduled(cron="1 0 0 * * ?")
//    public void myTask(){
//
//        spreadProfitAppService.count(2);
//        logger.info("推广收益每日定时任务已执行");
//    }
//
//    /**
//     * 每两小时执行一次
//     */
//    @Scheduled(cron="0 0 1/2 * * ?")
//    public void myTask1(){
//        spreadProfitAppService.count(1);
//        logger.info("推广收益每2小时定时任务已执行");
//    }
//
//    /**
//     * 每半小时执行一次
//     */
//    @Scheduled(cron="0 0/30 * * * ? *")
//    public void myTask2(){
//
//        fileUploadService.deleteCode(null);
//        logger.info("已清除过期验证码");
//    }

}
