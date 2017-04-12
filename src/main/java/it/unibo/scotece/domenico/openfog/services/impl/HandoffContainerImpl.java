package it.unibo.scotece.domenico.openfog.services.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import it.unibo.scotece.domenico.openfog.config.Config;
import it.unibo.scotece.domenico.openfog.services.HandoffContainer;

import java.util.List;
import java.util.Map;

public class HandoffContainerImpl implements HandoffContainer {

    @Override
    public void retrieveImage(DockerClient docker, String image) throws DockerException, InterruptedException {

        // By pulling
        final RegistryAuth registryAuth = RegistryAuth.builder()
                //.email(Config.email)
                .username(Config.user)
                .password(Config.pwd)
                .serverAddress(Config.server)
                .build();
        docker.pull(image, registryAuth);

    }

    @Override
    public ContainerCreation startContainer(DockerClient docker, String image, String portExposed, String portMapping) throws DockerException, InterruptedException {

        //Create Container
        final ContainerCreation container = docker.createContainer(
                ContainerConfig.builder()
                        .image(image)
                        .hostConfig(containerPortMAP(portExposed, portMapping))
                        .build());

        //Start Container
        docker.startContainer(container.id());
        System.out.println(container);

        return container;

    }

    private HostConfig containerPortMAP(String portExposed, String portMapping){

        //Container MAP PORT
        Map<String, List<PortBinding>> portBindings = Maps.newHashMap();
        // you can leave the host IP empty for the PortBinding.of first parameter
        portBindings.put(portExposed+"/tcp", Lists.newArrayList(PortBinding.of("", portMapping)));
        HostConfig hostConfig = HostConfig.builder()
                .portBindings(portBindings)
                // other host config here
                .build();

        return hostConfig;

    }
}
