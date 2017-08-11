package gr8pefish.ironbackpacks.api.variant;

import com.google.common.base.Preconditions;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The size of the backpack in terms of number of rows and columns.
 */
public class BackpackSize implements INBTSerializable<NBTTagByteArray> {

    //Size determined from cols x rows
    @Nonnegative
    private int columns;
    @Nonnegative
    private int rows;

    public BackpackSize() {
        //Empty constructor
    }

    public BackpackSize(@Nonnegative int columns, @Nonnegative int rows) {
        Preconditions.checkArgument(columns > 0, "Column count must be greater than 0" );
        Preconditions.checkArgument(rows > 0, "Row count must be greater than 0" );

        this.columns = columns;
        this.rows = rows;
    }

    //Getters and Setters

    @Nonnegative
    public int getColumns() {
        return columns;
    }

    public void setColumns(@Nonnegative int columns) {
        this.columns = columns;
    }

    @Nonnegative
    public int getRows() {
        return rows;
    }

    public void setRows(@Nonnegative int rows) {
        this.rows = rows;
    }

    //Overrides for NBT

    @Override
    @Nonnull
    public NBTTagByteArray serializeNBT() {
        return new NBTTagByteArray(new byte[]{ (byte)columns, (byte)rows });
    }

    @Override
    public void deserializeNBT(@Nonnull NBTTagByteArray nbt) {
        columns = nbt.getByteArray()[0];
        rows = nbt.getByteArray()[1];
    }

    //Overrides

    @Override
    public boolean equals(@Nonnull Object obj) {
        if (!(obj instanceof BackpackSize)) return false;
        if (obj == this) return true;
        BackpackSize size = (BackpackSize)obj;
        return (size.columns == columns) && (size.rows == rows);
    }

    @Override
    public String toString() {
        return columns + "x" + rows + " => " + getTotalSize();
    }

    //Helper methods

    @Nonnegative
    public int getTotalSize() {
        return columns * rows;
    }

    @Nonnull
    public BackpackSize applySizeSpecialtyModifier(@Nonnull BackpackSpecialty specialty) {
        if (specialty.equals(BackpackSpecialty.STORAGE)) {
            return new BackpackSize(this.getColumns(), this.getRows() + 1); //add a row //TODO: More configurable(/complex?)
        } else {
            return this;
        }
    }


}
