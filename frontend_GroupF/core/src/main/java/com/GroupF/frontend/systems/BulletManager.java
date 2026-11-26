package com.GroupF.frontend.systems;

import com.GroupF.frontend.objects.bullets.Bullet;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//object Pool Pattern:

public class BulletManager {
    private final Queue<Bullet> bulletPool = new LinkedList<>();
    private final List<Bullet> activeBullets = new ArrayList<>();

    public Bullet spawnBullet(float x, float y, float vx, float vy, int damage, boolean isPlayerBullet,
                              boolean penetratesArmor, int penetrationCount, Color color) {
        Bullet bullet;
        if (!bulletPool.isEmpty()) {
            bullet = bulletPool.poll();
        } else {
            bullet = new Bullet();
        }
        bullet.init(x, y, vx, vy, damage, isPlayerBullet, penetratesArmor, penetrationCount, color);
        activeBullets.add(bullet);
        return bullet;
    }

    public void update(float delta, float screenWidth, float screenHeight) {
        Iterator<Bullet> it = activeBullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            bullet.update(delta);
            if (bullet.isDestroyed() || bullet.isOffScreen(screenWidth, screenHeight)) {
                bullet.destroy();
                it.remove();
                bulletPool.offer(bullet);
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        for (Bullet bullet : activeBullets) {
            if (bullet.isActive()) {
                bullet.render(shapeRenderer);
            }
        }
    }

    public List<Bullet> getActiveBullets() {
        return activeBullets;
    }

    public int getPoolSize() {
        return bulletPool.size();
    }

    public void clear() {
        for (Bullet b : activeBullets) {
            b.destroy();
            bulletPool.offer(b);
        }
        activeBullets.clear();
    }
}
