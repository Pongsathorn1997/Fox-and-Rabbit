package io.muic.ooc.fab;

public class Rabbit extends Animal {


    @Override
    public Location moveToNewLocation() {
        return field.freeAdjacentLocation(getLocation());
    }

    @Override
    protected int getMaxAge() {
        return 40;
    }

    @Override
    protected double getBreedingProbability() {
        return 0.12;
    }

    @Override
    protected int getMaxLiterSize() {
        return 4;
    }

    @Override
    protected int getBreedingAge() {
        return 5;
    }
}
