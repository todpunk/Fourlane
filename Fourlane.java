import org.jibble.pircbot.*;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;


public class Fourlane extends PircBot {

	private List<UUID> pluginsLoaded = new ArrayList<UUID>();

	public Fourlane() {
		this.setName("Fourlane");
	}
	
	public Fourlane(String name) {
		this.setName(name);
	}
	
	// This should be the set of functions we pump to the lists.
	public void inMessage(String channel, String sender, String login, String hostname, String message) {
	
	}
	
	public void inCTCP(String sourceNick, String sourceLogin, String sourceHostname, String target) {
		if (target == this.getName()){
			
		}
	}
	
	public void inPing(String sourceNick, String sourceLogin, String sourceHostname, String target, String pingValue) {
		if (target == this.getName()){
			
		}
	}
	
	// Functions where we do something, like from plugins.
	public void outMessage(String target, String message) {
		sendMessage(target, message);
	}
	
	
	
	// Events we subscribe to and will pass to plugins.
	// These are only kept separate to keep things organized better for plugin events.
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
		inMessage(channel, sender, login, hostname, message);
    }
	
	public void onConnect() {
	
	}
	
	// CTCP Ping
	public void onPing(String sourceNick, String sourceLogin, String sourceHostname, String target, String pingValue) {
		inPing(sourceNick, sourceLogin, sourceHostname, target, pingValue);
	}
	
	// Other CTCP related items
	public void onFinger(String sourceNick, String sourceLogin, String sourceHostname, String target) {
		inCTCP(sourceNick, sourceLogin, sourceHostname, target);
	}
	
	public void onTime(String sourceNick, String sourceLogin, String sourceHostname, String target) {
		inCTCP(sourceNick, sourceLogin, sourceHostname, target);
	}

	public void onVersion(String sourceNick, String sourceLogin, String sourceHostname, String target) {
		inCTCP(sourceNick, sourceLogin, sourceHostname, target);
	}


	
	public void onDeVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
	
	}
	
	
}
