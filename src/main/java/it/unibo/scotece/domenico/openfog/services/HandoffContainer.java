package it.unibo.scotece.domenico.openfog.services;

import com.spotify.docker.client.DockerClient;

public interface HandoffContainer {

    DockerClient connect();
    void retrieveImage(String image);
    void startContainer();

}
