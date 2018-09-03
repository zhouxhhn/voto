package bjl.core.util;

import bjl.application.account.IAccountAppService;
import bjl.application.activity.IActivityAppService;
import bjl.application.agent.IAgentAppService;
import bjl.application.agent.IAgentConfigAppService;
import bjl.application.agent.IAgentProfitAppService;
import bjl.application.bank.IBankAppService;
import bjl.application.bettable.IBetTableAppService;
import bjl.application.carousel.ICarouselAppService;
import bjl.application.chat.IChatAppService;
import bjl.application.financialSummary.IFinancialSummaryAppService;
import bjl.application.gamebet.IGameBetAppService;
import bjl.application.gamedetailed.IGameDetailedAppService;
import bjl.application.guide.IGuideAppService;
import bjl.application.notice.INoticeAppService;
import bjl.application.phonebet.IPhoneBetAppService;
import bjl.application.profitrecord.IProfitRecordAppService;
import bjl.application.ratio.IRatioAppService;
import bjl.application.recharge.IRechargeAppService;
import bjl.application.recharge.IRechargeCtlAppService;
import bjl.application.robot.IRobotAppService;
import bjl.application.safebox.ISafeBoxAppService;
import bjl.application.scoredetailed.IScoreDetailedAppService;
import bjl.application.scoretable.IScoreTableAppService;
import bjl.application.spreadprofit.ISpreadProfitAppService;
import bjl.application.systemconfig.ISystemConfigAppService;
import bjl.application.transfer.ITransferAppService;
import bjl.application.upDownPoint.IUpDownPointAppService;
import bjl.application.userManager.IUserManagerAppService;
import bjl.application.withdraw.IWithdrawAppService;
import bjl.core.upload.FileUploadConfig;
import bjl.core.upload.IFileUploadService;
import bjl.domain.service.bettable.IBetTableService;
import bjl.domain.service.scoredetailed.IScoreDetailedService;
import bjl.domain.service.scoretable.IScoreTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *  service工具类，即在非controller类中使用service
 * Created by zhangjin on 2017/10/27.
 */
@Component
public class ServiceUtil {


    @Autowired
    private IUserManagerAppService userManagerAppService;
    @Autowired
    private IAccountAppService accountAppService;
    @Autowired
    private ITransferAppService transferAppService;
    @Autowired
    private IBankAppService bankAppService;
    @Autowired
    private ISafeBoxAppService safeBoxAppService;
    @Autowired
    private IRechargeAppService rechargeAppService;
    @Autowired
    private IWithdrawAppService withdrawAppService;
    @Autowired
    private IPhoneBetAppService phoneBetAppService;
    @Autowired
    private IChatAppService chatAppService;
    @Autowired
    private IGameBetAppService gameBetAppService;
    @Autowired
    private IFileUploadService fileUploadService;
    @Autowired
    private IGameDetailedAppService gameDetailedAppService;
    @Autowired
    private IAgentAppService agentAppService;
    @Autowired
    private IAgentProfitAppService agentProfitAppService;
    @Autowired
    private IAgentConfigAppService agentConfigAppService;
    @Autowired
    private INoticeAppService noticeAppService;
    @Autowired
    private ISystemConfigAppService systemConfigAppService;
    @Autowired
    private IUpDownPointAppService upDownPointAppService;
    @Autowired
    private IRechargeCtlAppService rechargeCtlAppService;
    @Autowired
    private IFinancialSummaryAppService financialSummaryAppService;
    @Autowired
    private IProfitRecordAppService profitRecordAppService;
    @Autowired
    private ISpreadProfitAppService spreadProfitAppService;
    @Autowired
    private FileUploadConfig fileUploadConfig;
    @Autowired
    private IRobotAppService robotAppService;
    @Autowired
    private IScoreTableAppService scoreTableAppService;
    @Autowired
    private IBetTableAppService betTableAppService;
    @Autowired
    private IRatioAppService ratioAppService;
    @Autowired
    private IActivityAppService activityAppService;
    @Autowired
    private ICarouselAppService carouselAppService;
    @Autowired
    private IGuideAppService guideAppService;
    @Autowired
    private IScoreDetailedAppService scoreDetailedAppService;

    public IScoreDetailedAppService getScoreDetailedAppService(){
        return scoreDetailedAppService;
    }

    public ICarouselAppService getCarouselAppService() {
        return carouselAppService;
    }

    public IGuideAppService getGuideAppService() {
        return guideAppService;
    }

