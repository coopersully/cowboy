package me.coopersully.cowboy;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMount implements Listener {

    private final Map<UUID, Long> lastMountTime = new HashMap<>();
    private final long MOUNT_COOLDOWN = 30 * 1000;

    @EventHandler
    public void onPlayerInteract(@NotNull PlayerInteractEntityEvent e) {

        Player rider = e.getPlayer();

        if (!(e.getRightClicked() instanceof Player mount)) return;

        if (!rider.hasPermission(Cowboy.permissionPrefix + "mount")) return;

        UUID riderUUID = rider.getUniqueId();
        long currentTime = System.currentTimeMillis();
        long lastMount = lastMountTime.getOrDefault(riderUUID, 0L);

        if (currentTime - lastMount < MOUNT_COOLDOWN) {
            long remainingTime = (lastMount + MOUNT_COOLDOWN - currentTime) / 1000; // convert to seconds
            String message = "You can mount another player in " + remainingTime + " seconds.";
            rider.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
            return;
        }

        mount.addPassenger(rider);
        lastMountTime.put(riderUUID, currentTime);
    }
}

