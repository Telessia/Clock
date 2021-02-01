package fr.licence.clock.reminder;

import android.os.Parcel;
import android.os.Parcelable;

public class Reminder implements Parcelable{
    private static int incID = 1;
    private String ID;
    private String timeToTrigger;
    private String title;
    private String notes;

    public Reminder(String ID, String timeToTrigger, String title, String notes) {
        this.ID="reminder"+incID;
        this.timeToTrigger = timeToTrigger;
        this.title = title;
        this.notes = notes;
        incID++;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(ID);
        out.writeString(timeToTrigger);
        out.writeString(title);
        out.writeString(notes);
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>(){

        public Reminder createFromParcel(Parcel in) {

            return new Reminder(in);
        }

    public Reminder[] newArray(int size) {

            return new Reminder[size];
    }
};

private Reminder(Parcel in) {
        ID = in.readString();
        timeToTrigger = in.readString();
        title = in.readString();
        notes = in.readString();
}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTimeToTrigger() {
        return timeToTrigger;
    }

    public void setTimeToTrigger(String timeToTrigger) {
        this.timeToTrigger = timeToTrigger;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' + ", notes='" + notes;
    }

}