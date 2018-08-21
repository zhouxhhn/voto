package bjl.application.personal.reprensentation.mapping;

import bjl.application.personal.reprensentation.PersonalRepresentation;
import bjl.domain.model.gamedetailed.GameDetailed;
import bjl.domain.model.user.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by zhangjin on 2018/1/15.
 */
@Component
public class PersonalRepresentationMapper extends CustomMapper<GameDetailed, PersonalRepresentation> {

    public void mapAtoB(GameDetailed gameDetailed, PersonalRepresentation representation, MappingContext context) {

        User user = gameDetailed.getUser();
        representation.setName(user.getAccount().getName());
        representation.setPlayerPro(gameDetailed.getPlayer().multiply(user.getBankerPlayerProportion()).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP));
        representation.setBankerPro(gameDetailed.getBanker().multiply(user.getBankerPlayerProportion()).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP));
        representation.setPlayerPir(gameDetailed.getPlayerPair().multiply(user.getTriratnaProportion()).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP));
        representation.setBankerPir(gameDetailed.getBankPair().multiply(user.getTriratnaProportion()).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP));
        representation.setDrawPro(gameDetailed.getDraw().multiply(user.getTriratnaProportion()).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP));
        representation.setBankPlayLose(gameDetailed.getBankPlayLose().multiply(user.getBankerPlayerProportion()).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP));
        representation.setBankPlayProfit(gameDetailed.getBankPlayProfit().multiply(user.getBankerPlayerProportion()).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP));
        representation.setTriratnaLose(gameDetailed.getTriratnaLose().multiply(user.getTriratnaProportion()).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP));
        representation.setTriratnaProfit(gameDetailed.getTriratnaProfit().multiply(user.getTriratnaProportion()).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP));
        representation.setEffective(((gameDetailed.getBanker().subtract(gameDetailed.getPlayer())).abs().multiply(user.getBankerPlayerProportion()).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP))
                .add(((gameDetailed.getBankPair().add(gameDetailed.getPlayerPair()).add(gameDetailed.getDraw())).multiply(user.getTriratnaProportion())).divide(BigDecimal.valueOf(100),0,BigDecimal.ROUND_HALF_UP)));

    }
}
