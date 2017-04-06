package it.unibo.scotece.domenico.openfog;

import static spark.Spark.*;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Info;

import java.net.URI;
import java.nio.file.Paths;


public class Programma {

    public static void main(String[] args) throws DockerCertificateException, DockerException, InterruptedException {

        port(8080);

        get("/info", (req, res) ->{

            final DockerClient docker = DefaultDockerClient.builder()
                    .uri(URI.create("https://10.0.0.3:2376"))
                    .dockerCertificates(new DockerCertificates(Paths.get("/home/dscotece/.docker/machine/certs")))
                    .build();

            final Info info = docker.info();
            System.out.print(info);

            // Close the docker client
            docker.close();

            return info.toString();
        });

    }
}
