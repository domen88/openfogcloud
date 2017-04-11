package it.unibo.scotece.domenico.openfog.services.impl;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import it.unibo.scotece.domenico.openfog.services.DockerConnect;

import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DockerConnectImpl implements DockerConnect {

    private DockerClient docker;
    private List<DockerClient> cachedDocker = new ArrayList<>(1);

    public DockerConnectImpl() throws DockerCertificateException {
        this.docker = connect();
        this.cachedDocker.add(this.docker);
    }

    @Override
    public DockerClient connect() throws DockerCertificateException {
        final DockerClient docker = DefaultDockerClient.builder()
            .uri(URI.create("https://10.0.0.7:2376"))
            .dockerCertificates(new DockerCertificates(Paths.get("/home/dscotece/.docker/machine/certs")))
            .build();
        return docker;

    }

    public DockerClient getConnection() throws DockerCertificateException {
        if (this.cachedDocker.isEmpty()){
            return connect();
        } else {
            return this.cachedDocker.get(0);
        }
    }
}
