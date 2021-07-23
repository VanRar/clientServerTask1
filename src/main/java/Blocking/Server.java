package Blocking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Сервер запущен");
            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            out.println("Привет, введи номер числа Фибоначи, которое хочешь расчитать");
            String number = in.readLine();
            long result = fibonacci(Long.parseLong(number));
            out.println(result + " вот твой результат\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long fibonacci(long number) {
        if (number == 1) return 0;
        if (number == 2 || number == 3) return 1;
        return fibonacci(number - 1) + fibonacci(number - 2);
    }
}