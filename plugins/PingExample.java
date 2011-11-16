import org.jibble.pircbot.*;
import java.util.UUID;
import java.util.List;
import java.util.HashMap;


// This is an example plugin, meant to show all aspects of a basic Fourlane plugin.
// Remember that all FourlaneConstants are available in plugins.
public class PingExample extends FourlanePlugin {

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
			pluginID = UUID.randomUUID();
			eventQueue = new Queue();
			parentBot = parent;
			parentBot.registerNotice(this);
			parentBot.registerMessage(this);
		}
	}
	
	public void OnUnload() {
		pluginID = null;
		eventQueue = new Queue();
		parentBot.unregisterNotice(this);
		parentBot.unregisterMessage(this);
	}
	
	public void OnReload() {
		eventQueue = new Queue();
		pluginID = UUID.randomUUID();
		parentBot.registerNotice(this);
		parentBot.registerMessage(this);
	}
	
	public void run() {
		runMe = 1;
		System.out.println("\n\nPingExample loaded\n\n");
		while (runMe == 1) {
			if (eventQueue.hasNext()) {
				FourlaneEvent e = (FourlaneEvent)eventQueue.next();
				if (e.eventType == EVT_MESSAGE) {
					if (e.message.startsWith("!ping")) {
						long epoch = System.currentTimeMillis();
						parentBot.outCTCP(e.sender, "PING " + String.valueOf(epoch));
						currentPingers.put(e.sender, String.valueOf(epoch) + "TnL~" + e.channel);
					}
				}
				else if (e.eventType == EVT_NOTICE) {
					if (currentPingers.containsKey(e.sender)) {
						String stored = currentPingers.remove(e.sender);
						long stamp = Long.parseLong(stored.split("TnL~")[0]);
						long now = System.currentTimeMillis();
						String seconds = String.format("%4.4f", (now - stamp)/1000.0);
						parentBot.outMessage(stored.split("TnL~")[1], e.sender + ": Pong! in " + seconds + " seconds");
					}
				}
			}
		}
	}
			// e.eventType = EVT_NOTICE;
			// e.target = target;
			// e.hostname = sourceHostname;
			// e.login = sourceLogin;
			// e.sender = sourceNick;
			// e.message = notice;
			// sendEvent(e);
	
	public void Stop() {
		runMe = 0;
	}
}

