package bjl.application.phonebet.command;

/**
 * Created by zhangjin on 2017/12/27.
 */
public class BetData {

    private Integer index;
    private String text;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BetData(Integer index, String text) {
        this.index = index;
        this.text = text;
    }
}
