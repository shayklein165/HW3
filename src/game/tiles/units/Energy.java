package game.tiles.units;

public class Energy {
    private int cost;
    private int specialAbilityCost;
    private int currentEnergy;

    public Energy(int cost, int specialAbilityCost, int currentEnergy) {
        this.cost = cost;
        this.specialAbilityCost = specialAbilityCost;
        this.currentEnergy = 100;
    }

    public int getCost() {
        return cost;
    }

    public int getSpecialAbilityCost() {
        return specialAbilityCost;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setSpecialAbilityCost(int specialAbilityCost) {
        this.specialAbilityCost = specialAbilityCost;
    }


}
