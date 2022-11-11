package org.g16.MonopolyJR;

public class PropertyField extends Field {
    protected int price;
    protected Color color;
    protected Player owner;
    public PropertyField(String name, Color startingColor, int price){
        super(name);
        this.color = startingColor;
        this.price = price;
    }
    public int getPrice(){
        return price;
    }
    public Color getColor(){
        return color;
    }
    public Player getOwner(){
        return owner;
    }
    public void setOwner(Player player){
        owner = player;
    }
}
