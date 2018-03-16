package com.feihua.jjcb.jd.phone.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.feihua.jjcb.jd.phone.constants.Constants;
import com.feihua.jjcb.jd.phone.trace.TraceService;


public class TargetReceiver extends BroadcastReceiver
{
    public TargetReceiver()
    {
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("com.feihua.jjcb.jd.service.destroy"))
        {
            //TODO
            //在这里写重新启动service的相关操作
            startUploadService(context);
        }
        else if (intent.getAction().equals(Constants.TRACESERVICE_DESTORY))
        {
            startTrace(context);
        }
    }

    private void startTrace(Context context)
    {
        Intent intent = new Intent(context, TraceService.class);
        context.startService(intent);
    }

    private void startUploadService(Context context)
    {
        Intent i = new Intent();
        i.setClass(context, TargetService.class);
        context.startService(i);
    }


}
