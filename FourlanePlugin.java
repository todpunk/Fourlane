import java.util.UUID;
import java.util.List;

public interface FourlanePlugin extends FourlaneConstants {

	// Need an event queue.  This will require an Event class
	public String getName();
	public String getInfo();
	
	public void OnLoad(UUID id, List channels);
	public void OnUnload();
	public void OnReload();

}

