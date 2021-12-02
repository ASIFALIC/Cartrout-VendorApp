package com.oliek.cartrout.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.oliek.cartrout.R;
import com.oliek.cartrout.activity.NewOrderActivity;


public class NotificationUtils {

    public Context mContext;
    public static final int ID_SMALL_NOTIFICATION = 235;
    private static final String CHANNEL_ID = "Cartrout_Notification";
    public static final String CHANNEL_NAME = "Cartrout Notification";
    private static final int NOTIFICATION_ID = 200;
    private static final String TAG = "MyFirebaseMsgService";

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
        //Populate activity map

    }

    public void displayNotification(RemoteMessage.Notification RemoteMsgNotification) {


        {
            String message = RemoteMsgNotification.getBody();
            String title = RemoteMsgNotification.getTitle();
            // Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Uri sound = Uri.parse("android.resource://com.oliek.cartrout"+ "/" + R.raw.ring);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            Intent resultIntent=new Intent(mContext, NewOrderActivity.class);
            resultIntent.putExtra("to","orders");
            Bitmap iconBitMap = null;
            final int icon = R.mipmap.bottom_orders;


            PendingIntent resultPendingIntent;
            resultPendingIntent =
                    PendingIntent.getActivity(
                            mContext,
                            ID_SMALL_NOTIFICATION,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );


            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    mContext, CHANNEL_ID).setSmallIcon(icon).setSound(sound);

            Notification notification=null;

            if (iconBitMap == null) {
                //When Inbox Style is applied, user can expand the notification
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.addLine(message);



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.YELLOW);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setVibrationPattern(new long[]{500, 500, 500, 500, 500, 500, 500, 500, 500});
                    mBuilder.setChannelId(CHANNEL_ID);
                    mBuilder.setSound(sound);
                    notificationManager.createNotificationChannel(notificationChannel);

                    notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                            .setAutoCancel(true)
                            .setContentTitle(title)
                            .setContentIntent(resultPendingIntent)
                            .setStyle(inboxStyle)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setColor(mContext.getResources().getColor(R.color.yellow))
                            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                            .setContentText(message)
                            .setSound(sound)
                            .build();
                    assert notificationManager != null;
                    notificationManager.notify(0 /* Request Code */, mBuilder.build());
                }



                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                            .setAutoCancel(true)
                            .setContentTitle(title)
                            .setContentIntent(resultPendingIntent)
                            .setStyle(inboxStyle)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setColor(mContext.getResources().getColor(R.color.yellow))
                            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                            .setContentText(message)
                            .setVibrate(new long[]{500, 500, 500, 500, 500, 500, 500, 500, 500})
                            .setSound(sound)
                            .build();
                    notificationManager.notify(NOTIFICATION_ID, notification);

                } else {
                    notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                            .setAutoCancel(true)
                            .setContentTitle(title)
                            .setContentIntent(resultPendingIntent)
                            .setStyle(inboxStyle)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setColor(mContext.getResources().getColor(R.color.yellow))
                            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                            .setContentText(message)
                            .setVibrate(new long[]{500, 500, 500, 500, 500, 500, 500, 500, 500})
                            .setSound(sound)
                            .build();
                    notificationManager.notify(NOTIFICATION_ID, notification);
                }



            }


        }

    }

    public void displayDataNotification(FirebaseNotificationModel notificationVO, Intent resultIntent) {


        {
            String message = notificationVO.getMessage();
            String title = notificationVO.getTitle();

            Bitmap iconBitMap = null;
            final int icon = R.mipmap.bottom_orders;


            PendingIntent resultPendingIntent;
            resultPendingIntent =
                    PendingIntent.getActivity(
                            mContext,
                            ID_SMALL_NOTIFICATION,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );


            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    mContext, CHANNEL_ID).setSmallIcon(icon);

            Notification notification=null;
            Uri sound = Uri.parse("android.resource://com.oliek.cartrout"+ "/" + R.raw.ring);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            if (iconBitMap == null) {
                //When Inbox Style is applied, user can expand the notification
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.addLine(message);



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.YELLOW);
                    notificationChannel.enableVibration(true);
                    notificationChannel.setSound(sound,att);
                    notificationChannel.setVibrationPattern(new long[]{500, 500, 500, 500, 500, 500, 500, 500, 500});
                    mBuilder.setChannelId(CHANNEL_ID);
                    notificationManager.createNotificationChannel(notificationChannel);

                    notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                            .setAutoCancel(true)
                            .setContentTitle(title)
                            .setContentIntent(resultPendingIntent)
                            .setStyle(inboxStyle)
                            .setSound(sound)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setColor(mContext.getResources().getColor(R.color.yellow))
                            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                            .setContentText(message)
                            .build();
                    assert notificationManager != null;
                    notificationManager.notify(0 /* Request Code */, mBuilder.build());
                }



                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                            .setAutoCancel(true)
                            .setContentTitle(title)
                            .setContentIntent(resultPendingIntent)
                            .setStyle(inboxStyle)
                            .setSound(sound)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setVibrate(new long[]{500, 500, 500, 500, 500, 500, 500, 500, 500})
                            .setColor(mContext.getResources().getColor(R.color.yellow))
                            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                            .setContentText(message)
                            .build();
                    notificationManager.notify(NOTIFICATION_ID, notification);

                } else {
                    notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                            .setAutoCancel(true)
                            .setContentTitle(title)
                            .setContentIntent(resultPendingIntent)
                            .setStyle(inboxStyle)
                            .setSound(sound)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setColor(mContext.getResources().getColor(R.color.yellow))
                            .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                            .setContentText(message)
                            .setVibrate(new long[]{500, 500, 500, 500, 500, 500, 500, 500, 500})
                            .build();
                    notificationManager.notify(NOTIFICATION_ID, notification);
                }



            }


        }

    }




}
