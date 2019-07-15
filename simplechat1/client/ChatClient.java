// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.server.*;
import ocsf.client.*;
import ocsf.server.AbstractServer;
import common.*;
import java.io.*;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;

	/**
	 * @lex implementing login and password validation when connecting to server
	 */
	public String user;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, String user, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.user = user;
		this.clientUI = clientUI;
		openConnection();
		sendToServer("#login " + user);
		/**
		 * @lex Whenever a client connects to a server, it should automatically send the
		 *      message #login <loginid> to the server
		 */
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {
		// clientUI.display(msg.toString());--@lex we mark the msg with server MSG
		System.out.println("Server MSG> " + msg);

	}

	/**
	 * 
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 * 
	 * @lex Edited method to add command function. If the client input starts with
	 *      "#" then it is considered to be a command and the implementation is
	 *      passed on to the "handleClientCommand()" method.
	 */
	public void handleMessageFromClientUI(String message) {
		try {
			if (message.startsWith("#")) {
				handleClientCommand(message);
			} else {
				sendToServer(message);
				clientUI.display(message);
				/*
				 * @lex the last line takes the input from clientConsole, prints it in the
				 * console then the server returns the same message prefixed as 'Server MSG'
				 */
			}

		} catch (IOException e) {
			clientUI.display("Could not send message to server.  Terminating client.");
			quit();
		}
	}

	/**
	 * @lex Method that handles the commands from the client.
	 */
	private void handleClientCommand(String message) {
		if (message.equals("#login")) {
			System.out.println("attempting to login...");
			if (!this.isConnected()) {
				System.out.println("connecting...");
				try {
					this.openConnection();
					System.out.println("Connection established");
				} catch (IOException e) {
					System.out.println("Error connecting to server.");
					e.printStackTrace();
				}
			} else {
				System.out.println("Already connected to server.");
			}

		} else if (message.equals("#quit")) {
			System.out.println("logging off and exiting!");
			System.exit(0);
		} else if (message.equals("#gethost")) {
			System.out.println("Host: " + getHost());
		} else if (message.equals("#getport")) {
			System.out.println("Port: " + getPort());
		} else if (message.equals("#close")) {
			try {
				System.out.println("closing connection...");//---> @lex throws error/to be resolved!
				this.closeConnection();
			} catch (IOException e) {
				System.out.println("error closing...");
				e.printStackTrace();
			}
		} else if (message.equals("#open")) {
			try {
				System.out.println("opening connection...");
				this.openConnection();
			} catch (IOException e) {
				System.out.println("error opening...");
				e.printStackTrace();
			}
		} else
			System.out.println("unknown command! ");

	}

	/**
	 * @lex This method responds if the server shuts down by printing a message and
	 *      quitting.
	 */
	protected void connectionClosed() {
		System.out.println("The server has shut down!");
		quit();
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class
