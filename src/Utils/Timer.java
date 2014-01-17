package Utils;
import org.lwjgl.Sys;
/**
 * Class to keep track of the time
 * 
 * @author ZL
 *
 */
public class Timer {
	private long ms = 0;
	private long initTime = 0;
	private long currentTime;
	/**
	 * Create the timer
	 */
	public Timer(){		
	}
	/**
	 * start the timer by recording the current time
	 * @return itself, allwoing you to chain
	 */
	public Timer start(){
		initTime = (Sys.getTime()*1000)/Sys.getTimerResolution();
		
		return this;
	}
	/**
	 * returns the time passed after having started the timer
	 * @return the time as a long
	 */
	public long getTime(){
		if(initTime !=0)
		return (Sys.getTime()*1000)/Sys.getTimerResolution()-initTime;
		return 0;
	}	
	
}
