package com.GroupF.frontend.systems;

import com.GroupF.frontend.objects.Enemy;
import com.GroupF.frontend.objects.Player;
import com.GroupF.frontend.objects.bullets.Bullet;
import com.GroupF.frontend.objects.drops.DropItem;
import com.GroupF.frontend.objects.drops.DropPool;
import com.GroupF.frontend.objects.weapons.Bomb;
import com.GroupF.frontend.objects.weapons.Weapon;
import com.GroupF.frontend.objects.weapons.strategies.MeleeSwingStrategy;

import java.util.List;

public class CollisionReferee {

    public void resolveCollisions(Player player, List<Enemy> enemies, BulletManager bulletManager, DropPool dropPool) {
        if (player == null) return;

        // 1. Player picking up DropItems
        if (dropPool != null) {
            for (DropItem drop : dropPool.getActiveDrops()) {
                if (drop.isActive() && player.getHitbox().overlaps(drop.getHitbox())) {
                    player.collectDrop(drop);
                    dropPool.free(drop);
                }
            }
        }

        if (enemies == null) return;

        // 2. Player Bullets hitting Enemies
        if (bulletManager != null) {
            for (Bullet bullet : bulletManager.getActiveBullets()) {
                if (!bullet.isActive() || !bullet.isPlayerBullet()) continue;

                for (Enemy enemy : enemies) {
                    if (!enemy.isActive()) continue;

                    if (bullet.getHitbox().overlaps(enemy.getHitbox())) {
                        boolean isPistol = (bullet.getDamage() == 30 && !bullet.penetratesArmor());
                        boolean killed = enemy.takeDamage(bullet.getDamage(), false, isPistol, bullet.penetratesArmor());
                        bullet.onHitEnemy();

                        if (killed) {
                            player.addScore(100);
                            enemy.onDeath(dropPool);
                        }
                        if (bullet.isDestroyed()) break;
                    }
                }
            }
        }

        // 3. Melee Attack Hit Check
        Weapon equipped = player.getInventory().getEquippedWeapon();
        if (equipped != null && equipped.getType() == Weapon.WeaponType.MELEE) {
            if (equipped.getStrategy() instanceof MeleeSwingStrategy melee && melee.isSwinging()) {
                float px = player.getX() + player.getWidth() / 2f;
                float py = player.getY() + player.getHeight() / 2f;
                float range = equipped.getRange();

                for (Enemy enemy : enemies) {
                    if (!enemy.isActive()) continue;
                    float ex = enemy.getX() + enemy.getWidth() / 2f;
                    float ey = enemy.getY() + enemy.getHeight() / 2f;
                    float dist = (float) Math.hypot(ex - px, ey - py);

                    if (dist <= range) {
                        boolean killed = enemy.takeDamage(equipped.getDamage(), true, false, false);
                        if (killed) {
                            player.addScore(150);
                            enemy.onDeath(dropPool);
                        }
                    }
                }
            }
        }

        // 4. Thrown Bomb Detonation AoE
        for (Bomb bomb : player.getActiveBombs()) {
            if (bomb.isActive() && bomb.isExploded()) {
                float bx = bomb.getX();
                float by = bomb.getY();
                float radius = bomb.getBlastRadius();

                for (Enemy enemy : enemies) {
                    if (!enemy.isActive()) continue;
                    float ex = enemy.getX() + enemy.getWidth() / 2f;
                    float ey = enemy.getY() + enemy.getHeight() / 2f;
                    float dist = (float) Math.hypot(ex - bx, ey - by);

                    if (dist <= radius) {
                        boolean killed = enemy.takeDamage(bomb.getBlastDamage(), false, false, true);
                        if (killed) {
                            player.addScore(200);
                            enemy.onDeath(dropPool);
                        }
                    }
                }
            }
        }
    }
}
