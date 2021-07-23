package Blocking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static String HOST = "netology.homework";
    public static Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        try (Socket clientService = new Socket(HOST, Server.SERVER_PORT);
             PrintWriter out = new PrintWriter(clientService.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientService.getInputStream()))) {

            String buffer = in.readLine();

            while (!buffer.isEmpty()) {

                System.out.println(buffer);
                out.println(SCANNER.nextLine());
                buffer = in.readLine();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}