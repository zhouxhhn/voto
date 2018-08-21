package bjl.domain.model.carousel;

import bjl.core.id.ConcurrencySafeEntity;

/**
 * Created by zhangjin on 2018/6/21
 */
public class Carousel extends ConcurrencySafeEntity {

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
