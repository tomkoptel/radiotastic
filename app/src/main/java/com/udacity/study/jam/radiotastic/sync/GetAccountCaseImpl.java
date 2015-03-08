/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.domain.GetAccountCase;

import javax.inject.Inject;

public class GetAccountCaseImpl implements GetAccountCase {

    private final Context context;
    private final AccountManager accountManager;

    @Inject
    public GetAccountCaseImpl(Context context) {
        this.context = context;
        this.accountManager = (AccountManager)
                context.getSystemService(Context.ACCOUNT_SERVICE);
    }

    @Override
    public Account get() {
        Account defaultAccount = new Account(
                context.getString(R.string.app_name),
                context.getString(R.string.sync_account_type));
        if (null == accountManager.getPassword(defaultAccount)) {
            if (!accountManager.addAccountExplicitly(defaultAccount, "", null)) {
                throw new IllegalStateException("Default account was rejected!");
            }
        }
        return defaultAccount;
    }

}
