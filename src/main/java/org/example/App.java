package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {

// ніби  очікує поки хтось підеднається на цей порт (10000)

        ServerSocket server = new ServerSocket(10000);

        while (true) {
            System.out.println("Waiting for connection....");

            // коли хться підьеднався створюється conection двухсторонне зеднання
            // кожний раз хто заходить відбувається нова итерація циклу


            Socket conection = server.accept();

            System.out.println("Client connected!");

            InputStream is = conection.getInputStream();

            String requestText = readAll(is);

            HttpReqest reqest = HttpReqest.of(requestText);

            System.out.println("requesText = " + requestText);

            System.out.println("reques = " + reqest);

        }


    }

    // метод логіки запису данних
    private static String readAll(InputStream is) throws InterruptedException, IOException {

        Thread.sleep(1000L); // чекаємо секунду

        // починаємо считувати данні

        byte[] buffer = new byte[1024 * 20]; // гадаємо що нас запит не буде більше 20 килобайт

        int len = 0;

        while (is.available() > 0) {
            int read = is.read(buffer, len, is.available()); // записуємо в масив
            len += read;

            Thread.sleep(1000L); // знову чекаємо секунду
        }
        return new String(buffer, 0, len);
    }
}