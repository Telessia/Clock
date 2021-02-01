package fr.licence.clock.main;

import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class ClockThread extends Thread implements Runnable {

    /**
     * <b>ihour</b> ImageView de l'aiguille des heures
     */
    ImageView ihour;
    /**
     * <b>iminute</b> ImageView de l'aiguille des minutes
     */
    ImageView iminute;
    /**
     * <b>isecond</b> ImageView de l'aiguille des secondes
     */
    ImageView isecond;
    private LocalDateTime currentTime;

    @RequiresApi(api = Build.VERSION_CODES.O)

    /**bh
     * Constructeur 1 de la classe ClockThread
     * @param ihour ImageView de l'aiguille des heures
     * @param iminute ImageView de l'aiguille des minutes
     * @param isecond Imageview de l'aiguille des secondes
     */
    public ClockThread(ImageView ihour, ImageView iminute, ImageView isecond) {
        this.ihour = ihour;//aiguille heures
        this.iminute = iminute;//aiguille minutes
        this.isecond = isecond;//aiguille secondes
        currentTime = LocalDateTime.now();//recupere le temps*/
        //place l'aiguille des heures en fonction du current time, heures*30 degrés du cercle
        ihour.setRotation((currentTime.getHour()) * 30);
        //place l'aiguille des minutes en fonction du current time, minutes*6 degrés du cercle
        iminute.setRotation(currentTime.getMinute() * 6);
        //place l'aiguille des secondes en fonction du current time, secondes*6 degrés du cercle
        isecond.setRotation(currentTime.getSecond() * 6);
    }

    /**
     * Lance le Thread qui déplace les aiguilles
     * @exception InterruptedException leve cette exception si le thread plante
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void run() {
        /*
         * Fait patienter le thread de 1000 millis (1 seconde) puis fait avancer la troteuse
         * (bouclé tant que l'appli est en premier plan)
         */
        int seconddelay = 60-currentTime.getSecond();
        int minutedelay = 60-currentTime.getMinute();
        int secondcount = 0;
        int minutecount = 0;

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //La trotteuse avance de 6 degrés à chaque itération (rappel : 1000ms)
            isecond.setRotation(isecond.getRotation() + 6);
            secondcount++;
            if(secondcount == seconddelay){
                iminute.setRotation(iminute.getRotation() + 6);
                seconddelay = 60;
                secondcount = 0;
                minutecount++;
            }
            if(minutecount == minutedelay){
                ihour.setRotation(ihour.getRotation() +30);
                minutedelay = 60;
                minutecount = 0;
            }
        }
    }
}

