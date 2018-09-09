package bjl.domain.model.publicRecharge;

import bjl.core.id.ConcurrencySafeEntity;

/**
 * Created by zhangjin on 2018/6/21
 */
public class PublicRecharge extends ConcurrencySafeEntity {

    private String title; //标题
    private String content; //内容
    private String image;//图片

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
