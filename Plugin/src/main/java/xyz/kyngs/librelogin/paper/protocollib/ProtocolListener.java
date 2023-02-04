package xyz.kyngs.librelogin.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import xyz.kyngs.librelogin.paper.PaperLibreLogin;
import xyz.kyngs.librelogin.paper.PaperListeners;

public class ProtocolListener extends PacketAdapter {

    private final PaperListeners delegate;
    private final PaperLibreLogin plugin;

    public ProtocolListener(PaperListeners delegate, PaperLibreLogin plugin) {
        super(params()
                .plugin(plugin.getBootstrap())
                .types(PacketType.Login.Client.START, PacketType.Login.Client.ENCRYPTION_BEGIN)
                .optionAsync()
        );
        this.delegate = delegate;
        this.plugin = plugin;

        ProtocolLibrary.getProtocolManager()
                .getAsynchronousManager()
                .registerAsyncHandler(this)
                .start();
    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {
        if (packetEvent.isCancelled()) return;

        delegate.onPacketReceive(packetEvent);
    }


}