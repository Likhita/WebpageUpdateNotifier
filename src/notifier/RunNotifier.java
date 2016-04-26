package notifier;


import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observer;

public class RunNotifier {

	public static void main(String[] args) {
		
		File dir = new File("C:\\NotifierFiles\\");
		WebpageUpdateNotifier[] subject= {};
		if(dir.exists()){
			String url;
			Memento memento = null;
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++){
				try{
			  		 FileInputStream fin = new FileInputStream(files[i]);
			  		 ObjectInputStream ois = new ObjectInputStream(fin);
			  		 memento =(Memento) ois.readObject();
			  		 ois.close();
			  	   }catch(Exception ex){
			  		   ex.printStackTrace();
			  	   } 
				 url=memento.getSavedUrl();
				 subject[i]= new WebpageUpdateNotifier(url);
				 subject[i].restoreFromMemento(memento);
				 ArrayList<Observer> observers = memento.getSavedObserverList();
				 ListIterator<Observer> itr = observers.listIterator();
					 while (itr.hasNext()) {
				        subject[i].addObserver(itr.next());
				     }	
			 }
		}
		else{
			WebpageUpdateNotifier notifier= new WebpageUpdateNotifier("http://www.eli.sdsu.edu/");
			notifier.setInitialData();
			Email aMail = new Email();
			Display aDisplay = new Display();
			notifier.addObserver(aMail);
			notifier.addObserver(aDisplay);
			WebpageUpdateNotifier sdsuNotifier= new WebpageUpdateNotifier("http://www.sdsu.edu");
			sdsuNotifier.setInitialData();
			sdsuNotifier.addObserver(aMail);
			sdsuNotifier.addObserver(aDisplay);
		}
		while(true){
			for (int i = 0; i < subject.length; i++){
				subject[i].checkUpdated();
			}
		}
	}
}





