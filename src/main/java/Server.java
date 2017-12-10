import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.Handler;


import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] argv) throws Exception {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(new InetSocketAddress("127.0.0.1", 8080));
        ResourceHandler resourceHandler= new ResourceHandler();
        resourceHandler.setResourceBase("folderstatic");
        resourceHandler.setDirectoriesListed(true);
        ContextHandler contextHandler= new ContextHandler("/jcg");
        contextHandler.setHandler(resourceHandler);

        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.setHandlers(new Handler[] {new HandleSearch(), resourceHandler});

        server.setHandler(handlerCollection);
//        server.setHandler(new HandleSearch());

        server.start();
        server.join();
    }
}
