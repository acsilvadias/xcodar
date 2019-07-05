package br.com.hannasocial;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class LatestFirebaseMessagingService extends FirebaseMessagingService {
     @Override
    public void onNewToken(String mToken) {
        super.onNewToken(mToken);
        Log.e("xcodar", "Token: "+mToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("xcodar", "Message: "+String.valueOf(remoteMessage.getData()));
        Map<String, String> data = remoteMessage.getData();
        Log.e("xcodar", "title: "+remoteMessage.getNotification().getTitle());
        Log.e("xcodar", "body: "+remoteMessage.getNotification().getBody());

        if (remoteMessage.getNotification() != null){

            NotificationUtils.createNotificationSimple(
                    this,
                    remoteMessage.getNotification().getBody(),
                    remoteMessage.getNotification().getTitle()
            );
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
