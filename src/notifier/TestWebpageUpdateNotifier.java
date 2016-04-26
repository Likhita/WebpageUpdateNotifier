package notifier;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observer;

import org.junit.Test;

public class TestWebpageUpdateNotifier {

	@Test
	public final void testNotifierWhenModified() {
		WebpageUpdateNotifier notifier= new CreateMock("http://www.eli.sdsu.edu/",1334388588599L);
		notifier.setInitialData();
		Email aMail = new Email();
		Display aDisplay = new Display();
		notifier.addObserver(aMail);
		notifier.addObserver(aDisplay);
		notifier.checkUpdated();
		assertEquals(true,aMail.checkNotification());
		assertEquals(true,aDisplay.checkNotification());
		WebpageUpdateNotifier sdsuNotifier= new CreateMock("http://www.sdsu.edu/",1334388588599L);
		sdsuNotifier.setInitialData();
		sdsuNotifier.addObserver(aMail);
		sdsuNotifier.addObserver(aDisplay);
		sdsuNotifier.checkUpdated();
		assertEquals(true,aMail.checkNotification());
		assertEquals(true,aDisplay.checkNotification());
		
		//fail("Observers  not notified"); // TODO
	}

	@Test
	public final void testNotifierWhenNotModified() {
		WebpageUpdateNotifier notifier= new CreateMock("http://www.eli.sdsu.edu/",1222222222222L);
		notifier.setInitialData();
		Email aMail = new Email();
		Display aDisplay = new Display();
		notifier.addObserver(aMail);
		notifier.addObserver(aDisplay);
		notifier.checkUpdated();
		assertEquals(false,aMail.checkNotification());
		assertEquals(false,aDisplay.checkNotification());
		
		//fail("Observers  not notified"); // TODO
	}
	
	@Test
	public final void testNotifierAfterCrash() {
		String url = "";
		WebpageUpdateNotifier oldMonitor = new CreateMock("http://www.eli.sdsu.edu/",1334388588599L);
		
		Memento memento = null;
		WebpageUpdateNotifier[] subject = new WebpageUpdateNotifier[20];
		
		File dir = new File("C:\\NotifierFiles\\");
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
		 subject[i].setInitialData();
		 subject[i].restoreFromMemento(memento); 
		 ArrayList<Observer> observers = memento.getSavedObserverList();
		 ListIterator<Observer> itr = observers.listIterator();
			 while (itr.hasNext()) {
		        subject[i].addObserver(itr.next());
		     }	
		}
		assertEquals(true,oldMonitor.equals(subject[0]));
	
		}
	}
	

