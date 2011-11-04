import org.jibble.pircbot.*;

public class FourlaneMain {

	public static void main(String[] args) throws Exception {
		// TODO: Make this have multiple bots, one for each server.
	
		// Now start our bot up.
		Fourlane bot = new Fourlane();

		// Enable debugging output.
		bot.setVerbose(true);

		// Connect to the IRC server.
		bot.connect("irc.esper.net");

		// Join the #todandlorna channel.
		bot.joinChannel("#todandlorna");
	}
}
