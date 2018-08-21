package bjl.application.ratio.representation.mapping;

import bjl.application.ratio.representation.RatioRepresentation;
import bjl.domain.model.ratio.Ratio;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/3/30
 */
@Component
public class RatioRepresentationMapper extends CustomMapper<Ratio, RatioRepresentation> {

    public void mapAtoB(Ratio ratio, RatioRepresentation representation, MappingContext context) {

        representation.setUserR(ratio.getAccount().getR() == null ? BigDecimal.valueOf(0) : ratio.getAccount().getR());
    }
}
