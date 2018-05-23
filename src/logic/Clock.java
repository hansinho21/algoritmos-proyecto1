package logic;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JLabel;

/**
 *
 * @author hvill
 */
public class Clock implements Runnable {

    String hour;
    String minutes;
    String seconds;
    String ampm;
    Calendar calendar;
    Thread h1;
    JLabel labelHour;

    public Clock(JLabel label) {
        this.labelHour = label;
        h1 = new Thread(this);
        h1.start();
    }

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == h1) {
            calcula();
            labelHour.setText(hour + ":" + minutes + ":" + seconds + " ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void calcula() {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();

        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.HOUR_OF_DAY) == Calendar.HOUR_OF_DAY ? "AM" : "PM";

        int h = calendario.get(Calendar.HOUR_OF_DAY);
        hour = h > 9 ? "" + h : "0" + h;

        minutes = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        seconds = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
    }

    public JLabel getLabelTime() {
        return this.labelHour;
    }

}