    public IActivityAppService getActivityAppService() {
        return activityAppService;
    }

    public IRatioAppService getRatioAppService() {
        return ratioAppService;
    }

    public IScoreTableAppService getScoreTableAppService() {
        return scoreTableAppService;
    }

    public IBetTableAppService getBetTableAppService() {
        return betTableAppService;
    }

    public IRobotAppService getRobotAppService() {
        return robotAppService;
    }

    public FileUploadConfig getFileUploadConfig() {
        return fileUploadConfig;
    }

    public ISpreadProfitAppService getSpreadProfitAppService() {
        return spreadProfitAppService;
    }

    public IProfitRecordAppService getProfitRecordAppService() {
        return profitRecordAppService;
    }

    public IFinancialSummaryAppService getFinancialSummaryAppService() {
        return financialSummaryAppService;
    }

    public IRechargeCtlAppService getRechargeCtlAppService() {
        return rechargeCtlAppService;
    }

    public IAgentConfigAppService getAgentConfigAppService() {
        return agentConfigAppService;
    }

    public IUpDownPointAppService getUpDownPointAppService() {
        return upDownPointAppService;
    }

    public ISystemConfigAppService getSystemConfigAppService() {
        return systemConfigAppService;
    }

    public INoticeAppService getNoticeAppService() {
        return noticeAppService;
    }

    public IAgentProfitAppService getAgentProfitAppService() {
        return agentProfitAppService;
    }

    public IAgentAppService getAgentAppService() {
        return agentAppService;
    }

    public IGameDetailedAppService getGameDetailedAppService() {
        return gameDetailedAppService;
    }

    public IFileUploadService getFileUploadService() {
        return fileUploadService;
    }

    public IGameBetAppService getGameBetAppService() {
        return gameBetAppService;
    }

    public IChatAppService getChatAppService() {
        return chatAppService;
    }

    public IPhoneBetAppService getPhoneBetAppService() {
        return phoneBetAppService;
    }

    public IWithdrawAppService getWithdrawAppService() {
        return withdrawAppService;
    }

    public IRechargeAppService getRechargeAppService() {
        return rechargeAppService;
    }

    public ISafeBoxAppService getSafeBoxAppService() {
        return safeBoxAppService;
    }

    public IBankAppService getBankAppService() {
        return bankAppService;
    }

    public ITransferAppService getTransferAppService() {
        return transferAppService;
    }

    public IUserManagerAppService getUserManagerAppService() {
        return userManagerAppService;
    }

    public IAccountAppService getAccountAppService() {
        return accountAppService;
    }

    public static ServiceUtil serviceUtil;


    @PostConstruct
    public void init(){
        serviceUtil = this;
        serviceUtil.userManagerAppService = this.userManagerAppService;
        serviceUtil.accountAppService = this.accountAppService;
        serviceUtil.transferAppService = this.transferAppService;
        serviceUtil.bankAppService = this.bankAppService;
        serviceUtil.safeBoxAppService = this.safeBoxAppService;
        serviceUtil.rechargeAppService = this.rechargeAppService;
        serviceUtil.withdrawAppService = this.withdrawAppService;
        serviceUtil.phoneBetAppService = this.phoneBetAppService;
        serviceUtil.chatAppService = this.chatAppService;
        serviceUtil.gameBetAppService = this.gameBetAppService;
        serviceUtil.fileUploadService = this.fileUploadService;
        serviceUtil.gameDetailedAppService = this.gameDetailedAppService;
        serviceUtil.agentAppService = this.agentAppService;
        serviceUtil.agentProfitAppService  = this.agentProfitAppService;
        serviceUtil.noticeAppService = this.noticeAppService;
        serviceUtil.systemConfigAppService = this.systemConfigAppService;
        serviceUtil.upDownPointAppService = this.upDownPointAppService;
        serviceUtil.agentConfigAppService = this.agentConfigAppService;
        serviceUtil.rechargeCtlAppService = this.rechargeCtlAppService;
        serviceUtil.financialSummaryAppService = this.financialSummaryAppService;
        serviceUtil.profitRecordAppService = this.profitRecordAppService;
        serviceUtil.spreadProfitAppService = this.spreadProfitAppService;
        serviceUtil.fileUploadConfig = this.fileUploadConfig;
        serviceUtil.scoreTableAppService = this.scoreTableAppService;
        serviceUtil.betTableAppService = this.betTableAppService;
        serviceUtil.ratioAppService = this.ratioAppService;
    }
}
