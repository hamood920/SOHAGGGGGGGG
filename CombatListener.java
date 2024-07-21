package org.drnull.varticspvp;

import static org.drnull.varticspvp.Plugin.colorize;
import static org.drnull.varticspvp.Plugin.prefix;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.drnull.varticspvp.Plugin;

public class CombatListener implements Listener {

    private final Combat combat;

    public CombatListener(Combat combat) {
        this.combat = combat;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        Player attacker = null;

        if (e.getDamager() instanceof Player) {
            attacker = (Player) e.getDamager();
        } else if (e.getDamager() instanceof FishHook) {
            FishHook fishHook = (FishHook) e.getDamager();
            if (fishHook.getShooter() instanceof Player) {
                attacker = (Player) fishHook.getShooter();
            }
        } else if (e.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getDamager();
            if (arrow.getShooter() instanceof Player) {
                attacker = (Player) arrow.getShooter();
            }
        }

        if (attacker == null || !(e.getEntity() instanceof Player)) {
            return;
        }

        Player victim = (Player) e.getEntity();
        combat.combatSet.add(victim);
        combat.combatSet.add(attacker);

        if (combat.taskMap.containsKey(attacker)) {
            combat.taskMap.get(attacker).cancel();
        }
        if (combat.taskMap.containsKey(victim)) {
            combat.taskMap.get(victim).cancel();
        }

        CombatTask task = new CombatTask(combat, attacker, victim);
        task.runTaskTimer(Plugin.getInstance(), 0, 20);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        if (combat.combatSet.contains(victim)) {
            combat.combatSet.remove(victim);
            victim.sendMessage(Plugin.colorize(Plugin.prefix + " &aYou are no longer in combat."));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (combat.combatSet.contains(player)) {
            Inventory inv = player.getInventory();
            List<ItemStack> drops = new ArrayList<>();
            for (ItemStack item : inv.getContents()) {
                if (item != null) {
                    drops.add(item);
                }
            }
            Plugin.getInstance().getServer().getPluginManager()
                    .callEvent(new PlayerDeathEvent(player, drops, 0, null));
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (combat.combatSet.contains(player)) {
            player.sendMessage(colorize(prefix + " &cCannot launch &e" + e.getMessage() + " &cWhile in combat."));
            e.setCancelled(true);
        }
    }
}
