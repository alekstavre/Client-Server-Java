import java.io.IOException;
import java.net.Socket;
import ocsf.server.AbstractServer;

/**
 *
 * @author aleksandar.stavreski
 * Specific implementation of the interface which creates a <code> ConnectionToClientSub</code>
 * object.
 * Calling this method is optional.If not called then the default implementation of 
 * <code>ConnectionToClient</code> is applied.
 * 
 */
public class SpecificConnectionFactory implements AbstractConnectionFactory {

	@Override
	public ConnectionToClientSub createConnection(ThreadGroup group, Socket clientSocket, AbstractServer server)
			throws IOException {

		return new ConnectionToClientSub(group, clientSocket, server);
	}

}
