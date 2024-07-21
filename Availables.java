package org.drnull.varticspvp;

import org.bukkit.entity.Player;

public class Availables {

    private final Player dead;
    private final Player alive;

    public Availables(Player dead, Player alive) {
        this.alive = alive;
        this.dead = dead;
    }

    public Player getDead() {
        return dead;
    }

    public Player getAlive() {
        return alive;
    }
}
