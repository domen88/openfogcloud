package it.unibo.scotece.domenico.openfog.services;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerCreation;

public interface HandoffContainer {

    void retrieveImage(DockerClient docker,String image) throws DockerException, InterruptedException;
    ContainerCreation startContainer(DockerClient docker, String image, String portExposed, String portMapping) throws DockerException, InterruptedException;

}
