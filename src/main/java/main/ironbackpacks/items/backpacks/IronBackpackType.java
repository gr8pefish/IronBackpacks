package main.ironbackpacks.items.backpacks;

public enum IronBackpackType {

    SINGLE(27, 9, "singleBackpack"),
    DOUBLE(54, 9, "doubleBackpack");

    private int size; //number of slots
    private int rowLength; //number of rows
    public String name;

    IronBackpackType(int size, int rowLength, String fancyName){
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
}
