package it.unibo.scotece.domenico.openfog;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import it.unibo.scotece.domenico.openfog.services.impl.DockerConnectImpl;

public class Programma {

    public static void main(String[] args) throws DockerCertificateException, DockerException, InterruptedException {

        //Connect to the docker into basevm
        DockerConnectImpl dockerConnector = new DockerConnectImpl();

        //Set Java Spark server port
        port(8080);

        get("/info", "application/json", (req, res) -> {

            DockerClient docker = dockerConnector.getConnection();

            final Info info = docker.info();
            System.out.println(info);

            // Close the docker client
            docker.close();

            Gson json = new Gson();
            return json.toJson(info);

        });

    }

}
