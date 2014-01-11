package Utils;
import org.lwjgl.Sys;

public class Timer {
	private long ms = 0;
	private long initTime = 0;
	private long currentTime;
	
	public Timer(){
		
	}
	
	public Timer start(){
		initTime = (Sys.getTime()*1000)/Sys.getTimerResolution();
		
		return this;
	}
	
	public long getTime(){
		if(initTime !=0)
		return (Sys.getTime()*1000)/Sys.getTimerResolution()-initTime;
		return 0;
	}	
	
}
