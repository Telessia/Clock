package fr.licence.clock.music;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import fr.licence.clock.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicActivity extends AppCompatActivity {

    private List<Music> musiclist; //List des futures musiques
    private MusicAdapter musicAdapter; //Permettra d'afficher les musiques dynamiquement
    private MediaPlayer mediaPlayer; //Lecteur de media
    private static final int SPECIAL_PERMISSION_REQUEST = 1;
    private Button buttonplayer;
    private TextView titleplaying;
    private TextView lengthplaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        musiclist = loadMusic();
        /**Tri des musiques par titre**/
        Collections.sort(musiclist,new MusicSorter());

        buttonplayer = findViewById(R.id.music_button_player);
        titleplaying = findViewById(R.id.music_title_playing);
        lengthplaying = findViewById(R.id.music_length_playing);

        titleplaying.invalidate();//Rafraichi le titre de la chanson (affiché en bas de l'ecran)
        lengthplaying.invalidate();//Rafraichi la durée de la chanson (affichée en bas de l'ecran)
        final ListView listView = findViewById(R.id.list_view_music);//Object visuel (liste en somme) qui contiendra et affichera les musiques
        musicAdapter = new MusicAdapter(this, musiclist);
        listView.setAdapter(musicAdapter);
    /**ajout d'une gestion du clic sur les musiques de la liste, pour pouvoir lancer l'audio en un clic**/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);//Permettra de reperer si le lecteur est en pause ou non
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object m = listView.getItemAtPosition(position);
                Music music = (Music) m;
                Log.i("PATHMUSIC",music.getPath());
                if(am.isMusicActive()){
                    mediaPlayer.pause();
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
                titleplaying.setText(music.getTitle());
                lengthplaying.setText(music.lengthToDate());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),Uri.parse(music.getPath()));//Pré-lit en mémoire le fichier
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {//Se déclenche que le fichier est totalement préparé
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.seekTo(0);//Seek au début de la musique
                        buttonplayer.setText("Pause");//Le bouton affiche "Pause" (le lecteur est en marche)
                        buttonplayer.invalidate();//Rafraichit le bouton pour pouvoir afficher le "Pause"
                        mediaPlayer.start();
                    }
                    });
                }
        });
        /**Gestion du bouton Play/Pause**/
        buttonplayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
                if (am.isMusicActive()){
                    buttonplayer.setText("Play");
                    buttonplayer.invalidate();
                    mediaPlayer.pause();
                } else {
                    buttonplayer.setText("Pause");
                    buttonplayer.invalidate();
                    mediaPlayer.start();
                }
            }
        });
        /**Controle des permission pour accèder aux fichiers**/
        if (ContextCompat.checkSelfPermission(MusicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MusicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MusicActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SPECIAL_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(MusicActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SPECIAL_PERMISSION_REQUEST);
            }
        } else {
            musiclist = loadMusic();
        }

        EditText musicFilter = (EditText) findViewById(R.id.music_filter);
        musicFilter.addTextChangedListener(new TextWatcher() {//Permet de filtrer les musiques par titre

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MusicActivity.this.musicAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
        Toast.makeText(this,"Track loaded : "+musiclist.size(),Toast.LENGTH_LONG).show();//affiche le nombre de musique chargées
    }


    public List<Music> loadMusic() {
        ArrayList<Music> loadedMusic = new ArrayList<>();

        /* TEST autre manière
        MediaMetadataRetriever mediaMetadataRetriever= new MediaMetadataRetriever();
        File directory = new File("/sdcard/Music/");
        File files[] = directory.listFiles();
        String musiclength;
        String musictitle;
        for (int i = 0; i < files.length; i++)
        {
        mediaMetadataRetriever.setDataSource(files[i].getPath());
        musictitle = mediaMetadataRetriever.extractMetadata((MediaMetadataRetriever.METADATA_KEY_TITLE));
        musiclength = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        loadedMusic.add(new Music(files[i].getAbsolutePath(),musictitle,musiclength));
        }
        */

        ContentResolver contentResolver = getContentResolver();//Permet d'aller fouiller dans les dossiers
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;//Définit ou chercher
        Cursor musicCursor = contentResolver.query(musicUri, null, null, null, null);//Curseur qui pointera-
        //-les fichiers audio

        if (musicCursor != null && musicCursor.moveToFirst()) {
            do{
                //int musicpath = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.RELATIVE_PATH);
                int musicname = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
                int musictitle = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                int musiclength = musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
                Log.i("MUSIC INFOS:",musicUri.getPath() +"  "+musicCursor.getString(musictitle)+"  "+musicCursor.getString(musiclength));
                loadedMusic.add(new Music("/storage/9016-4EF8/Music/"+musicCursor.getString(musicname), musicCursor.getString(musictitle), musicCursor.getString(musiclength)));
            }while (musicCursor.moveToNext());
        }
            return loadedMusic;
    }

        /** Vérification permission et demande d'accès**/
        @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults){
            switch(requestCode){
                case SPECIAL_PERMISSION_REQUEST: {
                    if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        if (ContextCompat.checkSelfPermission(MusicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                            musiclist = loadMusic();
                        }
                    }else{
                        Toast.makeText(this,"No permission granted",Toast.LENGTH_SHORT).show();
                        finish();
                        }
                    }
                }
            }
}

