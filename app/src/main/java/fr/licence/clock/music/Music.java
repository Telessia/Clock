package fr.licence.clock.music;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Music {

    private String title;
    private String length;
    private String path;

    public Music(String path,String title,String length){
        this.path=path; //Chemin d'accès au fichier audio
        this.title=title;
        this.length=length; //durée
    }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    /**Transforme le temps de lecture (long) en format Date pour l'affichage**/
    public String lengthToDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Integer.parseInt(length));
        return formatter.format(calendar.getTime());
    }
    /**Transforme le temps de lecture (long) en format Date pour l'affichage POUR UN USAGE EN STATIC**/
    public static String lengthToDate(String xlength){
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Integer.parseInt(xlength));
        return formatter.format(calendar.getTime());
    }
}
