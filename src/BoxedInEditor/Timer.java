/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxedInRunner;

import java.util.ArrayList;

/**
 *
 * @author azh5442
 */
public class Timer implements Runnable {
    private static Timer timer = null;
    private ArrayList<TimerObserver> observerList = new ArrayList();     // list to store observers
    private int secondsLeft = 0;        // counter for seconds remaining
    private boolean isRunning = false;
    private boolean infinite = false;
    
    private Timer(){
        // for singleton pattern
    }
    
    public static Timer getInstance(){
        if(timer == null){
            timer = new Timer();
        }
        return timer;
    }
    
    public void run(){
        while(true){
            if(isRunning){
                try {
                    Thread.sleep(1000); // sleep for 1 second
                    if(!infinite){
                        secondsLeft--;      // subtract 1 from seconds counter
                    }
                    updateObservers();
                }catch (Exception ex){
                    System.err.println(ex);
                }
            }
        }
    }
    
    public void startTimer(){
        isRunning = true;
    }
    
    public void stopTimer(){
        isRunning = false;
    }
    
    // this method should be called before starting a level and starting the timer
    public void setTimer(int seconds){
        if(seconds == 0){
            infinite = true;
            secondsLeft = 600;      // just sets the time to 10 minutes so there is something to display
        }else{
            secondsLeft = seconds;
        }
    }
    
    public void subscribe(TimerObserver o){
        // adds this observer to the list for updating
        observerList.add(o);
        //o.setObserverID(observerList.lastIndexOf(o));
    }
   
    
    public void unsubscribe(TimerObserver o){
        // removes this observer from the list for updating
        //observerList.remove(o.getObserverID());
    }
    
    private void updateObservers(){
        // loop through arraylist and update all observers with new price
        for (TimerObserver observer : observerList){
            observer.updateTime(getSeconds());
        } 
    }
    
    private int getSeconds(){
        return secondsLeft;
    }
}
