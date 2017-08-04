package gr8pefish.ironbackpacks.api.variant;

import net.minecraft.nbt.NBTTagByteArray;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * CURRENTLY UNUSED
 */
public class BackpackSize implements INBTSerializable<NBTTagByteArray> {

    //Size determined from cols x rows
    private int columns = -1;
    private int rows    = -1;

    public BackpackSize() {
        //Empty constructor
    }

    public BackpackSize(int columns, int rows) {
        this.columns = columns;
        this.rows    = rows;
    }

    //Getters and Setters

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    //Overrides for NBT

    @Override
    public NBTTagByteArray serializeNBT() {
        return new NBTTagByteArray(new byte[]{ (byte)columns, (byte)rows });
    }

    @Override
    public void deserializeNBT(NBTTagByteArray nbt) {
        columns = nbt.getByteArray()[0];
        rows    = nbt.getByteArray()[1];
    }

    //Override equals

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BackpackSize)) return false;
        if (obj == this) return true;
        BackpackSize size = (BackpackSize)obj;
        return (size.columns == columns) && (size.rows == rows);
    }

    //Helper methods

    public int getTotalSize() {
        return columns * rows;
    }

    public enum SIZE {
        BASIC,
        IRON,
        GOLD,
        DIAMOND;
    }


}
