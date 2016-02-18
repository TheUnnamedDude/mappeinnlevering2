package no.kevin.innlevering2.net;

import no.kevin.innlevering2.net.packets.*;

import java.util.HashMap;

public class PacketMapping {
    private static PacketSupplier[] ID_TO_PACKET_MAP = new PacketSupplier[7];

    private static HashMap<Class<? extends Packet>, Integer> PACKET_TO_ID_MAP = new HashMap<>();

    private static void registerPacket(int id, PacketSupplier supplier) {
        PACKET_TO_ID_MAP.put(supplier.newPacket().getClass(), id); // Ugly but shouldnt give too much of a cost
        ID_TO_PACKET_MAP[id] = supplier;
    }

    static {
        registerPacket(0, HandshakePacket::new);
        registerPacket(1, QuestionPacket::new);
        registerPacket(2, AnswerPacket::new);
        registerPacket(3, QuestionResultPacket::new);
        registerPacket(4, NextQuestionPacket::new);
        registerPacket(5, EndGameStatsPacket::new);
        registerPacket(6, ErrorPacket::new);
    }

    public static Packet getPacketById(int packetId) {
        if (packetId >= ID_TO_PACKET_MAP.length || packetId < 0)
            return null;
        return ID_TO_PACKET_MAP[packetId].newPacket();
    }

    public static int getMaxPacketId() {
        return ID_TO_PACKET_MAP.length;
    }

    public static int getIdByPacket(Class<? extends Packet> cl) {
        return PACKET_TO_ID_MAP.get(cl);
    }

    private interface PacketSupplier {
        Packet newPacket();
    }
}