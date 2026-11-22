package com.GroupF.frontend.objects.enemies;

public class Enemy {
    public String name;
    public int hp;
    public int maxHp;
    protected ItemType itemDrop = null;
    protected boolean damageable = true;
    protected boolean collidable = true;
    protected boolean rangedAttack = false;
    protected boolean canSummon = false;
    protected float shotHitboxWidth = 0f;
    protected float shotHitboxHeight = 0f;
    protected long scoreValue;


}
