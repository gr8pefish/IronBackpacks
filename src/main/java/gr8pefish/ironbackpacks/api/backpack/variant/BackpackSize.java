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

    //Static instances to define valid bounds
    //Private/internal
    private static final int MIN_COL = 1;
    private static final int MIN_ROW = 1;
    //Public/external
    public static final BackpackSize MIN = new BackpackSize(MIN_COL, MIN_ROW, true);
    private static final int MAX_COL = 17;
    private static final int MAX_ROW = 7;
    public static final BackpackSize MAX = new BackpackSize(MAX_COL, MAX_ROW, true);
    @Nonnegative
    private int columns; //not final to allow for configurable sizes on the fly //TODO: configurable sizes
    @Nonnegative
    private int rows; //not final to allow for configurable sizes on the fly

    // Constructors
    public BackpackSize(@Nonnegative int columns, @Nonnegative int rows) {
        Preconditions.checkArgument(columns >= BackpackSize.MIN.columns, "Column count must be greater than the minimum");
        Preconditions.checkArgument(columns <= BackpackSize.MAX.columns, "Column count must be less than the maximum");
        Preconditions.checkArgument(rows >= BackpackSize.MIN.rows, "Row count must be greater than the minimum");
        Preconditions.checkArgument(rows <= BackpackSize.MAX.rows, "Row count must be less than the maximum");

        this.columns = columns;
        this.rows = rows;
    }

    public BackpackSize(@Nonnegative int columns, @Nonnegative int rows, boolean useBoundingConstructor) {
        Preconditions.checkArgument(columns >= 0, "Column count must be non negative");
        Preconditions.checkArgument(columns <= MAX_COL, "Column count must be less than the maximum");
        Preconditions.checkArgument(rows >= 0, "Row count must be non negative");
        Preconditions.checkArgument(rows <= MAX_ROW, "Row count must be less than the maximum");

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
        Preconditions.checkArgument(columns >= BackpackSize.MIN.columns, "Column count must be greater than the minimum");
        Preconditions.checkArgument(columns <= BackpackSize.MAX.columns, "Column count must be less than the maximum");
        this.columns = columns;
        return this;
    }

    @Nonnegative
    public int getRows() {
        return rows;
    }

    @Nonnull
    public BackpackSize setRows(@Nonnegative int rows) {
        Preconditions.checkArgument(rows >= BackpackSize.MIN.rows, "Row count must be greater than the minimum");
        Preconditions.checkArgument(rows <= BackpackSize.MAX.rows, "Row count must be less than the maximum");
        this.rows = rows;
        return this;
    }

    // Overrides for NBT

    @Override
    @Nonnull
    public NBTTagByteArray serializeNBT() {
        return new NBTTagByteArray(new byte[]{(byte) columns, (byte) rows});
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
        BackpackSize size = (BackpackSize) obj;
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
     * @param specialty         - the {@link BackpackSpecialty} to modify the size with.
     * @param rowIncreaseAmount - the number of rows to increase the size by.
     * @param colIncreaseAmount - the number of columns to increase the size by.
     * @return - the updated BackpackSize
     */
    @Nonnull
    public BackpackSize applySizeModifierFromBackpackSpecialty(@Nonnull BackpackSpecialty specialty, @Nonnegative int colIncreaseAmount, @Nonnegative int rowIncreaseAmount) {

        Preconditions.checkNotNull(specialty, "Specialty cannot be null");
        Preconditions.checkArgument(colIncreaseAmount >= 0, "ColIncreaseAmount cannot be negative");
        Preconditions.checkArgument(rowIncreaseAmount >= 0, "RowIncreaseAmount cannot be negative");

        if (specialty.equals(BackpackSpecialty.STORAGE)) {
            return new BackpackSize(this.getColumns() + colIncreaseAmount, this.getRows() + rowIncreaseAmount);
        } else {
            return this;
        }

    }

    //TODO: More complex/configurable
    @Nonnull
    public BackpackSize applyDefaultSizeModifierFromBackpackSpecialty(@Nonnull BackpackSpecialty specialty) {
        Preconditions.checkNotNull(specialty, "Specialty cannot be null");

        if (this.getRows() == MAX.getRows()) {
            return applySizeModifierFromBackpackSpecialty(specialty, 2, 0); //have to increase width (columns += 2)
        } else {
            return applySizeModifierFromBackpackSpecialty(specialty, 0, 1); //default increase height (rows += 1)
        }
    }


}
