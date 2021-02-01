package fr.licence.clock.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.licence.clock.R;
import fr.licence.clock.reminder.Reminder;

public class AlarmAdapter extends BaseAdapter {

    private List<Alarm> alarmList;
    private LayoutInflater Lif;
    private Context context;

    public AlarmAdapter(Context xcontext, List<Alarm> xalarmlist){
        context=xcontext;
        alarmList=xalarmlist;
        Lif = LayoutInflater.from(xcontext);
    }

    @Override
    public int getCount(){
        return alarmList.size();
    }

    @Override
    public Object getItem(int position){
        return alarmList.get(position);
    }

    @Override
    public  long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView = Lif.inflate(R.layout.activity_alarm, null);
        }
        ((TextView) convertView.findViewById(R.id.alarm_title)).setText(((Reminder)getItem(position)).getTitle());
        ((TextView) convertView.findViewById(R.id.alarm_music_title)).setText(((Reminder)getItem(position)).getNotes());
        return convertView;
    }
}
