package com.oliek.cartrout.Firebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oliek.cartrout.activity.NewOrderActivity;
import com.oliek.cartrout.activity.NotificationActivity;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.preference.PreferenceService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;



public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static final String MyPref="info";
    SharedPreferences s;
    SharedPreferences.Editor ed;
    String is_Login;


    private static final String TITLE = "title";
    private static final String EMPTY = "";
    private static final String MESSAGE = "message";
    private static final String IMAGE = "image";
    private static final String ACTION = "action";
    private static final String DATA = "data";
    private static final String ACTION_DESTINATION = "action_destination";

    public MyFirebaseMessagingService() {

        super();
        FirebaseMessaging.getInstance().subscribeToTopic("Cartrout_Notification");

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        UserModel user = PreferenceService.getInstance(this).getUser();
        if(user !=null)
        {
            Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {

                Log.e(TAG, "Message data payload: " + remoteMessage.getData());
                Map<String, String> data = remoteMessage.getData();
                handleData(data);


                //sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
         }
         else  if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.displayNotification(remoteMessage.getNotification());

            }

        }
    }

    @Override
    public void onNewToken(String token) {


        s=getSharedPreferences(MyPref, MODE_PRIVATE);
        ed=s.edit();
        ed.putString("token", token);
        ed.commit();


        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        Log.e("FCM TOKEN", token);
        //sendRegistrationToServer(token);


    }


    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), NewOrderActivity.class);

            //if there is no image
            if(imageUrl.equals("null")){
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            }else{

                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void handleData(Map<String, String> data) {



        String title = data.get(TITLE);
        String message = data.get(MESSAGE);
        String iconUrl = data.get(IMAGE);

        String action = data.get(ACTION);
        String actionDestination = data.get(ACTION_DESTINATION);
        FirebaseNotificationModel notificationVO = new FirebaseNotificationModel();
        notificationVO.setTitle(title);
        notificationVO.setMessage(message);
        notificationVO.setIconUrl(iconUrl);
        notificationVO.setAction(action);
        notificationVO.setActionDestination(actionDestination);




        Intent resultIntent;


        if(action!=null)
        {
            Log.e("action",action);
            if(action.equals("orders"))
            {
                resultIntent = new Intent(getApplicationContext(), NewOrderActivity.class);
                resultIntent.putExtra("to","orders");
                resultIntent.putExtra("res_id","");
            }else {
                resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);
                resultIntent.putExtra("to","");
                resultIntent.putExtra("res_id","");

            }
        }
        else {
            resultIntent = new Intent(getApplicationContext(), NewOrderActivity.class);
            resultIntent.putExtra("to","");
            resultIntent.putExtra("res_id","");


        }

        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.displayDataNotification(notificationVO, resultIntent);
        // notificationUtils.playNotificationSound();

    }
}

