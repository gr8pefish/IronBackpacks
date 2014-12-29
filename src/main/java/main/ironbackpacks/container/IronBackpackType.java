package main.ironbackpacks.container;

public enum IronBackpackType {

    BASIC(54, 9, "Basic Backpack");

    int size;
    private int rowLength;
    public String fancyName;
//    public String textureLocation;

    IronBackpackType(int size, int rowLength, String fancyName){
        this.size = size;
        this.rowLength = rowLength;
        this.fancyName = fancyName;
    }

    public int getRowCount() {
        return size / rowLength;
    }

    public int getRowLength() {
        return rowLength;
    }

    public int getSize() {
        return size;
    }
}
