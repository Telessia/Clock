package fr.licence.clock.alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.licence.clock.R;

public class AlarmEditorActivity extends AppCompatActivity {

    private Button confirmbutton;
    private Button selectbutton;
    private EditText title;
    private TextView musictitle;
    private String musicPath;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_editor);
        title = findViewById(R.id.editorremindertitle);
        musictitle = findViewById(R.id.music_alarm);

        Intent intentIn=getIntent();
        if(intentIn!=null) {
            Alarm a = intentIn.getParcelableExtra("alarmOut");
            position = intentIn.getIntExtra("positionOut",-1);
            if (a != null) {
                title.setText(a.getTitle());
            }
        }

        selectbutton = findViewById(R.id.add_track);
        selectbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selectTrack();
            }
         });

        confirmbutton = findViewById(R.id.confirm_button_alarm);
        confirmbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                confirm();
            }
        });
    }

    public void confirm(){
        Alarm a = new Alarm(title.getText().toString(),musictitle.getText().toString(),getArrayDays());
        Intent intent = new Intent(this,AlarmActivity.class);
        intent.putExtra("alarmrIn",a);
        intent.putExtra("positionIn",position);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    public void selectTrack(){

    }

    public int[] getArrayDays(){
        int arrayInt[]=new int[]{};
        CheckBox arrayCheck[]=new CheckBox[]{findViewById(R.id.MON),findViewById(R.id.TUE),findViewById(R.id.WED),
                findViewById(R.id.TUE),findViewById(R.id.FRI),findViewById(R.id.SAT),findViewById(R.id.SUN)};
    for(int i=0;i<7;i++){
        if(arrayCheck[i].isChecked()){
            arrayInt[i]=1;
        }
        }
    return arrayInt;
    }
}
