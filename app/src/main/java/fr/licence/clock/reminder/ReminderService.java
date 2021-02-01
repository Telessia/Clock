package fr.licence.clock.reminder;

import android.app.IntentService;
import android.content.Intent;

public class ReminderService extends IntentService {
        private static final int NOTIFICATION_ID = 3;

        public ReminderService() {
            super("ReminderService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
           /* Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle("My Title");
            builder.setContentText("This is the Body");
            builder.setSmallIcon(R.drawable.notif);
            Intent notifyIntent = new Intent(this, ReminderActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //to be able to launch your activity from the notification
            builder.setContentIntent(pendingIntent);
            Notification notificationCompat = builder.build();
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(NOTIFICATION_ID, notificationCompat);*/
        }
    }

