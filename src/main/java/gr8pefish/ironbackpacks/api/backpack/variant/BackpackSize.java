package gr8pefish.ironbackpacks.api.backpack.variant;

import com.google.common.base.Preconditions;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * The size of the backpack.
 *
 * Defined by the number of rows and columns.
 * Includes methods to change these internal values, as well as other miscellaneous helper methods.
 */
public class BackpackSize implements INBTSerializable<NBTTagByteArray> {

    // Fields

    @Nonnegative
    private int columns; //not final to allow for configurable sizes on the fly
    @Nonnegative
    private int rows; //not final to allow for configurable sizes on the fly


    public static final BackpackSize MIN = new BackpackSize(1, 1);
    public static final BackpackSize MAX = new BackpackSize(17, 7);


    // Constructors

    public BackpackSize() {
        //No-op
    }

    public BackpackSize(@Nonnegative int columns, @Nonnegative int rows) {
        Preconditions.checkArgument(columns > 0, "Column count must be greater than 0" );
        Preconditions.checkArgument(rows > 0, "Row count must be greater than 0" );

        this.columns = columns;
        this.rows = rows;
    }

    // Getters and Setters

    @Nonnegative
    public int getColumns() {
        return columns;
    }

    @Nonnull
    public BackpackSize setColumns(@Nonnegative int columns) {
        this.columns = columns;
        return this;
    }

    @Nonnegative
    public int getRows() {
        return rows;
    }

    @Nonnull
    public BackpackSize setRows(@Nonnegative int rows) {
        this.rows = rows;
        return this;
    }

    // Overrides for NBT

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

    // Overrides

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

    // Helper methods

    @Nonnegative
    public int getTotalSize() {
        return columns * rows;
    }

    /**
     * Alters the size of the backpack depending on the {@link BackpackSpecialty}.
     *
     * Currently only modifies with the {@link BackpackSpecialty#STORAGE} specialty.
     *
     * @param specialty - the {@link BackpackSpecialty} to modify the size with.
     * @param rowIncreaseAmount - the number of rows to increase the size by.
     * @param colIncreaseAmount - the number of columns to increase the size by.
     * @return - the updated BackpackSize
     */
    @Nonnull
    public BackpackSize applySizeModifierFromBackpackSpecialty(@Nonnull BackpackSpecialty specialty, @Nonnegative int colIncreaseAmount, @Nonnegative int rowIncreaseAmount) {
        if (specialty.equals(BackpackSpecialty.STORAGE)) {
            return new BackpackSize(this.getColumns() + colIncreaseAmount, this.getRows() + rowIncreaseAmount);
        } else {
            return this;
        }

    }

    /** Helper method to apply the default size specialty modifier, an increase of 1 row. */
    @Nonnull
    public BackpackSize applyDefaultSizeModifierFromBackpackSpecialty(@Nonnull BackpackSpecialty specialty) {
        if (this.getRows() == MAX.getRows()) {
            return applySizeModifierFromBackpackSpecialty(specialty, 2, 0); //have to increase width (cols)
        } else {
            return applySizeModifierFromBackpackSpecialty(specialty, 0, 1); //default increase by 1 row
        }
    }


}
