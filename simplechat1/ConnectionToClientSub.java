import java.io.IOException;
import java.net.Socket;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * 
 * @author aleksandar.stavreski
 *
 *         Subclass implementation of <code>ConnectionToClient</code> class.
 */
public class ConnectionToClientSub extends ConnectionToClient {

	ThreadGroup group;
	Socket clientSocket;
	AbstractServer server;

	public ConnectionToClientSub(ThreadGroup group, Socket clientSocket, AbstractServer server) throws IOException {
		super(group, clientSocket, server);
	}

	protected void handleMessageFromClient(Object msg, ConnectionToClientSub client) {
		System.out.println("from SubClient factory!");
	}
}
