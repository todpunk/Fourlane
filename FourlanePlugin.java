

public interface iFourlanePlugin extends iFourlaneConstants {

	// Need an event queue.  This will require an Event class
	public String getName();
	public String getInfo();
	
	public void OnLoad(ChannelList channels);
	public void OnUnload();
	public void OnReload();

}

