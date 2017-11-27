package xyz.no87.mywallpaper.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;

public class NotificationUtil {
    private static int LedID = 0;

    public static void notification(Context context, Uri uri,
                                    int icon, String ticker, String title, String msg) {
        LogUtil.i("notiry uri :" + uri);
        // 设置通知的事件消息
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            intent.setPackage(context.getPackageName());
        }
        intent.setData(uri);
        notification(context, intent, 0, icon, ticker, title, msg);
    }

    public static void notification(Context context, String activityClass, Bundle bundle,
                                    int icon, String ticker, String title, String msg) {
        // 设置通知的事件消息
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            intent.setPackage(context.getPackageName());
        }
        intent.putExtras(bundle);
        intent.setComponent(new ComponentName(context.getPackageName(), activityClass));
        notification(context, intent, 0, icon, ticker, title, msg);
    }

    public static void notification(Context context, Intent intent, int id,
                                    int icon, String ticker, String title, String msg) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification(context, pendingIntent, id, icon, ticker, title, msg);
    }

    public static void notification(Context context, PendingIntent pendingIntent, int id,
                                    int icon, String ticker, String title, String msg) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(icon);

        builder.setContentTitle(title);
        builder.setTicker(ticker);
        builder.setContentText(msg);

        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setLights(0xFFFFFF00, 0, 2000);
        builder.setVibrate(new long[]{0, 100, 300});
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        Notification baseNF;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            baseNF = builder.getNotification();
        } else {
            baseNF = builder.build();
        }
        //发出状态栏通知
        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        nm.notify(id, baseNF);
    }

    public static void lightLed(Context context, int colorOx, int durationMS) {
        lightLed(context, colorOx, 0, durationMS);
    }

    public static void lightLed(Context context, int colorOx, int startOffMS, int durationMS) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.ledARGB = colorOx;
        notification.ledOffMS = startOffMS;
        notification.ledOnMS = durationMS;
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        LedID++;
        nm.notify(LedID, notification);
        nm.cancel(LedID);
    }

    public static void lightLed(final Context context, final int colorOx, final int startOffMS, final int durationMS,
                                int repeat) {
        if (repeat < 1) {
            repeat = 1;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        for (int i = 0; i < repeat; i++) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lightLed(context, colorOx, startOffMS, durationMS);
                }
            }, (startOffMS + durationMS) * i);
        }
    }

    public static void lightLed(Context context, ArrayList<LightPattern> patterns) {
        if (patterns == null) {
            return;
        }
        for (LightPattern lp : patterns) {
            lightLed(context, lp.argb, lp.startOffMS, lp.durationMS);
        }
    }

    public static class LightPattern {
        public int argb = 0;
        public int startOffMS = 0;
        public int durationMS = 0;

        public LightPattern(int argb, int startOffMS, int durationMS) {
            this.argb = argb;
            this.startOffMS = startOffMS;
            this.durationMS = durationMS;
        }
    }

}
