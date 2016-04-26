package notifier;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class Display implements Observer,Serializable {

	private static final long serialVersionUID = 1L;
	private Object dateInDisplay;
	private Observable aSubject;
	boolean checkNotification;
    
    Display(){
    	checkNotification = false;
    }

    Object getdateInDisplay(){
    	return dateInDisplay;
    }
  
    boolean checkNotification(){
    	return checkNotification;
    }
    
    //updates the date and displays the output on the console
    public void update(Observable subject,Object msg){
    	aSubject= subject;
		dateInDisplay = ((WebpageUpdateNotifier) subject).getCurrentModifiedDate();
		checkNotification = true;
		display();
		
	}
    
    void display(){
    	System.out.println("Website has been last updated on :" + dateInDisplay.toString());
    }
    
}
