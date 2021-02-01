package fr.licence.clock.music;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import fr.licence.clock.R;
import fr.licence.clock.reminder.Reminder;

public class MusicAdapter extends BaseAdapter implements Filterable { //Implémente filterable pour pouvoir filtrer la liste grace-
    //- au TextWatcher cf.MusicActivity

    private List<Music> musiclist;
    private LayoutInflater Lif;//Permet de créer des layoiut (du visuel) dynamiquement après le build initial
    private Context context;

    public MusicAdapter(Context xcontext, List<Music> xmusiclist) {
        context = xcontext;//Réfèrence à l'appli MusicActivity dans laquelle la liste sera produite
        musiclist = xmusiclist;
        Lif = LayoutInflater.from(xcontext);
    }

    @Override
    public int getCount() {
        return musiclist.size();
    }

    @Override
    public Object getItem(int position) {
        return musiclist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = Lif.inflate(R.layout.music_cell_layout, null);//Une cellule contenant le visuel-
            //-d'une seule musique
        }
        ((ImageView) convertView.findViewById(R.id.musicicon)).setImageResource(R.drawable.musicico);
        ((TextView) convertView.findViewById(R.id.musictitle)).setText(((Music) getItem(position)).getTitle());
        ((TextView) convertView.findViewById(R.id.musiclength)).setText(((Music) getItem(position)).lengthToDate());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {//Filtre grace aux -
                //- lettres du TextWatcher cf.MusicActivity
                FilterResults results = new FilterResults();
                ArrayList<Music> FilteredMusic = new ArrayList<Music>();
                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < musiclist.size(); i++) {
                    String filteredtitle = musiclist.get(i).getTitle();
                    if (filteredtitle.toLowerCase().startsWith(constraint.toString())) {
                        FilteredMusic.add(musiclist.get(i));
                    }
                }
                results.count = FilteredMusic.size();
                results.values = FilteredMusic;
                return results;
            }
            //Applique le résultat sur la liste
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                musiclist = (List<Music>) results.values;
                notifyDataSetChanged();//Rafraichit la liste de MusicActivity pour afficher-
                //-la nouvelle liste filtrée
            }
        };
        return filter;
    }


}
