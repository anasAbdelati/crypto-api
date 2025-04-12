package org.projetperso.crypto.glue.server;

import jakarta.annotation.PreDestroy;
import org.mockserver.integration.ClientAndServer;

public class ApiMockServer {

    private final int serverPort;
    protected ClientAndServer mockServer;

    ApiMockServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void start(){
        if(mockServer == null || !mockServer.isRunning()){
            mockServer= ClientAndServer.startClientAndServer(serverPort);
        }
        mockServer.reset();
    }

    @PreDestroy
    public void stop(){
        if(mockServer != null){
            mockServer.stop();
        }
    }
}
