package com.tenantsproject.flatmates.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalDigitsInputFilter {

    Pattern mPattern;

    public DecimalDigitsInputFilter() {
        mPattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");
    }

    public boolean filter(CharSequence source) {
        Matcher matcher = mPattern.matcher(source);
        return matcher.matches();
    }
}