package com.jappka.mirrorclient.googleAuth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jappka.mirrorclient.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Activity with Google API request
 * Only Gmail and Calendar
 * Only Read rights
 */
public class GetGmailTokenActivity extends AppCompatActivity {

    /**
     * Authentication data to Google APIs
     */
    private final String GMAIL_SCOPE = "https://www.googleapis.com/auth/gmail.readonly";
    private final String CALENDAR_SCOPE = "https://www.googleapis.com/auth/calendar.readonly";
    private final String FULL_SCOPE = "oauth2:" + GMAIL_SCOPE + " " + CALENDAR_SCOPE;

    /**
     * Interface elements on Authentication Page
     */
    private Button requireGmailAccessButton;
    private TextView gmailTokenLabel;
    private Button chooseAccountButton;
    private TextView selectedAccountLabel;

    private final Activity context = this;
    public AccountManager accountManager;
    private Account[] accounts;
    private Account selectedAccount;

    /**
     * Elements to get tokens asynchronously
     */
    private final ExecutorService service = Executors.newFixedThreadPool(1);
    private  Future<String> googleTask;

    private final int CHOOSE_ACCOUNT = 124;
    private final String errorMessage = "Error! Try again or contact our support (supporrt@magicmirror.com)!";
    private final String operationCanceledMessage = "You denied access to this service." +
            "Without access to it you can't use widget for this service on Mirror device." +
            "Please, click button again to grant access.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_gmail_token);
        accountManager = AccountManager.get(this);
        accounts = accountManager.getAccountsByType("com.google");

        setupAccountPicker();
        setupGmailAuthentication();
    }

    /**
     * Setup google account picker
     * User will have to choose one of existing account or add new one.
     * TODO: check how it works when only one account is present on device!
     */
    private void setupAccountPicker(){
        chooseAccountButton = (Button) findViewById(R.id.chooseAccountButton);
        selectedAccountLabel = (TextView) findViewById(R.id.selectedAccountLabel);

        chooseAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AccountManager.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
                startActivityForResult(intent, CHOOSE_ACCOUNT);
            }
        });
    }

    /**
     * Set all listeners
     * Ask for Gmail auth token after click on button
     */
    private void setupGmailAuthentication(){

        requireGmailAccessButton = (Button) findViewById(R.id.requireGoogleAccessButton);
        gmailTokenLabel = (TextView) findViewById(R.id.googleTokenLabel);

        requireGmailAccessButton.setEnabled(true);
        requireGmailAccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleTask = service.submit(new AskForToken(accountManager, selectedAccount, FULL_SCOPE, context));
                try {
                    String token = googleTask.get();
                    gmailTokenLabel.setText("Token:\n" + token);
                } catch (Exception e) {
                    e.printStackTrace();
                    gmailTokenLabel.setText(errorMessage);
                }
            }
        });
    }

    /**
     * Method to retrieve result from system account picker
     * TODO: Refactor this
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CHOOSE_ACCOUNT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

                // TODO: probably this can be done easier, without searching through the account list
                for( Account account:accounts){
                    if(account.name.equals(accountName)){
                        selectedAccount = account;
                        chooseAccountButton.setText("Change account");
                        selectedAccountLabel.setText("Current selected account: " + accountName);
                        break;
                    }
                }
            }
        }
    }
}

