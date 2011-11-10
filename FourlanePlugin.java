import org.jibble.pircbot.*;
import java.util.UUID;
import java.util.List;
import java.lang.Thread;

public abstract class FourlanePlugin extends Thread implements FourlaneConstants {
	
	private String name = "PingExample";
	private String info = "The example Fourlane plugin!";
	private Queue eventQueue = new Queue();

	public abstract String getPluginName();
	public abstract String getPluginInfo();
	
	public abstract void ReceiveEvent(FourlaneEvent e);
	public abstract int GetQueueItemCount();
	
	// Register for events here, setup any environment
	public abstract void OnLoad(Fourlane parentBot);
	
	// Clean up environment, reset Queue. Events do not need to be unregistered.
	public abstract void OnUnload();
	
	// Reset environment, including Queue.  Events should already be registered.
	public abstract void OnReload();
	
	// Start your plugin here.  This is your "Main" function.  Use whatever looping
	// you need to check the event queue for items.  A stop mechanism is needed.
	public abstract void run();
	
	// This will get called to stop a plugin.  Note that this does not clear it,
	// so a plugin can get Run() again without being reloaded.  Do not clear state.
	// You will, however need to return quickly.
	public abstract void Stop();

}

