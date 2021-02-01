package fr.licence.clock.alarm;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fr.licence.clock.R;
import fr.licence.clock.music.*;

public class AlarmSelectTrackActivity extends AppCompatActivity {

private String path;
private Music music;
private MusicAdapter musicAdapter;
private List<Music> musiclist;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_select_track);

    final ListView listView = findViewById(R.id.list_view_select_track);
    musicAdapter = new MusicAdapter(this, musiclist);
        listView.setAdapter(musicAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object m = listView.getItemAtPosition(position);
                Music music = (Music) m;
                Intent intent = new Intent(getApplicationContext(), AlarmEditorActivity.class);
                intent.putExtra("musicIn",(Parcelable)music);
                setResult(Activity.RESULT_OK,intent);
            }
        });
    }
}
