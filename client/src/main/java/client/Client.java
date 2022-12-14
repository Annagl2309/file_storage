package client;

import common.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.function.Consumer;

public class Client {
    private DataInputStream in;
    private DataOutputStream out;

    private String username;

    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static File file = new File("client/dir/my-file.txt");

    public static void main(String[] args) {
    }

    public static void sendMessage() throws IOException, InterruptedException {
        Message message = new Message("put", file, Files.readAllBytes(file.toPath()));
        new Client("localhost", 9000).send(message, (response) -> {
            System.out.println("response = " + response);
        });
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().equals("send")) {
                new Client("localhost", 9000).send(message, (response) -> {
                    System.out.println("response = " + response);
                });
            }
        }
    }

    public void send(Message message, Consumer<String> callback) throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap client = new Bootstrap();
            client.group(workerGroup);
            client.channel(NioSocketChannel.class);
            client.option(ChannelOption.SO_KEEPALIVE, true);
            client.handler(new ChannelInitializer<>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(
                            new ObjectEncoder(),
                            new LineBasedFrameDecoder(80),
                            new StringDecoder(StandardCharsets.UTF_8),
                            new ClientHandler(message, callback)
                    );
                }
            });
            ChannelFuture future = client.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public String sendAuthMessage(String login, String password) {
        try {
            out.writeUTF(String.format(login, password));
            String response = in.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
            return  e.getMessage();
        }
        return login;
    }

    public static String getFile() {
        return file.getName();
    }

}

