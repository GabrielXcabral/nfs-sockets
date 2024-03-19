package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.example.NFSServer.arquivos;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] tokens = inputLine.split(":");
                String command = tokens[0];

                switch (command) {
                    case "readdir":
                        readdir();
                        break;
                    case "rename":
                        String oldName = tokens[1];
                        String newName = tokens[2];
                        rename(oldName, newName);
                        break;
                    case "create":
                        String filename = tokens[1];
                        create(filename);
                        break;
                    case "remove":
                        String fileToRemove = tokens[1];
                        remove(fileToRemove);
                        break;
                    default:
                        out.println("Comando inválido");
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readdir() {
        StringBuilder fileList = new StringBuilder();
        for (String arquivo : arquivos) {
            fileList.append(arquivo).append("\n");
        }
        out.println(fileList.toString());
    }

    private void rename(String oldName, String newName) {
        if (arquivos.contains(oldName)) {
            arquivos.remove(oldName);
            arquivos.add(newName);
        }
    }
    
    private void create(String filename) {
        arquivos.add(filename);
    }

    private void remove(String filename) {
        arquivos.remove(filename);
    }
}
