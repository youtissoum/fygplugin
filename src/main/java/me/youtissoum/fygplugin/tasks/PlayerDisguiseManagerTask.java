/*
 * Copyright (c) 2022. This is of full ownership of the fyg cafe server admins and owner lexy kobashigawa it was made for them and exclusively for them
 */

package me.youtissoum.fygplugin.tasks;

import me.youtissoum.fygplugin.Fygplugin;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerDisguiseManagerTask extends BukkitRunnable {
    private Player p;
    private Entity disguise;
    private LivingEntity le;
    public PlayerDisguiseManagerTask(Player p, Entity disguise) {
        this.p = p;
        this.disguise = disguise;
    }

    @Override
    public void run() {
        boolean living = false;

        if(disguise instanceof LivingEntity) {
            le = ((LivingEntity) disguise);
            living = true;
        }

        if(Fygplugin.getDisguises().containsKey(p)) {
            disguise.teleport(p.getLocation());
            p.setVisualFire(false);
            if(living) {
                if(le.getHealth() != 0) {
                    p.setHealth((le.getHealth() / le.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) * 20);
                } else p.setHealth(0);
            }
        } else this.cancel();
    }
}
