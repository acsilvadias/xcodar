package br.com.hannasocial;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class NotificationUtils {
    public static final String ACTION_DELETE =
            "hannasocial.android.notification.DELETE_NOTIFICATION";
    public static final String ACTION_NOTIFICATION =
            "hannasocial.android.notification.ACTION_NOTIFICATION";

    public static final String CHANNEL_NAME =
            "hannasocial.android.notification.HANNASOCILA_NOTIFICATION";

    public static final String CHANNEL_DESCRIPTION =
            "Projeto de ação social e segurança Open Source Hanna Social";

    private static PendingIntent  CreatePendingIntent(Context ctx, String text , int id){
        Log.i("xcodar", "CreatePendingIntent >>>");
        Intent resultIntent = new Intent(ctx,MainActivity.class);
        resultIntent.putExtra(MainActivity.EXTRA_TEXTO ,text);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        return stackBuilder.getPendingIntent(
                id,PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    public static void createNotificationSimple(Context ctx, String text, String title){
        Log.i("xcodar", "createNotificationSimple >>>");
        PendingIntent resultPendingItent = CreatePendingIntent(ctx,text, 1 );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx, ACTION_NOTIFICATION)
                .setSmallIcon(R.drawable.ic_notificacao)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingItent);

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);
        nm.notify(1,mBuilder.build());
        Log.i("xcodar", "createNotificationSimple <<<");
    };

}
