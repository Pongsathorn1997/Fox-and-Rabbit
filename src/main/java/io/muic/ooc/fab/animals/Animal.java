package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public abstract class Animal {

    // Random generator
    protected static final Random RANDOM = new Random();

    // Encapsulation
    // Whether the animal is alive or not.
    private boolean alive = true;

    // The fox's position.
    private Location location;
    // The field occupied.
    protected Field field;
    // Individual characteristics (instance fields).
    // The animal's age.
    private int age = 0;



    public void initialize(boolean randomAge, Field field, Location location) {
        this.field = field;
        setLocation(location);
        if (randomAge) {
            age = RANDOM.nextInt(getMaxAge());
        }
    }

    public abstract Location moveToNewLocation();

    public void act(List<Animal> newAnimals){
        incrementAge();
        if (isAlive()) {
            giveBirth(newAnimals);
            // Try to move into a free location.
            Location newLocation = moveToNewLocation();

            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    private Animal breedOne(boolean randomAge, Field field, Location location){
        return AnimalFactory.createAnimal(getClass(), field, location);
    }

    private void giveBirth(List<Animal> newAnimals) {
        // New animals are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = breedOne(false, field, loc);
            newAnimals.add(young);
        }
    }


    //Check whether the animal is alive or not.
    //return true if the animal is still alive.

    public boolean isAlive() {
        return alive;
    }


    //Indicate that the animal is no longer alive. It is removed from the field.
    protected void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    // get the location of animal
    public Location getLocation() {
        return location;
    }


    // Place the animal at the new location in the given field.

    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }


    // liskov - substitution
    // get the max age
    protected abstract int getMaxAge();

    // Increase the age. This could result in the rabbit's death.
    protected void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    protected abstract double getBreedingProbability();

    protected abstract int getMaxLiterSize();

    protected abstract int getBreedingAge();

    /**
     * Generate a number representing the number of births, if it can breed.
     *
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && RANDOM.nextDouble() <= getBreedingProbability()) {
            births = RANDOM.nextInt(getMaxLiterSize()) + 1;
        }
        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     *
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed() {
        return age >= getBreedingAge();
    }
}
