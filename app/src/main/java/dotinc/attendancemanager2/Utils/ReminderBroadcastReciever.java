package dotinc.attendancemanager2.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

import dotinc.attendancemanager2.MainActivity;
import dotinc.attendancemanager2.NotificationActivity;
import dotinc.attendancemanager2.R;

/**
 * Created by vellapanti on 12/2/17.
 */

public class ReminderBroadcastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("option_noti","here_broad");
        int notificationId = new Random().nextInt();
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent cancelIntent = new Intent(context,CancelIntent.class);
        cancelIntent.putExtra("notificationId",notificationId);
        PendingIntent dismissIntent = PendingIntent.getActivity(context, notificationId,
                cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("option_not_rem", String.valueOf(notificationId));

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Attendance Manager")
                        .setContentText("Did you mark today's attendance?")
//                        .addAction(R.mipmap.ic_action_checked_done,"Yes",dismissIntent)
//                        .addAction(R.mipmap.ic_action_navigation_cancel,"No",pendingIntent)
                        .setAutoCancel(true);


        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, mBuilder.build());



    }

}
