package eu.hquer.wekdroid.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import eu.hquer.wekdroid.R;
import eu.hquer.wekdroid.enums.AuthenticationEnum;
import eu.hquer.wekdroid.enums.SharedPrefEnum;
import eu.hquer.wekdroid.model.User;
import eu.hquer.wekdroid.model.WekanToken;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class AccountsActivity extends BaseAcitvity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private AutoCompleteTextView mBaseUrl;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    SharedPreferences sharedPreferences;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        sharedPreferences = getSharedPreferences(SharedPrefEnum.BASE_PREF.getName(), Context.MODE_PRIVATE);
        basePath = sharedPreferences.getString(SharedPrefEnum.BASE_URL.getName(), null);
        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username_form);
        mBaseUrl = (AutoCompleteTextView) findViewById(R.id.baseUrl);

        if (basePath != null && !basePath.isEmpty()) {
            mBaseUrl.setText(basePath);
        }

        mPasswordView = (EditText) findViewById(R.id.password_form);

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password, mBaseUrl.getText().toString());
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        protected String errorMessage;

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password, String baseUrl) {
            mUsername = username;
            mPassword = password;
            basePath = mBaseUrl.getText().toString();
            // Store baseURL
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SharedPrefEnum.BASE_URL.getName(), basePath);
            editor.apply();

            createService();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            User user = new User();
            user.setUsername(mUsername);
            user.setPassword(mPassword);
            Call<WekanToken> authenticateCall = wekanService.authenticate(user);

            try {
                Response<WekanToken> execute = authenticateCall.execute();
                if (execute.body() == null) {
                    errorMessage = String.format("Code %s/ %s", execute.raw().code(), execute.raw().message());
                    return false;
                }
                String tokenText = token = execute.body().getToken();
                userId = execute.body().getId();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SharedPrefEnum.USER_ID.getName(), userId);
                editor.apply();
                token = String.format("Bearer %s", tokenText);
                createAccount(mUsername, mPassword, token);
                startActivity(new Intent(AccountsActivity.this, MainActivity.class));
            } catch (IOException e) {
                errorMessage = e.getMessage();
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                Toast.makeText(AccountsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public void createAccount(String username, String password, String authToken) {
        Account account = new Account(username, AuthenticationEnum.accountType.getName());

        AccountManager am = AccountManager.get(this);
        am.addAccountExplicitly(account, password, null);
        am.setAuthToken(account, AuthenticationEnum.authTokenType.getName(), authToken);
    }
}

