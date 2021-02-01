package fr.licence.clock.reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import fr.licence.clock.R;

public class ReminderEditorActivity extends AppCompatActivity {

    private Button confirmbutton;
    private EditText title;
    private EditText notes;
    private int position = -1;
    int requestcode;
    Reminder editedReminder = null;
    Reminder r;
    EditText month;
    EditText day;
    EditText hour;
    EditText minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_editor);
        title = findViewById(R.id.editorremindertitle);
        notes = findViewById(R.id.editorremindernotes);

        Intent intentIn = getIntent();
        if (intentIn != null) {
            r = intentIn.getParcelableExtra("reminderOut");
            position = intentIn.getIntExtra("positionOut", -1);
            requestcode = intentIn.getIntExtra("requestcode", 1);
            if (r != null) {
                title.setText(r.getTitle());
                notes.setText(r.getNotes());
                if (requestcode == 2) {
                    editedReminder = r;
                }
            }
        }

        confirmbutton = findViewById(R.id.remindersavebutton);
        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
    }

    public void confirm() {

        if (requestcode == 1) {
            Reminder rx = new Reminder("1", "1500", title.getText().toString(), notes.getText().toString());
            Intent intent = new Intent(this, ReminderActivity.class);
            intent.putExtra("reminderIn", rx);
            intent.putExtra("positionIn", position);
            setResult(Activity.RESULT_OK, intent);
        }
        if (requestcode == 2) {
            editedReminder.setTitle(title.getText().toString());
            editedReminder.setNotes(notes.getText().toString());
            Intent intent = new Intent(this, ReminderActivity.class);
            intent.putExtra("reminderIn", editedReminder);
            intent.putExtra("positionIn", position);
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }
}



