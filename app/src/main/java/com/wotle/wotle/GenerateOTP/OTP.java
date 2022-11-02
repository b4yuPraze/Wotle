package com.wotle.wotle.GenerateOTP;

import android.content.Context;

import java.util.Random;

public class OTP {

    public char[] oneTimePassword(){
        String number = "0123456789";
        Random random = new Random();

        char[] otp = new char[4];

        for (int i = 0; i < 4; i++){
            otp[i] = number.charAt(random.nextInt(number.length()));
        }

        return otp;
    }



}
