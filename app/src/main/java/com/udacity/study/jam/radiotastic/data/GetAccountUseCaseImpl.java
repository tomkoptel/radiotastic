/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.udacity.study.jam.radiotastic.data;

import android.accounts.Account;
import android.content.Context;

import com.udacity.study.jam.radiotastic.R;
import com.udacity.study.jam.radiotastic.domain.GetAccountUseCase;

import javax.inject.Inject;

public class GetAccountUseCaseImpl implements GetAccountUseCase {

    @Inject Context mContext;

    @Inject
    public GetAccountUseCaseImpl() {
    }

    @Override
    public Account execute() {
        return new Account(
                mContext.getString(R.string.app_name),
                mContext.getString(R.string.sync_account_type));
    }

}
