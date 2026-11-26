package com.GroupF.frontend.objects.weapons.strategies;

import com.GroupF.frontend.objects.weapons.Weapon;
import com.GroupF.frontend.systems.BulletManager;


 //Defines different weapon attack behaviors (Single shot, Automatic, Spread, Piercing, Melee swing).

public interface AttackStrategy {
    boolean executeAttack(float originX, float originY, float targetX, float targetY,
                          Weapon weapon, BulletManager bulletManager);
    String getBehaviorName();
}
