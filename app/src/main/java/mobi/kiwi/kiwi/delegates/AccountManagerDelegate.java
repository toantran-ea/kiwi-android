package mobi.kiwi.kiwi.delegates;

import android.accounts.Account;
import android.accounts.AccountManager;

/**
 * Created by toan on 8/8/15.
 */
public class AccountManagerDelegate {
    private AccountManager mAccountManager;

    public AccountManagerDelegate(AccountManager accountManager) {
        this.mAccountManager = accountManager;
    }

    public Account[] getAllAccounts() {
        return mAccountManager.getAccounts();
    }
}
