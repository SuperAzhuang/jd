package com.feihua.jjcb.jd.phone.utils;

import android.app.DatePickerDialog;
import android.content.Context;


import com.feihua.jjcb.jd.phone.R;
import com.feihua.jjcb.jd.phone.view.MonPickerDialog;

import java.util.Calendar;


public abstract class DatePickDialogUtil implements DatePickerDialog.OnDateSetListener
{

    public void showMonPicker(Context ctx,String date) {
        final Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(DateUtil.strToDate("yyyy-MM", date));
        MonPickerDialog dialog = new MonPickerDialog(ctx, R.style.AppTheme_Dialog, this, localCalendar.get(Calendar.YEAR), localCalendar.get(Calendar.MONTH), localCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

}