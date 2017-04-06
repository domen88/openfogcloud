package it.unibo.scotece.domenico.openfog;

import static spark.Spark.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificates;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;

import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


public class Programma {

    public static void main(String[] args) throws DockerCertificateException, DockerException, InterruptedException {

        port(8080);

        get("/info", (req, res) ->{

            final DockerClient docker = DefaultDockerClient.builder()
                    .uri(URI.create("https://10.0.0.3:2376"))
                    .dockerCertificates(new DockerCertificates(Paths.get("/home/dscotece/.docker/machine/certs")))
                    .build();

            final Info info = docker.info();
            System.out.println(info);

            // By pulling
            final RegistryAuth registryAuth = RegistryAuth.builder()
                    .email("EMAIL")
                    .username("USER")
                    .password("PWD")
                    .build();
            docker.pull("domen88/hello:latest", registryAuth);

            //Container MAP PORT
            Map<String, List<PortBinding>> portBindings = Maps.newHashMap();
            // you can leave the host IP empty for the PortBinding.of first parameter
            portBindings.put("80/tcp", Lists.newArrayList(PortBinding.of("", "8090")));
            HostConfig hostConfig = HostConfig.builder()
                    .portBindings(portBindings)
                    // other host config here
                    .build();

            //Create Container
            final ContainerCreation container = docker.createContainer(
                    ContainerConfig.builder()
                            .image("domen88/hello")
                            .hostConfig(hostConfig)
                            .build());

            //Start Container
            docker.startContainer(container.id());

            System.out.println(container.toString());
            // Close the docker client
            docker.close();

            return info.toString();
        });

    }
}
