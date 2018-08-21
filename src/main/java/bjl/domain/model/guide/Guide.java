package bjl.domain.model.guide;

import bjl.core.id.ConcurrencySafeEntity;

/**
 * Created by zhangjin on 2018/6/21
 */
public class Guide extends ConcurrencySafeEntity {

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
