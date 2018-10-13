package com.movie.movie.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;


/*
 * The AppUtil class is used for perform different tasks for other class
 * */

public class AppUtil {
  private static final String TAG = AppUtil.class.getSimpleName();

  // hide the keyboard for an activity
  // hide the keyboard for an activity
  public static void hideKeyboard(Activity activity) {
    InputMethodManager imm = (InputMethodManager)
            activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
  }


  // checking if there is network connection
  public static boolean isNetworkConnected(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    if (cm != null) {
      return cm.getActiveNetworkInfo() != null;
    }

    return  false;
  }

  // return the year of date according to supplied date pattern
  public static String getYearFromDate(String date, String pattern) {
    try {
      DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern).withLocale(Locale.US);
      LocalDate localDate = formatter.parseLocalDate(date);
      return String.valueOf(localDate.getYear());
    } catch (Exception e) {
      Log.e(TAG, e.getMessage());
      return date;
    }
  }
}
