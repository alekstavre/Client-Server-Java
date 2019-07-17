/**
 * 
 *@lex
 *An interface to be implemented by app-specific factory.
 */
import java.io.IOException;

import java.net.Socket;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public interface AbstractConnectionFactory {
	/**
	 * @lex
	 * It returns a new connection to client object when called.
	 */
	 abstract ConnectionToClient createConnection(ThreadGroup group, 
		     Socket clientSocket, AbstractServer server) throws IOException;
		}

