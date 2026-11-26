package com.GroupF.frontend.objects.weapons;

import com.GroupF.frontend.objects.weapons.strategies.AttackStrategy;
import com.GroupF.frontend.systems.BulletManager;


 //Base class for all melee and ranged weapons.
 //Uses Strategy Pattern to delegate attack execution behaviors.

public abstract class Weapon {
    protected String name;
    protected WeaponType type;
    protected int damage;
    protected float range;
    protected float cooldown;
    protected float cooldownTimer = 0f;
    protected int currentAmmo;
    protected int maxAmmo;
    protected int armorPenetration; // 0 = none/weak, 1 = medium, 2 = full penetration
    protected AttackStrategy strategy;
    protected boolean automatic;

    public enum WeaponType {
        MELEE,
        RANGED
    }

    public Weapon(String name, WeaponType type, int damage, float range, float cooldown,
                  int currentAmmo, int maxAmmo, int armorPenetration, boolean automatic,
                  AttackStrategy strategy) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.range = range;
        this.cooldown = cooldown;
        this.currentAmmo = currentAmmo;
        this.maxAmmo = maxAmmo;
        this.armorPenetration = armorPenetration;
        this.automatic = automatic;
        this.strategy = strategy;
    }

    public void update(float delta) {
        if (cooldownTimer > 0f) {
            cooldownTimer -= delta;
        }
    }

    public boolean canAttack() {
        if (cooldownTimer > 0f) return false;
        if (type == WeaponType.RANGED && currentAmmo <= 0) return false;
        return true;
    }

    public boolean attack(float originX, float originY, float targetX, float targetY, BulletManager bulletManager) {
        if (!canAttack() || strategy == null) return false;

        boolean success = strategy.executeAttack(originX, originY, targetX, targetY, this, bulletManager);
        if (success) {
            cooldownTimer = cooldown;
            if (type == WeaponType.RANGED) {
                currentAmmo--;
            }
        }
        return success;
    }

    public void addAmmo(int amount) {
        this.currentAmmo = Math.min(this.maxAmmo, this.currentAmmo + amount);
    }

    public String getName() { return name; }
    public WeaponType getType() { return type; }
    public int getDamage() { return damage; }
    public float getRange() { return range; }
    public float getCooldown() { return cooldown; }
    public int getCurrentAmmo() { return currentAmmo; }
    public void setCurrentAmmo(int currentAmmo) { this.currentAmmo = currentAmmo; }
    public int getMaxAmmo() { return maxAmmo; }
    public int getArmorPenetration() { return armorPenetration; }
    public boolean isAutomatic() { return automatic; }
    public AttackStrategy getStrategy() { return strategy; }
    public void setStrategy(AttackStrategy strategy) { this.strategy = strategy; }
}
