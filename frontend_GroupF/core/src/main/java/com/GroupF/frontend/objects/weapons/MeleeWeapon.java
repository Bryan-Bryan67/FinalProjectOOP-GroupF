package com.GroupF.frontend.objects.weapons;

import com.GroupF.frontend.objects.weapons.strategies.MeleeSwingStrategy;

 // Strategy Pattern (MeleeSwingStrategy) and parameterizes range, damage, and cooldown.

public class MeleeWeapon extends Weapon {
    public MeleeWeapon(String name, int damage, float range, float cooldown) {
        super(name, WeaponType.MELEE, damage, range, cooldown, 0, 0, 0, false, new MeleeSwingStrategy());
    }
}
