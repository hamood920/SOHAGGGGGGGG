package org.drnull.varticspvp.Util;

import java.lang.reflect.Constructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleAPI {
    @SuppressWarnings("deprecation")
    public static void sendTitle(Player player, String title, String subtitle) {
        if (title != null)
            try {
                title = color(title);
                Object packetTitle = getMcClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE")
                        .get(null);
                Object objectTitle = getMcClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", new Class[] { String.class })
                        .invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
                Constructor<?> cTitle = getMcClass("PacketPlayOutTitle").getConstructor(new Class[] {
                        getMcClass("PacketPlayOutTitle").getDeclaredClasses()[0], getMcClass("IChatBaseComponent") });
                sendPacket(player, cTitle.newInstance(new Object[] { packetTitle, objectTitle }));
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchFieldException
                     | NoSuchMethodException | SecurityException | java.lang.reflect.InvocationTargetException ex) {
                try {
                    if (subtitle != null) {
                        player.sendTitle(title, color(subtitle));
                    } else {
                        player.sendTitle(title, color("&7"));
                    }
                } catch (Exception exception) {
                }
                return;
            }
        if (subtitle != null)
            try {
                subtitle = color(subtitle);
                Object packetTitle = getMcClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE")
                        .get(null);
                Object objectTitle = getMcClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", new Class[] { String.class })
                        .invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
                Constructor<?> cTitle = getMcClass("PacketPlayOutTitle").getConstructor(new Class[] {
                        getMcClass("PacketPlayOutTitle").getDeclaredClasses()[0], getMcClass("IChatBaseComponent") });
                sendPacket(player, cTitle.newInstance(new Object[] { packetTitle, objectTitle }));
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchFieldException
                     | NoSuchMethodException | SecurityException | java.lang.reflect.InvocationTargetException ex) {
                try {
                    if (title != null) {
                        player.sendTitle(color(title), subtitle);
                    } else {
                        player.sendTitle(color("&7"), subtitle);
                    }
                } catch (Exception exception) {
                }
            }
    }

    public static void clearTitle(Player player) {
        sendTitle(player, "", "");
    }

    private static Class<?> getMcClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    private static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[] { getMcClass("Packet") })
                    .invoke(playerConnection, new Object[] { packet });
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException
                 | SecurityException | java.lang.reflect.InvocationTargetException illegalAccessException) {
        }
    }

    private static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}