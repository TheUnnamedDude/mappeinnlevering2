package no.kevin.innlevering2;

import no.kevin.innlevering2.entity.Question;
import no.kevin.innlevering2.net.Client;
import no.kevin.innlevering2.net.PacketDecoder;
import no.kevin.innlevering2.net.ServerPacketHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ConnectionHandler implements Runnable, Status {
    private final InetAddress address;
    private final int port;
    private final ArrayList<Question> questions;
    private final PacketDecoder packetDecoder;

    private boolean running = true;
    private Selector selector;
    private ServerSocketChannel serverChannel;

    private HashMap<SocketChannel, Client> sessions = new HashMap<>();

    public ConnectionHandler(InetAddress address, int port,
                             ArrayList<Question> questions) {
        this.address = address;
        this.port = port;
        this.questions = questions;
        this.packetDecoder = new PacketDecoder(this);
    }

    public void initSelector() throws IOException {
        serverChannel = ServerSocketChannel.open();
        this.selector = SelectorProvider.provider().openSelector();
        serverChannel.configureBlocking(false);

        serverChannel.bind(new InetSocketAddress(address, port));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        new Thread(packetDecoder).start();
    }

    public void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        sessions.put(channel, new Client(channel, channel.getRemoteAddress(), new ServerPacketHandler(questions)));
        System.out.println("ACCEPT");

        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
    }

    public void read(SelectionKey key) throws IOException {

        SocketChannel channel = (SocketChannel) key.channel();

        packetDecoder.decode(channel, sessions.get(channel));
    }

    public void handleKey(SelectionKey key) {
        try {
            if (key.isAcceptable()) {
                accept(key);
            }
            if (key.isReadable()) {
                read(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                key.channel().close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            key.cancel();
        }
    }

    @Override
    public void run() {
        while (running()) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            selectedKeys.stream()
                    .filter(SelectionKey::isValid)
                    .forEach(this::handleKey);
            selectedKeys.clear();
        }
    }

    @Override
    public boolean running() {
        return running;
    }

    @Override
    public void shutdown() {
        running = false;
        try {
            serverChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
