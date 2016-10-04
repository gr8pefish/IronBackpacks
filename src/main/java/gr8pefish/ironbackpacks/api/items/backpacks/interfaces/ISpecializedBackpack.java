package gr8pefish.ironbackpacks.api.items.backpacks.interfaces;

import net.minecraft.item.ItemStack;

public interface ISpecializedBackpack extends IBackpack {
    //ToDo: Make enum for specialities, comment this, etc.
    String getSpecialty(ItemStack stack);

    void setSpecialty(ItemStack stack, String specialty);
}
