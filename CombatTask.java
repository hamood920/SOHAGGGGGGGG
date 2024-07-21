package org.drnull.varticspvp;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.drnull.varticspvp.Plugin;
import org.drnull.varticspvp.Util.Actionbar;

public class CombatTask extends BukkitRunnable {

    private final Player player1;
    private final Player player2;
    private final Combat combat;

    private int count = 10;

    public CombatTask(Combat combat, Player player1, Player player2) {
        this.combat = combat;
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        combat.taskMap.put(player1, this);
        combat.taskMap.put(player2, this);

        if (count > 0) {
            sendCombatMessage(player1);
            sendCombatMessage(player2);
            count--;
        } else {
            handleCanceling(player1, player2);
            this.cancel();
        }
    }

    private void sendCombatMessage(Player player) {
        if (combat.combatSet.contains(player)) {
            Actionbar.sendActionBar(player, Plugin.colorize(Plugin.prefix + " &aIn Combat for &c" + count + " &aseconds"));
        }
    }

    private void handleCanceling(Player player1, Player player2) {
        if (combat.combatSet.contains(player1)) {
            player1.sendMessage(Plugin.colorize(Plugin.prefix + " &aYou are no longer in combat."));
            combat.combatSet.remove(player1);
        }
        if (combat.combatSet.contains(player2)) {
            player2.sendMessage(Plugin.colorize(Plugin.prefix + " &aYou are no longer in combat."));
            combat.combatSet.remove(player2);
        }
        combat.taskMap.remove(player1);
        combat.taskMap.remove(player2);
    }
}
