package fr.licence.clock.alarm;

import android.os.Parcel;
import android.os.Parcelable;

public class Alarm implements Parcelable {
    private String title;
    private String musicTitle;
    private int days[];

    public Alarm(String title, String musicTitle, int[] days) {
        this.title = title;
        this.musicTitle = musicTitle;
        this.days = days;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(title);
        out.writeString(musicTitle);
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>(){
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    private Alarm(Parcel in) {
        title = in.readString();
        musicTitle = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    public int[] getDay() {
        return days;
    }

    public void setDays(int[] days) {
        this.days = days;
    }
}
