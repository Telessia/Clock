package fr.licence.clock.alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fr.licence.clock.R;

public class AlarmActivity extends AppCompatActivity {

    private Button buttonadd;
    private List<Alarm> alarmList;
    private AlarmAdapter alarmAdapter;
    private final int ALARM_EDITOR_ADD = 1;
    private final int ALARM_EDITOR_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        buttonadd = findViewById(R.id.button_add_alarm);
        buttonadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addAlarm();
            }

        });
        //alarmList =????
        final ListView listView = findViewById(R.id.list_view_alarm);
        alarmAdapter = new AlarmAdapter(this, alarmList);
        listView.setAdapter(alarmAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object xa = listView.getItemAtPosition(position);
                Alarm alarm = (Alarm) xa;
                editAlarm(alarm,position);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (ALARM_EDITOR_ADD) : {
                if (resultCode == Activity.RESULT_OK) {
                    Alarm a = data.getParcelableExtra("alarmIn");
                    alarmList.add(a);
                    alarmAdapter.notifyDataSetChanged();
                    break;
                }
            }
            case (ALARM_EDITOR_EDIT) : {
                if(resultCode == Activity.RESULT_OK) {
                    Alarm a = data.getParcelableExtra("alarmIn");
                    int position = data.getIntExtra("positionIn",-1);
                    alarmList.remove(position);
                    alarmList.add(position,a);
                    alarmAdapter.notifyDataSetChanged();
                    break;
                }
            }
            break;
        }
    }

    public void addAlarm() {
        Intent intentOut = new Intent(this, AlarmEditorActivity.class);
        intentOut.putExtra("requestcode",ALARM_EDITOR_ADD);
        startActivityForResult(intentOut,ALARM_EDITOR_ADD);
    }

    public void editAlarm(Alarm a,int position) {
        Intent intentOut = new Intent(this, AlarmEditorActivity.class);
        intentOut.putExtra("alarmOut",a);
        intentOut.putExtra("positionOut",position);
        intentOut.putExtra("requestcode",ALARM_EDITOR_EDIT);
        startActivityForResult(intentOut,ALARM_EDITOR_EDIT);
    }

    public void dataUpdater(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alarmAdapter.notifyDataSetChanged();
            }
        });
    }


    private void Load(){}

    private void Save(Alarm a){

    }

    private void SaveEdit(Alarm a){

    }

}