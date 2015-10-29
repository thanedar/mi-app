package com.mitelcel.pack.utils;

import android.content.Context;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.R;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class Validator {

    Context context;

    public Validator(Context context) {
        this.context = context;
    }

    public String isNumberValid(String referral){

        if(referral.length() < Config.MSISDN_LENGTH)
            return this.context.getString(R.string.msisdn_not_valid);

        // If null return the referral code is OK
        return null;
    }

    public String isValidPassSignUp(String pass) {
        if(pass.length() < Config.PASS_LENGTH)
            return this.context.getString(R.string.password_more_digit);
        return null;
    }

    public String doesPassMatchSignUp(String pass, String passConfirm) {
        if(!pass.equals(passConfirm)){
            return this.context.getString(R.string.password_doesnt_match);
        }
        return null;
    }

}
