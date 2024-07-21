package org.drnull.varticspvp.Util;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Actionbar {

    public static void sendActionBar(Player p, String msg) {


        try {
            if (ReflectionUtils.getServerVersion().equalsIgnoreCase("v1_12_R1") ||
                    ReflectionUtils.getServerVersion().equalsIgnoreCase("v1_12_R2")) {
                Object icbc = ReflectionUtils.getNmsClass("ChatComponentText")
                        .getConstructor(new Class[] { String.class })
                        .newInstance(new Object[] { ChatColor.translateAlternateColorCodes('&', msg) });
                Object cmt = ReflectionUtils.getNmsClass("ChatMessageType").getField("GAME_INFO").get(null);
                Object ppoc = ReflectionUtils.getNmsClass("PacketPlayOutChat")
                        .getConstructor(
                                new Class[] { ReflectionUtils.getNmsClass("IChatBaseComponent"),
                                        ReflectionUtils.getNmsClass("ChatMessageType") })
                        .newInstance(new Object[] { icbc, cmt });
                Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);
                Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
                pcon.getClass().getMethod("sendPacket", new Class[] { ReflectionUtils.getNmsClass("Packet") }).invoke(
                        pcon,
                        new Object[] { ppoc });
            } else if (ReflectionUtils.getServerVersion().equalsIgnoreCase("v1_9_R1") ||
                    ReflectionUtils.getServerVersion().equalsIgnoreCase("v1_9_R2") ||
                    ReflectionUtils.getServerVersion().equalsIgnoreCase("v1_10_R1") ||
                    ReflectionUtils.getServerVersion().equalsIgnoreCase("v1_11_R1")) {
                Object icbc = ReflectionUtils.getNmsClass("ChatComponentText")
                        .getConstructor(new Class[] { String.class })
                        .newInstance(new Object[] { ChatColor.translateAlternateColorCodes('&', msg) });
                Object ppoc = ReflectionUtils.getNmsClass("PacketPlayOutChat")
                        .getConstructor(new Class[] { ReflectionUtils.getNmsClass("IChatBaseComponent"), byte.class })
                        .newInstance(new Object[] { icbc, Byte.valueOf((byte) 2) });
                Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);
                Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
                pcon.getClass().getMethod("sendPacket", new Class[] { ReflectionUtils.getNmsClass("Packet") }).invoke(
                        pcon,
                        new Object[] { ppoc });
            } else if (ReflectionUtils.getServerVersion().equalsIgnoreCase("v1_8_R2") ||
                    ReflectionUtils.getServerVersion().equalsIgnoreCase("v1_8_R3")) {
                Object icbc = ReflectionUtils.getNmsClass("IChatBaseComponent$ChatSerializer")
                        .getMethod("a", new Class[] { String.class })
                        .invoke(null, new Object[] { "{'text': '" + msg + "'}" });
                Object ppoc = ReflectionUtils.getNmsClass("PacketPlayOutChat")
                        .getConstructor(new Class[] { ReflectionUtils.getNmsClass("IChatBaseComponent"), byte.class })
                        .newInstance(new Object[] { icbc, Byte.valueOf((byte) 2) });
                Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);
                Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
                pcon.getClass().getMethod("sendPacket", new Class[] { ReflectionUtils.getNmsClass("Packet") }).invoke(
                        pcon,
                        new Object[] { ppoc });
            } else {
                Object icbc = ReflectionUtils.getNmsClass("ChatSerializer").getMethod("a", new Class[] { String.class })
                        .invoke(null,
                                new Object[] { "{'text': '" + msg + "'}" });
                Object ppoc = ReflectionUtils.getNmsClass("PacketPlayOutChat")
                        .getConstructor(new Class[] { ReflectionUtils.getNmsClass("IChatBaseComponent"), byte.class })
                        .newInstance(new Object[] { icbc, Byte.valueOf((byte) 2) });
                Object nmsp = p.getClass().getMethod("getHandle", new Class[0]).invoke(p, new Object[0]);
                Object pcon = nmsp.getClass().getField("playerConnection").get(nmsp);
                pcon.getClass().getMethod("sendPacket", new Class[] { ReflectionUtils.getNmsClass("Packet") }).invoke(
                        pcon,
                        new Object[] { ppoc });
            }
        } catch (IllegalAccessException | IllegalArgumentException | java.lang.reflect.InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
                 | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}