package org.drnull.varticspvp;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Combat {
    public HashSet<Player> combatSet = new HashSet<>();
    public HashMap<Player, BukkitRunnable> taskMap = new HashMap<>();

    public Player getKey(Player value , HashMap<Player, Player> map){

        if (!map.containsValue(value))
            return null;


        return map.keySet().stream().filter(key -> map.get(key).equals(value)).findFirst().orElse(null);
    }

    public boolean onCombat(Player player, Player player2){
        return combatSet.contains(player2) || combatSet.contains(player);
    }
}
