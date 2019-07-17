
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import common.ChatIF;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the server.
	 */
	ChatIF serverUI;

	// Constructors ****************************************************

	/**
	 * @lex Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */

	public EchoServer(int port, ChatIF serverUI) {
		super(port);
		this.serverUI = serverUI;

	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		String message = String.valueOf(msg.toString());
		String[] msgParts = message.split(" ");

		if (msgParts[0].equals("#login") && msgParts[1].equals("alex")) {
			client.setInfo("login", msgParts[1]);
			System.out.println("Message received: " + msg + " from " + client.getInfo("login"));
		} else {
			System.out.println("Message received: " + msg + " from " + client.getInfo("login"));
			this.sendToAllClients(msg);
		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * @lex This method overrides the one in the superclass. Called when client
	 *      disconnects from server.
	 */
	protected void clientConnected(ConnectionToClient client) {
		System.out.println("the client: " + client + " has connected!");
	}

	/**
	 * @lex Method called each time a client disconnects.
	 */
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		System.out.println("the client: " + client + " has disconnected!");
	}
	/**
	 * @lex
	 * Method added to test AbstractFactory in section 6.14
	 */
	public void setConnectionFactory(SpecificConnectionFactory specificConnectionFactory) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @lex Method called on ServerConsole input. It displays the input in the
	 *      server console and sends it to the client's console
	 *
	 * @param message
	 */
	public void handleMessageFromServerConsole(String message) {

		if (message.startsWith("#")) {
			handleServerCommand(message);
		} else {
			serverUI.display(message);
			sendToAllClients(message);
		}
	}

	private void handleServerCommand(String message) {
		if (message.equals("#quit")) {
			System.out.println("stop server and exiting!");
			System.exit(0);
		} else if (message.equals("#getport")) {
			System.out.println("Port: " + getPort());
		} else
			System.out.println("unknown command! ");

	}

	
}
//End of EchoServer class
