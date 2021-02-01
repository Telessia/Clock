package fr.licence.clock.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.licence.clock.R;

public class ReminderAdapter extends BaseAdapter {

    private List<Reminder> remindList;
    private LayoutInflater Lif;
    private Context context;

    public ReminderAdapter(Context xcontext, List<Reminder> xremindlist){
        context=xcontext;
        remindList=xremindlist;
        Lif = LayoutInflater.from(xcontext);
    }

    @Override
    public int getCount(){
        return remindList.size();
    }

    @Override
    public Object getItem(int position){
        return remindList.get(position);
    }

    @Override
    public  long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView = Lif.inflate(R.layout.reminder_cell_layout, null);
        }
        ((TextView) convertView.findViewById(R.id.remindertitle)).setText(((Reminder)getItem(position)).getTitle());
        ((TextView) convertView.findViewById(R.id.remindernotes)).setText(((Reminder)getItem(position)).getNotes());
        return convertView;
    }
}
