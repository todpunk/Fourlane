import org.jibble.pircbot.*;
import java.util.UUID;
import java.util.List;
import java.util.HashMap;


// This is an example plugin, meant to show all aspects of a basic Fourlane plugin.
// Remember that all FourlaneConstants are available in plugins.
public class PingExample implements FourlanePlugin {

	private String name = "PingExample";
	private String info = "The example Fourlane plugin!";
	private Queue eventQueue = new Queue();
	private static UUID pluginID = null;
	private int runMe = 0;
	private Fourlane parentBot = null;
	private HashMap<String,String> currentPingers = new HashMap<String,String>(4);
	
	public PingExample() {
	
	}
	
	public String getPluginName() { return name; }
	public String getPluginInfo() { return info; }
	
	public void ReceiveEvent(FourlaneEvent e) {
		eventQueue.add(e);
	}
	
	public int GetQueueItemCount(){
		return eventQueue.size();
	}

	// TODO: Register for events somehow.
	public void OnLoad(Fourlane parent) {
		if (pluginID == null) {
			pluginID = new UUID();
			eventQueue = new Queue();
			parentBot = parent;
		}
	}
	
	public void OnUnload() {
		pluginID = null;
		eventQueue = new Queue();
	}
	
	public void OnReload() {
		eventQueue = new Queue();
		pluginID = new UUID();
	}
	
	public void run() {
		runMe = 1;
		while (runMe == 1) {
			if (eventQueue.hasNext()) {
				FourlaneEvent e = (FourlaneEvent)eventQueue.next();
				if (e.eventType == EVT_MESSAGE) {
					if (e.message.StartsWith("!ping")) {
						long epoch = System.currentTimeMillis()/1000;
						parentBot.outCTCP(e.sender, "PING " + String.valueOf(epoch));
						currentPingers.put(e.sender, new String(epoch) + "TnL~" + e.channel);
					}
				}
				else if (e.eventType == EVT_PING) {
					if (currentPingers.containsKey(e.sender)) {
						String stored = currentPingers.remove(e.sender);
						long stamp = long.parseLong(stored.split("TnL~")[0]);
						long now = System.currentTimeMillis()/1000;
						String seconds = String.valueOf((now - stamp)/1000);
						parentBot.outMessage(stored.split("TnL~")[1], e.sender + ": Pong! in " + "seconds");
					}
				}
			}
		}
	}
	
	public void Stop() {
		runMe = 0;
	}
}

