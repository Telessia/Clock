package fr.licence.clock.music;

import java.util.Comparator;
/**Permet de trier les musiques en comparant leur titre une à une, à utiliser avec Collection*/
public class MusicSorter implements Comparator<Music> {
    @Override
    public int compare (Music track1,Music track2){
        return track1.getTitle().compareTo(track2.getTitle());
    }
}
