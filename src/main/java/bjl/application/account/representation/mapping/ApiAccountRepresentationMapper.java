package bjl.application.account.representation.mapping;

import bjl.application.account.representation.ApiAccountRepresentation;
import ma.glasnost.orika.CustomMapper;
import bjl.domain.model.account.Account;
import org.springframework.stereotype.Component;

/**
 * Created by pengyi on 2016/3/30 0030.
 */
@Component
public class ApiAccountRepresentationMapper extends CustomMapper<Account, ApiAccountRepresentation> {
}
