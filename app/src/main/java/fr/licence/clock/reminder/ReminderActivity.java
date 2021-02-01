package fr.licence.clock.reminder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.licence.clock.R;

public class ReminderActivity extends AppCompatActivity {

    private Button buttonadd;
    private List<Reminder> remindlist;
    private ReminderAdapter remindAdapter;
    private final int REMINDER_EDITOR_ADD = 1;
    private final int REMINDER_EDITOR_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        buttonadd = findViewById(R.id.button_add_reminder);
        buttonadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addReminder();
            }

        });
        try {
            remindlist = load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ListView listView = findViewById(R.id.list_view_reminder);
        remindAdapter = new ReminderAdapter(this, remindlist);
        listView.setAdapter(remindAdapter);
        remindAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object r = listView.getItemAtPosition(position);
                Reminder remind = (Reminder) r;
                editReminder(remind, position);
            }
        });
    }


    /**Recupère les données depuis ReminderEditorActivity à sa fermeture**/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REMINDER_EDITOR_ADD): {
                if (resultCode == Activity.RESULT_OK) {
                    Reminder r = data.getParcelableExtra("reminderIn");
                    remindlist.add(r);
                    remindAdapter.notifyDataSetChanged();
                    save(this, remindlist);
                    break;
                }
            }
            case (REMINDER_EDITOR_EDIT): {
                if (resultCode == Activity.RESULT_OK) {
                    Reminder r = data.getParcelableExtra("reminderIn");
                    int position = data.getIntExtra("positionIn", -1);
                    remindlist.remove(position);
                    remindlist.add(position, r);
                    remindAdapter.notifyDataSetChanged();
                    save(getApplicationContext(),remindlist);
                    break;
                }
            }
        }
    }

    public void addReminder() {
        Intent intentOut = new Intent(this, ReminderEditorActivity.class);
        intentOut.putExtra("requestcode", REMINDER_EDITOR_ADD);
        startActivityForResult(intentOut, REMINDER_EDITOR_ADD);
    }

    public void editReminder(Reminder r, int position) {
        Intent intentOut = new Intent(this, ReminderEditorActivity.class);
        intentOut.putExtra("reminderOut", r);
        intentOut.putExtra("positionOut", position);
        intentOut.putExtra("requestcode", REMINDER_EDITOR_EDIT);
        startActivityForResult(intentOut, REMINDER_EDITOR_EDIT);
    }


    private List<Reminder> load() throws IOException {
        File folder = getFilesDir();
        Log.i("PATH", folder.toString());
        List<Reminder> loadedreminders = new ArrayList<>();
        File[] arrayFiles = folder.listFiles();
        if (arrayFiles.length == 0) {
            return new ArrayList<Reminder>();
        }
        FileInputStream fis=null;
        String title = null;
        String notes = null;
        String ID = null;
        String timeToTrigger = null;
        for (int i = 0; i < arrayFiles.length; i++) {
            if(arrayFiles[i].getName().contains("reminder")){
                fis = openFileInput(arrayFiles[i].getName());
                byte[] buffer = new byte[1024];
                StringBuilder content = new StringBuilder();
                while ((fis.read(buffer)) != -1) {
                    content.append(new String(buffer));
                }
                String Separator1 = "^";
                String Separator2 ="°";
                String[] splited;
                String[] splited2;
                splited = content.toString().split(Separator1);
                for(int j=0;j<splited.length;j++){
                    splited2 = splited[j].split(Separator2);
                    ID = splited2[0];
                    timeToTrigger = splited2[1];
                    title = splited2[2];
                    notes = splited2[3];
                    loadedreminders.add(new Reminder(ID, timeToTrigger, title, notes));
                }
            }
        }
        fis.close();
        return loadedreminders;
    }

    private void save(Context context,List<Reminder> r) {

        FileOutputStream fos = null;
        try {
            for(int i=0;i<r.size();i++) {
                fos = openFileOutput(r.get(i).getID() + ".bin", Context.MODE_PRIVATE);
                Log.i("INFOS REMINDER", r.get(i).getID() + "   " + r.get(i).getTimeToTrigger() + "   " + r.get(i).getTitle() + "   " + r.get(i).getNotes());
                fos.write(r.get(i).getID().getBytes());
                fos.write("°".getBytes());
                fos.write(r.get(i).getTimeToTrigger().getBytes());
                fos.write("°".getBytes());
                fos.write(r.get(i).getTitle().getBytes());
                fos.write("°".getBytes());
                fos.write(r.get(i).getNotes().getBytes());
                fos.write("°".getBytes());
                fos.write("^".getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


