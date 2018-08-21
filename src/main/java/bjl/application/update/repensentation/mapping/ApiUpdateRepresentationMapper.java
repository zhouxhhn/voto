package bjl.application.update.repensentation.mapping;

import bjl.application.update.repensentation.ApiUpdateRepresentation;
import bjl.domain.model.update.Update;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2017/12/23.
 */
@Component
public class ApiUpdateRepresentationMapper extends CustomMapper<Update, ApiUpdateRepresentation> {

    public void mapAtoB(Update update, ApiUpdateRepresentation representation, MappingContext context) {
        representation.setHtml_download_url(update.getHtmlUrl());
        representation.setAndroid_download_url(update.getAndroidUrl());
        representation.setIos_download_url(update.getIosUrl());
        representation.setHtml_version(update.getHtmlVersion());
        representation.setAndroid_version(update.getAndroidVersion());
        representation.setIos_version(update.getIosVersion());
    }
}
