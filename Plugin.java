package org.drnull.varticspvp;

import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    public static String prefix = "&7[&bVarticsPvP&7]&r";

    private static Plugin instance;
    private Combat combat;

    @Override
    public void onEnable() {
        instance = this;
        combat = new Combat();
        getServer().getPluginManager().registerEvents(new CombatListener(combat), this);
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static String colorize(String message) {
        return message.replaceAll("&", "§");
    }
}
