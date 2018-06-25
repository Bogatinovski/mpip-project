package mk.ukim.finki.mpip.helloworld.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

import java.util.function.Consumer;

import mk.ukim.finki.mpip.helloworld.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by ristes on 22.11.17.
 */

public class NotificationUtils {

    public static void makeNotification(Context context,
                                        String channelId,
                                        String title,
                                        String text,
                                        int iconResource,
                                        Intent resultIntent,
                                        int mNotificationId,
                                        Consumer<Builder> builderAdapter) {



        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel channel= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT);
            mNotifyMgr.createNotificationChannel(channel);
        }


        Builder mBuilder = new Builder(context, channelId)
                .setSmallIcon(iconResource)
                .setContentTitle(title)
                .setContentText(text);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        mBuilder.setContentIntent(resultPendingIntent);


        if (builderAdapter != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builderAdapter.accept(mBuilder);
            }
        }
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    public static void cancel(Context context, int notificationId) {

        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.cancel(notificationId);
    }
}
