package notifier;

import java.net.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.io.*;

public class WebpageUpdateNotifier extends Observable {
	
	private Date lastCheckedDate;
	private String url;
	private ArrayList<Observer> observers;
	protected URLConnection connect;
	protected Date currentModifiedDate;
	
	public WebpageUpdateNotifier(String inputUrl){
 		observers = new ArrayList<Observer>();
		url = inputUrl;
		Calendar cal = GregorianCalendar.getInstance();
	    cal.set(1900 + 112, 00, 01);
	    lastCheckedDate = cal.getTime();
	    System.out.println("lastCheckedDate :"+ lastCheckedDate);
	}
	
	//creates url and url connection objects
	void setInitialData(){	
		try{
			URL address = new URL (url);
			connect = createURLConnection(address);
			long time = connect.getLastModified();
		    currentModifiedDate = new Date(time);
		}
		catch (IOException e){
		
		}
	    System.out.println("currentModifiedDate :"+ currentModifiedDate.toString());
	}

	public void addObserver(Observer o) {
		observers.add(o);
		super.addObserver(o);
	}
	
	public void deleteObserver(Observer o) {
		observers.remove(o);
	}
	
	//factory method returns URLConnection object
	protected URLConnection createURLConnection(URL url) throws IOException{
		return url.openConnection();
	}
	
	
	Date getCurrentModifiedDate(){
		 return currentModifiedDate;
	}
	
	//notifies all the observers observing the subject
	public void notifyObservers() {
		
		 ListIterator<Observer> i = observers.listIterator();
		 while (i.hasNext()) {
	     i.next().update(this,"updated date");
	    }	
	}
	
	//returns the file name in which the memento of the subject has to be stored
	private String getFileName(){
		int firstdot,seconddot;
		firstdot = url.indexOf('.');
		seconddot = url.indexOf('.', firstdot+1);
		return url.substring(firstdot+1, seconddot);	
	}
		
	//Retrieves the lastmodified date of the webpage and 
	//notifies the observers and writes the state of the subject into a file
    void checkUpdated(){
    	long time = connect.getLastModified();
    	currentModifiedDate= new Date(time);
    	if(lastCheckedDate.before(currentModifiedDate)){
    		setChanged();
    		notifyObservers();
    		lastCheckedDate=currentModifiedDate;
    		writeToFile();
    	}
    }
    
    //writes the state of Subject into file
    private void writeToFile(){
    	String path = "C:\\NotifierFiles\\";
		String ext = ".ser";
    	 try{
 			FileOutputStream fout = new FileOutputStream(path+getFileName()+ext);
 			ObjectOutputStream oos = new ObjectOutputStream(fout); 
 			oos.writeObject(saveToMemento());
 			oos.close();
 			System.out.println("file created and saved");
 	 
 		   }catch(Exception ex){
 			   ex.printStackTrace();
 		   }
    }
    
    Memento saveToMemento() {
        System.out.println("Originator: Saving to Memento.");
        return new Memento(lastCheckedDate,url,observers);
    }
 
    void restoreFromMemento(Memento obj) {
    	if(obj instanceof Memento) {
		   Memento memento =(Memento)obj;
		   lastCheckedDate = memento.getSavedLastCheckedDate();
		   url = memento.getSavedUrl();
		   observers = memento.getSavedObserverList();
        
    	}
    }	 

}


