package game.utils;
import java.util.Objects;

public class Resource {
    private int capacity;
    private int amount;

    public Resource( int amount, int capacity){
        this.capacity = capacity;
        this.amount = amount;
    }

    public int getCapacity(){
        return capacity;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public void addCapacity(int capacity){
        this.capacity += capacity;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = Math.min(amount,capacity);
    }

    public void addAmount(int amount){
        this.amount = Math.min(this.amount+amount,capacity);
    }

    public void reduceAmount(int amount){
        this.amount = Math.max(this.amount-amount,0);
    }

    public void restore(){
        this.amount = this.capacity;
    }

    @Override
    public String toString(){
        return String.format("%d, /%d", amount, capacity);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass()!=o.getClass()){
            return false;
        }

        Resource that = (Resource) o;
        return capacity == that.capacity && amount == that.amount;
    }
}
