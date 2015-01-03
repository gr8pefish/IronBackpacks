package main.ironbackpacks.items.backpacks;

public enum IronBackpackType {

    SINGLE(1, 27, 9, "singleBackpack"),
    DOUBLE(2, 54, 9, "doubleBackpack");

    private int id; //id int value of number in enum
    private int size; //number of slots
    private int rowLength; //number of rows
    public String name;

    IronBackpackType(int id, int size, int rowLength, String fancyName){
        this.id = id;
        this.size = size;
        this.rowLength = rowLength;
        this.name = fancyName;
    }

    public int getRowCount() {
        return size / rowLength;
    }

    public int getRowLength() {
        return rowLength;
    }

    public int getSize() { return size; }

    public String getName() {return name; }

    public int getId() { return id; }
}
