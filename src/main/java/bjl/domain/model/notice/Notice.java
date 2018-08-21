package bjl.domain.model.notice;

import bjl.core.id.ConcurrencySafeEntity;

import java.util.Date;

/**
 * 公告
 * Created by pengyi on 2016/4/15.
 */
public class Notice extends ConcurrencySafeEntity {

    private Integer type;           //类型  1文字  2图片
    private String title;           //公告标题
    private String content;         //公告内容
    private String image;           //图片链接

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
