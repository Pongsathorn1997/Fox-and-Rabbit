package io.muic.ooc.fab;

import io.muic.ooc.fab.*;

import java.util.Iterator;
import java.util.List;

public class Hunter extends Animal {

    /**
     * Create a Hunter. A Hunter can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    @Override
    public void initialize(boolean randomAge, Field field, Location location) {
        super.initialize(randomAge, field, location);
    }

    // move to new location;
    @Override
    public Location moveToNewLocation() {
        Location newLocation = huntAnimal();
        if (newLocation == null) {
            // No food found - try to move to a free location.
            newLocation = field.freeAdjacentLocation(getLocation());
        }
        return newLocation;
    }

    // make it immortal;
    @Override
    protected int getMaxAge() {
        return Integer.MAX_VALUE;
    }

    /**
     * This is what the fox does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newHumans A list to return newly born foxes
     */
    @Override
    public void act(List<Animal> newHumans) {
        super.act(newHumans);
    }


    /**
     * Look for rabbits and foxes adjacent to the current location. Only the first live
     * rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location huntAnimal() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            // making it looking for rabbit
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    return where;
                }
            }
            // this one looking for fox
            if (animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if (fox.isAlive()) {
                    fox.setDead();
                    return where;
                }
            }
            // this one looking for tiger
            if (animal instanceof Tiger) {
                Tiger tiger = (Tiger) animal;
                if (tiger.isAlive()) {
                    tiger.setDead();
                    return where;
                }
            }
        }
        return null;
    }

    // minimize the breedingProbability because it now can eat both fox and rabbit
    @Override
    protected double getBreedingProbability() {
        return 0.0009;
    }

    @Override
    protected int getMaxLiterSize() {
        return 2;
    }

    @Override
    protected int getBreedingAge() {
        return 60;
    }
}
