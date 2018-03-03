package eu.hquer.wekdroid.authenticator;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import eu.hquer.wekdroid.activity.AccountsActivity;
import eu.hquer.wekdroid.enums.AuthenticationEnum;

/**
 * Created by mariovor on 03.03.18.
 * Inspired by https://www.pilanites.com/android-account-manager/
 */

public class WekanAccountAuthenticator extends AbstractAccountAuthenticator {
    Context mContext;

    public WekanAccountAuthenticator(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String accountType,
                             String authTokenType, String[] requiredFeatures, Bundle options)
            throws NetworkErrorException {
        final Intent intent = new Intent(mContext, AccountsActivity.class);

        // An account name
        intent.putExtra(AuthenticationEnum.accountType.getName(), accountType);

        // Arbitrary name way of identifying the token's type to differ between multiple permissions)
        intent.putExtra(AuthenticationEnum.authTokenType.getName(), authTokenType);

        // Copy this exactly from the line below.
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);

        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String authTokenType,  Bundle bundle) throws NetworkErrorException {
        AccountManager am = AccountManager.get(mContext);

        String authToken = am.peekAuthToken(account, authTokenType);

//        if (TextUtils.isEmpty(authToken)) {
//            authToken = HTTPNetwork.login(account.name, am.getPassword(account));
//        }


        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // No authToken found, login required
        final Intent intent = new Intent(mContext, AccountsActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
        intent.putExtra(AuthenticationEnum.accountType.getName(), account.type);
        intent.putExtra(AuthenticationEnum.authTokenType.getName(), authTokenType);

        Bundle retBundle = new Bundle();
        retBundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return retBundle;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}
