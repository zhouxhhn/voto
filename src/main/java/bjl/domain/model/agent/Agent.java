package bjl.domain.model.agent;

import bjl.core.id.ConcurrencySafeEntity;
import bjl.domain.model.account.Account;
import bjl.domain.model.user.User;

/**
 * 代理
 * Created by zhangjin on 2017/12/26.
 */
public class Agent extends ConcurrencySafeEntity {

    private Account parent;
    private Account user;

    public Account getParent() {
        return parent;
    }

    public void setParent(Account parent) {
        this.parent = parent;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }
}
