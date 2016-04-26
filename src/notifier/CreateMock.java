package notifier;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import static org.mockito.Mockito.*;

public class CreateMock extends WebpageUpdateNotifier implements Serializable {

	private static final long serialVersionUID = 1L;
	protected URLConnection mockConnect;
	long aTime= 0;
	
	CreateMock(String inputUrl,long time){
		super(inputUrl);
		aTime = time;
	}
	
	//factory method that returns mock URLConnection object
	protected URLConnection createURLConnection(URL url) throws IOException{
		mockConnect = mock(URLConnection.class);
		System.out.println("time :" +aTime);
		when(mockConnect.getLastModified()).thenReturn(aTime);
		return mockConnect;
	}
	
}
