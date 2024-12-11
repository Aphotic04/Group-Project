package Weapons;

public class Weapon {
    //Member variables
    protected int damage;
    protected double accuracy;
    protected String weaponName;
    
    //Weapon constructor
    public Weapon(int damage, double accuracy, String weaponName) {
        this.damage = damage;
        this.accuracy = accuracy;
        this.weaponName = weaponName;
    }
    
    //Setters/Getters
    public int getDamage() {
        return damage;
    }
    
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public double getAccuracy() {
        return accuracy;
    }
    
    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
    
    public String getWeaponName() {
        return weaponName;
    }
    
    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }
}
