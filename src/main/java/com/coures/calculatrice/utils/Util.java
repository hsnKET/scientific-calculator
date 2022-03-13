package com.coures.calculatrice.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.coures.calculatrice.R;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Util {

    public static boolean isLandscape(Context context){
        return context.getResources()
                .getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static String isFunction(String exp){
        String[] fs = {"cos","sin","tan","exp","ln","lot","deg","rad","arccos","arcsin","arctan","abs","fact","sqrt"};
        String f = Util.getFunction(exp);
        return Arrays.asList(fs).contains(f)?f:null;
    }

    public static String getFunction(String exp){
        int i = exp.length()-2;
        StringBuilder res = new StringBuilder("");
        while(i>= 0 && Util.isLetter(exp.charAt(i)) && exp.charAt(i)!='x'){
            res.append(exp.charAt(i));
            i--;
        }
        return res.reverse().toString();
    }

    public static boolean isParenthesesClosed(String exp){
        int j=0;
        for (int i=0;i<exp.length();i++) if (exp.charAt(i)=='('||exp.charAt(i)==')')j++;
        return j % 2 == 0;
    }
    public static boolean isNumber(char c){

        return c >= '0' && c <= '9';

    }
    public static boolean isLetter(char c){

        return c >= 'a' && c <= 'z';

    }
    public static boolean canAddDot(String exp){
        int j = Util.operatorsCount(exp);
        int k = Util.dotsCount(exp);
        return (k==0) || k < j;

    }
    private static int operatorsCount(String exp){
        int j=0;
        for (int i=0;i<exp.length();i++) if (isOperator(exp.charAt(i)))j++;
        return j;
    }
    private static int dotsCount(String exp){
        int j=0;
        for (int i=0;i<exp.length();i++) if (exp.charAt(i) == '.')j++;
        return j;
    }
    public static boolean isOperator(char c){
        return    c == '+'
                ||c == '-'
                ||c == '/'
                ||c == 'X'
                ||c == 'x'
                ||c == '%';
    }
    public static boolean isOpenParentheses(char c){
        return    c == '(';
    }
    public static boolean isCloseParentheses(char c){
        return    c == ')';
    }
    public static void slideLeft(Context c,View v){
        Animation animation1 =
                AnimationUtils.loadAnimation(c, R.anim.slide_left);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setScaleX(0);
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(animation1);
    }
    public static void slideRight(Context c,View v){
        Animation animation1 =
                AnimationUtils.loadAnimation(c, R.anim.slide_right);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setScaleX(1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(animation1);
    }
}
