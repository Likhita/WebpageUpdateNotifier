package notifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observer;

public final class Memento implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date mLastCheckedDate;
	private String mUrl;
	private ArrayList<Observer> mlist;
 
    Memento(Date lastCheckedDate,String url,ArrayList<Observer> table) {
		mLastCheckedDate = lastCheckedDate;
		mUrl = url;
		mlist = table;
    }

	Date getSavedLastCheckedDate() {
        return mLastCheckedDate;
    }
    
    String getSavedUrl(){
    	return mUrl;
    }
    
    ArrayList<Observer> getSavedObserverList(){
    	return mlist;
    }
}
