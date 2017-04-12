package it.unibo.scotece.domenico.openfog.services;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;

public interface DockerConnect {

    DockerClient connect() throws DockerCertificateException;

}
