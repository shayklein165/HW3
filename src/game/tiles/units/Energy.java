package game.tiles.units;

public class Energy {
    private int cost;
    private int currentEnergy;

    public Energy(int cost, int currentEnergy) {
        this.cost = cost;
        this.currentEnergy = currentEnergy;
    }

    public int getCost() {
        return cost;
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

}
