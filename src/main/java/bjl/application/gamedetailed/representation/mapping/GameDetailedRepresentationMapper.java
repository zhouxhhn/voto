package bjl.application.gamedetailed.representation.mapping;

import bjl.application.gamedetailed.representation.GameDetailedRepresentation;
import bjl.domain.model.gamedetailed.GameDetailed;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjin on 2018/1/8.
 */
@Component
public class GameDetailedRepresentationMapper extends CustomMapper<GameDetailed,GameDetailedRepresentation> {

    public void mapAtoB(GameDetailed gameDetailed, GameDetailedRepresentation representation, MappingContext context) {

        representation.setXjNum(gameDetailed.getBoots()+"靴"+gameDetailed.getGames()+"局");
        representation.setName(gameDetailed.getUser().getAccount().getName());
        representation.setResult( (gameDetailed.getLottery()[0] == 1 ? "庄 ":"")+ (gameDetailed.getLottery()[1] == 1 ? "闲 ":"")
                +(gameDetailed.getLottery()[2] == 1 ? "庄对 ":"")+ (gameDetailed.getLottery()[3] == 1 ? "闲对 ":"")+(gameDetailed.getLottery()[4] == 1 ? "和":""));
    }
}
