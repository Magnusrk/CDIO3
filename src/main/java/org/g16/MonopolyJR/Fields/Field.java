package org.g16.MonopolyJR.Fields;

public abstract class Field {
    protected String name;
    public Field(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
