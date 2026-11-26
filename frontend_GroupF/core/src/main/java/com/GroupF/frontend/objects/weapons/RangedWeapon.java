package com.GroupF.frontend.objects.weapons;

import com.GroupF.frontend.objects.weapons.strategies.AttackStrategy;


 //ranged firearms .
 //magazine capacity, armor penetration, firing rate, and dynamic AttackStrategy.

public class RangedWeapon extends Weapon {
    public RangedWeapon(String name, int damage, float range, float cooldown,
                        int magazineCapacity, int armorPenetration, boolean automatic,
                        AttackStrategy strategy) {
        super(name, WeaponType.RANGED, damage, range, cooldown, magazineCapacity, magazineCapacity,
              armorPenetration, automatic, strategy);
    }
}
