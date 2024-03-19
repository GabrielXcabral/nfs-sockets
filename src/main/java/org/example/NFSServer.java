package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NFSServer {
    static List<String> arquivos = new ArrayList<>();

    public static void main(String[] args) {
        arquivos.add("arquivo1.txt");
        arquivos.add("arquivo2.txt");
        arquivos.add("arquivo3.txt");

        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor NFS iniciado...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket);

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
