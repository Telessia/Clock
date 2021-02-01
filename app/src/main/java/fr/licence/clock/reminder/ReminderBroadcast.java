package fr.licence.clock.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderBroadcast extends BroadcastReceiver {

    public ReminderBroadcast() {
    }

    /**Reçoit la notification du système (lorsque le Reminder doit se dérouler et réalise
     * une action en conséquence (ici lance ReminderService grace à l'intent**/

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentService = new Intent(context, ReminderService.class);
        context.startService(intentService);
    }
}
