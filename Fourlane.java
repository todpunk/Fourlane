import org.jibble.pircbot.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.lang.*;


public class Fourlane extends PircBot implements FourlaneConstants {

	private ArrayList<FourlanePlugin> pluginsLoaded = new ArrayList<FourlanePlugin>();
	private HashMap<Integer, ArrayList<FourlanePlugin>> eventPluginsRegistered = new HashMap<Integer, ArrayList<FourlanePlugin>>();
	// TODO: Need a list for all event types for registration.
	// TODO: Need to make event handlers and event registration functions.
	
	public Fourlane() {
		this.setName("Fourlane");
	}
	
	public Fourlane(String name) {
		this.setName(name);
	}
	
	public void LoadPlugins() {
		File pluginDir = new File("plugins\\");
		File files[] = pluginDir.listFiles();
		System.out.println("Loading stuff");
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
		FourlaneEvent e = new FourlaneEvent();
		e.eventType = EVT_MESSAGE;
		e.channel = channel;
		e.sender = sender;
		e.login = login;
		e.hostname = hostname;
		e.message = message;
		sendEvent(e);
	}
	
	public void inCTCP(String sourceNick, String sourceLogin, String sourceHostname, String target) {
		if (target == this.getName()){
			
		}
	}
	
	public void inPing(String sourceNick, String sourceLogin, String sourceHostname, String target, String pingValue) {
			FourlaneEvent e = new FourlaneEvent();
			e.eventType = EVT_PING;
			e.value = Integer.parseInt(pingValue);
			e.target = target;
			e.hostname = sourceHostname;
			e.login = sourceLogin;
			e.sender = sourceNick;
			sendEvent(e);
	}
	
	public void inNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) { 
			FourlaneEvent e = new FourlaneEvent();
			e.eventType = EVT_NOTICE;
			e.target = target;
			e.hostname = sourceHostname;
			e.login = sourceLogin;
			e.sender = sourceNick;
			e.message = notice;
			sendEvent(e);
	}
	
	// Functions where we do something, like from plugins.
	public void outMessage(String target, String message) {
		sendMessage(target, message);
	}
	
	public void outCTCP(String target, String command) {
		sendCTCPCommand(target, command);
	}
	
	
	
	// Functions to register for events.
	private void sendEvent(FourlaneEvent e) {
		if (eventPluginsRegistered.containsKey(e.eventType)) {
			Iterator iter = eventPluginsRegistered.get(e.eventType).listIterator();
			while(iter.hasNext()) {
				FourlanePlugin plug = (FourlanePlugin)iter.next();
				plug.ReceiveEvent(e);
			}
		}
	}
	
	private boolean registerEvent(int event, FourlanePlugin plugin) {
		if (!eventPluginsRegistered.containsKey(event)) {
			eventPluginsRegistered.put(event, new ArrayList<FourlanePlugin>());
			eventPluginsRegistered.get(event).add(plugin);
		}
		else if (!eventPluginsRegistered.get(event).contains(plugin)) {
			eventPluginsRegistered.get(event).add(plugin);
		}
		return true;
	}
	
	private boolean unregisterEvent(int event, FourlanePlugin plugin) {
		if (eventPluginsRegistered.containsKey(event)) {
			ArrayList<FourlanePlugin> list = eventPluginsRegistered.get(event);
			while (list.contains(plugin)){
				list.remove(plugin);
			}
		}
		return true;
	}

	public boolean registerMessage(FourlanePlugin plugin) {
		return registerEvent(EVT_MESSAGE, plugin);
	}
	public boolean unregisterMessage(FourlanePlugin plugin) {
		return unregisterEvent(EVT_MESSAGE, plugin);
	}

	public boolean registerPing(FourlanePlugin plugin) {
		return registerEvent(EVT_PING, plugin);
	}
	public boolean unregisterPing(FourlanePlugin plugin) {
		return unregisterEvent(EVT_PING, plugin);
	}

	public boolean registerNotice(FourlanePlugin plugin) {
		return registerEvent(EVT_NOTICE, plugin);
	}
	public boolean unregisterNotice(FourlanePlugin plugin) {
		return unregisterEvent(EVT_NOTICE, plugin);
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
	
	public void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice) {
		inNotice(sourceNick, sourceLogin, sourceHostname, target, notice);
	}
	
	
	
	public void onDeVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
	
	}
	
	
}
