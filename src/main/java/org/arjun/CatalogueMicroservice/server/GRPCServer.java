package org.arjun.CatalogueMicroservice.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.arjun.CatalogueMicroservice.service.ItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class GRPCServer {

  private static final Logger logger = LogManager.getLogger(GRPCServer.class);

  public static void main(String[] args) throws IOException, InterruptedException {

    Server server = ServerBuilder.forPort(9876).addService(new ItemService()).build();
    server.start();
    logger.info("Server started at port " + server.getPort());
    server.awaitTermination();
  }
}
