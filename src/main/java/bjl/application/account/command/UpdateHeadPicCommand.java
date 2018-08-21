package bjl.application.account.command;


import bjl.application.shared.command.SharedCommand;

/**
 * Created by pengyi on 2016/4/12.
 */
public class UpdateHeadPicCommand extends SharedCommand {

    private String handPic;

    public String getHandPic() {
        return handPic;
    }

    public void setHandPic(String handPic) {
        this.handPic = handPic;
    }
}
