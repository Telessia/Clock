package fr.licence.clock.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import fr.licence.clock.R;
import fr.licence.clock.alarm.AlarmActivity;
import fr.licence.clock.music.MusicActivity;
import fr.licence.clock.reminder.ReminderActivity;

/**
 * MainActivity contient l'horloge analogique et un apperçu basique des rappels et notes
 * en cours
 */
public class MainActivity extends AppCompatActivity {
    /**
     * <b>ihour</b> ImageView de l'aiguille des heures
     */
    private ImageView ihour;
    /**
     * <b>iminute</b> ImageView de l'aiguille des minutes
     */
    private ImageView iminute;
    /**
     * <b>isecond</b> ImageView de l'aiguille des secondes
     */
    private ImageView isecond;

    /**
     *  <b>STORAGE_PERMISSION_CODE</b> Code d'accès storage
     */
    private static final int PERMISSION_CODE = 100;

    private Button btnstartreminder;
    private Button btnstartmusic;
    private Button btnstartalarm;
    private ClockThread Tc;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**VERIFICATION PERMISSIONS**/

        String[] PERMISSIONS = { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.VIBRATE,
                Manifest.permission.WAKE_LOCK};
        checkPermission(PERMISSIONS,PERMISSION_CODE,3);



        ihour = findViewById(R.id.hour);
        iminute = findViewById(R.id.minute);
        isecond = findViewById(R.id.second);
        //Thread de l'Horloge analogique
        Tc = new ClockThread(ihour,iminute,isecond);
        Tc.start();

        btnstartalarm = findViewById(R.id.button_Alarm);
        btnstartreminder = findViewById(R.id.button_Reminder);
        btnstartmusic = findViewById(R.id.button_Music);

        btnstartreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReminder(v);
            }
        });
        btnstartmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusic(v);
            }
        });
        btnstartalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm(v);
            }
        });

    }

    protected void onStart () {
        super.onStart();

    }
    protected void onPause() {
        super.onPause();
        Tc.interrupt();
        Tc = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onResume() {
        super.onResume();
        Tc = new ClockThread(ihour,iminute,isecond);
        Tc.start();
    }

    public void checkPermission(String[] permissions, int requestCode, int n) {
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[i])
                    == PackageManager.PERMISSION_DENIED) {
                // Requesting the permission
                ActivityCompat.requestPermissions(MainActivity.this, permissions, requestCode);
                break;
            }
        }
    }

    public void startMusic(View view) {
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }
    public void startReminder(View view) {
        Intent intent = new Intent(this, ReminderActivity.class);
        startActivity(intent);
    }

    public void startAlarm(View view) {
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }

}
