import org.jibble.pircbot.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.lang.*;


public class Fourlane extends PircBot {

	private ArrayList<FourlanePlugin> pluginsLoaded = new ArrayList<FourlanePlugin>();
	// TODO: Need a list for all event types for registration.
	// TODO: Need to make event handlers and event registration functions.
	
	public Fourlane() {
		this.setName("Fourlane");
	}
	
	public Fourlane(String name) {
		this.setName(name);
		this.LoadPlugins();
	}
	
	public void LoadPlugins() {
		File pluginDir = new File("plugins\\");
		File files[] = pluginDir.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().endsWith(".class")){
				try {
					URI uri = files[i].toURI();
					URL url = uri.toURL();
					URL[] urls = new URL[]{url,pluginDir.toURI().toURL()};
					ClassLoader cl = new URLClassLoader(urls);
					System.out.println("class found: " + files[i].getName());
					Class cls = cl.loadClass(files[i].getName().split("\\.")[0]);
					Object o = cls.newInstance();
					if (o instanceof FourlanePlugin){
						((FourlanePlugin)o).OnLoad(this);
						((FourlanePlugin)o).start();
						pluginsLoaded.add(((FourlanePlugin)o));
					}
				}
				catch (MalformedURLException e ) {
				}
				catch (ClassNotFoundException e) {
				}
				catch (InstantiationException e) {
				}
				catch (IllegalAccessException e) {
				}
			}
		}		
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
	
	public void outCTCP(String target, String command) {
		sendCTCPCommand(target, command);
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
