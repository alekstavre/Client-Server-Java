import java.io.BufferedReader;
import java.io.InputStreamReader;

import common.ChatIF;

public class ServerConsole implements ChatIF {

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	/**
	 * The instance of the server that created this ServerConsole.
	 */
	static EchoServer server;

	/**
	 * @lex Constructs an instance of the ServerConsole UI.
	 * @param port The port to listen to.
	 */
	public ServerConsole(int port) {
		server = new EchoServer(port, this);
	}

	// Instance methods ************************************************
	/**
	 * @lex This method waits for input from the console. Once it is received, it
	 *      sends it to the server's message handler.
	 */
	public void accept() {
		try {
			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			String message;

			while (true) {
				message = fromConsole.readLine();
				server.handleMessageFromServerConsole(message);

			}
		} catch (Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}
	}

	@Override
	public void display(String message) {
		System.out.println("SERVER> " + message);

	}

	public static void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		ServerConsole console = new ServerConsole(port);

		try {
			server.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}

		console.accept();
	}
}
