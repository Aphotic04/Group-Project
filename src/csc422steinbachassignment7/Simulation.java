/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package csc422steinbachassignment7;

/**
 *
 * @author chris, connor
 */
import Characters.Soldier;
import Characters.Tank;
import Characters.Child;
import Characters.CommonInfected;
import Characters.Teacher;
import java.util.ArrayList;
import java.util.Random;
import Characters.Character;
import Characters.Child;
import Characters.CommonInfected;
import Characters.Soldier;
import Characters.Tank;
import Characters.Teacher;
import Weapons.AssaultRifle;
import Weapons.Axe;
import Weapons.Crowbar;
import Weapons.FaultyGrenade;
import Weapons.FryingPan;
import Weapons.Pistol;
import Weapons.RubberChicken;
import Weapons.SubmachineGun;
import Weapons.Weapon;

public class Simulation {
    public static void main(String[] args) {
        // ArrayList to contain the survivors 
        ArrayList<Character> survivors = new ArrayList<>();
        // ArrayList to contain the zombies 
        ArrayList<Character> zombies = new ArrayList<>();
        // ArrayList to contain the random survivor types
        ArrayList<String> survivorTypes = new ArrayList<>();
        // ArrayList to contain the random zombie types
        ArrayList<String> zombieTypes = new ArrayList<>();
        //ArrayList to contain the weapons cache
        ArrayList<Weapon> weaponsCache = new ArrayList<>();
        // creating a random object
        Random random = new Random();
        // setting the count for child, teacher, and soldier to 0
        int childCount = 0, teacherCount = 0, soldierCount = 0;
        for (int i = 0; i < 5; i++) {
            int survivorType = random.nextInt(3);
            if (survivorType == 0) {
                survivors.add(new Child());
                survivorTypes.add("Child");
                childCount++;
            } else if (survivorType == 1) {
                survivors.add(new Teacher());
                survivorTypes.add("Teacher");
                teacherCount++;
            } else {
                survivors.add(new Soldier());
                survivorTypes.add("Soldier");
                soldierCount++;
            }
        }
        
        //Adds to weapons cache
        for (int i = 0; i < random.nextInt(20); i++) {
            int weaponType = random.nextInt(8);
            switch(weaponType) {
                case 0 -> weaponsCache.add(new AssaultRifle());
                case 1 -> weaponsCache.add(new Axe());
                case 2 -> weaponsCache.add(new Crowbar());
                case 3 -> weaponsCache.add(new FaultyGrenade());
                case 4 -> weaponsCache.add(new FryingPan());
                case 5 -> weaponsCache.add(new Pistol());
                case 6 -> weaponsCache.add(new RubberChicken());
                case 7 -> weaponsCache.add(new SubmachineGun());
            }
        }
        
        //Array for if a weapon has been picked
        ArrayList<Weapon> tempWeaponsCache = (ArrayList<Weapon>) weaponsCache.clone();
        //Array for if a survivor has been picked
        ArrayList<Character> tempSurvivors = (ArrayList<Character>) survivors.clone();
        //Makes survivors choose as many weapons as they can, one per person
        while (!tempSurvivors.isEmpty() && !tempWeaponsCache.isEmpty()) {
            //Weapon and Survivor choices
            int weaponChoice = random.nextInt(tempWeaponsCache.size());
            int survivorChoice = random.nextInt(tempSurvivors.size());
            //Placing the weapon in the survivor's inventory
            tempSurvivors.get(survivorChoice).setWeapon(tempWeaponsCache.get(weaponChoice));
            //Remove survivor/weapon from temp array
            tempSurvivors.remove(survivorChoice);
            tempWeaponsCache.remove(weaponChoice);
        }

        int commonInfectedCount = 0;
        int tankCount = 0;
        for (int i = 0; i < 9; i++) {
            int zombieType = random.nextInt(2);
            if (zombieType == 0) {
                zombies.add(new CommonInfected());
                zombieTypes.add("CommonInfected");
                commonInfectedCount++;
            } else {
                zombies.add(new Tank());
                zombieTypes.add("Tank");
                tankCount++;
            }
        }

        System.out.println("We have " + survivors.size() + " survivors trying to make it to safety (" +
                childCount + " children, " + teacherCount + " teachers, " + soldierCount + " soldiers)\n");
        
        System.out.println("The survivors have picked their weapon of choice out of a weapons cache of " + weaponsCache.size());

        System.out.println("But there are " + zombies.size() + " zombies waiting for them (" +
                commonInfectedCount + " common infected, " + tankCount + " tanks)\n");

        //Initialize variables for tracking survivors and zombieduring the battle
        int survivorIndex = 0; //Track the index of the current survivor
        int zombieIndex = 0; //Track the index of the current zombie
        ArrayList<String> killList = new ArrayList<>(); //Store the message of who killed whom

        while (true) {
            //Loop through each survivor in the list
            for (int i = 0; i < survivors.size(); i++) {
                //check if the survivor is still alive
                if (survivors.get(i).isAlive()) {
                    //loop through each zombies in the list
                    for (int j = 0; j < zombies.size(); j++) {
                        //check if the zombie is still alive
                        if (zombies.get(j).isAlive()) {
                            //survivor attacks the zombie decreasing its health
                            survivors.get(i).attacking(zombies.get(j));

                            //if the zombie is killed from this attack 
                            if (!zombies.get(j).isAlive()) {
                                
                                //If the survivor didn't have a weapon, else
                                if(survivors.get(i).getWeapon() == null) {
                                    //add the message to killist array of which survivor killed which zombie
                                    killList.add(survivorTypes.get(i) + " " + i + " killed " +
                                            zombieTypes.get(j) + " " + j);
                                }
                                else {
                                    //add the message to killist array of which survivor killed which zombie and what with
                                    killList.add(survivorTypes.get(i) + " " + i + " killed " +
                                            zombieTypes.get(j) + " " + j + " with a " + survivors.get(i).getWeapon().getWeaponName());
                                }
                            }
                        }
                    }
                }
            }
            //iterate through each zombie in the list
            for (int i = 0; i < zombies.size(); i++) {
                //check if the zombie is still alive
                if (zombies.get(i).isAlive()) {
                    //iterate through the survivor
                    for (int j = 0; j < survivors.size(); j++) {
                        //check if the current survivor is still alive
                        if (survivors.get(j).isAlive()) {
                            //Zombie attacks the survior and their health reduces.
                            zombies.get(i).attacking(survivors.get(j));
                            //if the survivor dies
                            if (!survivors.get(j).isAlive()) {
                                //add the message to killist showing which zombie killed which survivor
                                killList.add(zombieTypes.get(i) + " " + i + " killed " +
                                        survivorTypes.get(j) + " " + j);
                            }
                        }
                    }
                }
            }
            // Gets alive survivors and zombies, checks for who has remaining counts of zombies and survivors
            int aliveSurvivors = 0;
            for (Character survivor : survivors) {
                if (survivor.isAlive()) {
                    aliveSurvivors++;
                }
            }
            
            int aliveZombies = 0;
            for (Character zombie : zombies) {
                if (zombie.isAlive()) {
                    aliveZombies++;
                }
            }
            // Break condition if no one is left alive, zombies or survivors
            if (aliveSurvivors == 0 || aliveZombies == 0) {
                break;
            }
        }
        // Prints killList's report
        for (String report : killList) {
            System.out.println(report);
        }
        // Checks for count of survivors
        int survivingCount = 0;
        for (Character survivor : survivors) {
            if (survivor.isAlive()) {
                survivingCount++;
            }
        }
        //Prints scenarios depending on if any survivors made it or not
        if (survivingCount == 0) {
            System.out.println("None of the survivors made it.");
        } else {
            System.out.println(survivingCount + " survivors made it to safety.");
        }
    }
}
