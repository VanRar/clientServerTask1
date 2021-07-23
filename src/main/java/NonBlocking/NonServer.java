package NonBlocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NonServer {
    public static void main(String[] args) throws IOException {
        boolean exit = false;
        // Занимаем порт, определяя серверный сокет
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 23334));
        StringBuilder result = new StringBuilder();
        while (!exit) {
            // Ждем подключения клиента и получаем потоки для дальнейшей работы
            try (SocketChannel socketChannel = serverChannel.accept()) {
                // Определяем буфер для получения данных
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    // читаем данные из канала в буфер
                    int bytesCount = socketChannel.read(inputBuffer);
                    // если из потока читать нельзя, перестаем работать с этим клиентом
                    if (bytesCount == -1) break;
                    // получаем переданную от клиента строку в нужной кодировке и очищаем буфер
                    final String msg = new String(inputBuffer.array(), 0, bytesCount,
                            StandardCharsets.UTF_8);
                    inputBuffer.clear();
                    if ("end".equals(msg)) {
                        exit = true;//в действительности наверное так не надо, но в данном случае норм
                        break;
                    }
                    String[] buffer = msg.split(" ");
                    for (String s : buffer) {
                        result.append(s);
                    }
                    System.out.println("Получено сообщение от клиента: " + msg);
                    // отправляем сообщение клиента назад с пометкой ЭХО
                    socketChannel.write(ByteBuffer.wrap(("Эхо: " +
                            result.toString()).getBytes(StandardCharsets.UTF_8)));

                }

            } catch (IOException err) {
                System.out.println(err.getMessage());
            }
        }
    }
}
