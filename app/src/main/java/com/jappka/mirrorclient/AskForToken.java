package com.jappka.mirrorclient;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telecom.Call;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by psienkiewicz on 29/12/15.
 */
public class AskForToken implements Callable<String> {
    /**
     * Token access requirements data
     */
    private AccountManager accountManager;
    private Account account;
    private String scope;
    private Activity context;

    /**
     * Token to return
     */
    private String token = null;

    public AskForToken(AccountManager accountManager, Account account, String scope, Activity context) {
        this.accountManager = accountManager;
        this.account = account;
        this.scope = scope;
        this.context = context;
    }

    @Override
    public String call() throws OperationCanceledException {
        try {
            AccountManagerFuture<Bundle> acc = accountManager.getAuthToken(account, scope, null, context, null, null);
            Bundle authTokenBundle = acc.getResult();
            token = authTokenBundle.get(AccountManager.KEY_AUTHTOKEN).toString();
            //Can be thrown: NullPointerException, OperationCanceledException, IOException, AuthenticatorException
        } catch (OperationCanceledException e){
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }
}
