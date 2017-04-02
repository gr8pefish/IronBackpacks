package gr8pefish.ironbackpacks.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BackpackInfo implements INBTSerializable<NBTTagCompound> {

    @Nonnull
    private BackpackType backpackType;
    @Nonnull
    private final List<BackpackUpgrade> upgrades;
    @Nonnull
    private BackpackSpecialty specialty;
    @Nonnull
    private ItemStackHandler stackHandler;

    private BackpackInfo(@Nonnull BackpackType backpackType, @Nonnull List<BackpackUpgrade> upgrades, @Nonnull BackpackSpecialty specialty, @Nonnull ItemStackHandler stackHandler) {
        Preconditions.checkNotNull(backpackType);
        Preconditions.checkNotNull(upgrades);
        Preconditions.checkNotNull(specialty);

        this.backpackType = backpackType;
        this.upgrades = upgrades;
        this.specialty = specialty;
        this.stackHandler = stackHandler;
    }

    public BackpackInfo(@Nonnull BackpackType backpackType, @Nonnull BackpackSpecialty specialty, @Nonnull ItemStackHandler stackHandler) {
        this(backpackType, Lists.newArrayList(), specialty, stackHandler);
    }

    private BackpackInfo() {
        //noinspection ConstantConditions - null/null is automatically registered, so we know it's always there.
        this(IronBackpacksHelper.getBackpackType(IronBackpacksHelper.NULL), Lists.newArrayList(), BackpackSpecialty.NONE, new ItemStackHandler());
    }

    @Nonnull
    public BackpackType getBackpackType() {
        return backpackType;
    }

    @Nonnull
    public ItemStackHandler getStackHandler() {
        return stackHandler;
    }

    public boolean hasUpgrade(@Nullable BackpackUpgrade backpackUpgrade) {
        return upgrades.contains(backpackUpgrade);
    }

    @Nonnull
    public BackpackInfo applyUpgrade(@Nullable BackpackUpgrade backpackUpgrade) {
        if (backpackUpgrade != null)
            upgrades.add(backpackUpgrade);

        return this;
    }

    @Nonnull
    public BackpackInfo removeUpgrade(@Nullable BackpackUpgrade backpackUpgrade) {
        if (backpackUpgrade != null)
            upgrades.remove(backpackUpgrade);

        return this;
    }

    @Nonnull
    public BackpackInfo removeUpgrade() {
        if (!upgrades.isEmpty())
            upgrades.remove(upgrades.size() - 1);

        return this;
    }

    public BackpackSpecialty getSpecialty() {
        return specialty;
    }

    public int getPointsUsed() {
        int used = 0;
        for (BackpackUpgrade backpackUpgrade : upgrades)
            used += backpackUpgrade.getApplicationCost();

        return used;
    }

    public int getMaxPoints() {
        return backpackType.getMaxPoints() + (specialty == BackpackSpecialty.UPGRADE ? 5 : 0);
    }

    // INBTSerializable

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();

        // Serialize backpack info
        tag.setString("backpackType", backpackType.getIdentifier().toString());
        tag.setString("specialty", specialty.name());

        // Serialize upgrades
        NBTTagList installedUpgrades = new NBTTagList();
        for (BackpackUpgrade backpackUpgrade : upgrades)
            installedUpgrades.appendTag(new NBTTagString(backpackUpgrade.getIdentifier().toString()));
        tag.setTag("installedUpgrades", installedUpgrades);

        // Serialize inventory
        tag.setTag("inventory", stackHandler.serializeNBT());

        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        // Deserialize backpack info
        backpackType = IronBackpacksHelper.getBackpackType(new ResourceLocation(nbt.getString("backpackType")));
        specialty = BackpackSpecialty.getSpecialty(nbt.getString("specialty"));

        // Deserialize upgrades
        NBTTagList installedUpgrades = nbt.getTagList("installedUpgrades", 8);
        for (int i = 0; i < installedUpgrades.tagCount(); i++) {
            ResourceLocation identifier = new ResourceLocation(installedUpgrades.getStringTagAt(i));
            BackpackUpgrade backpackUpgrade = IronBackpacksHelper.getUpgrade(identifier);
            if (!backpackUpgrade.isNull())
                upgrades.add(backpackUpgrade);
        }

        // Deserialize inventory
        stackHandler.deserializeNBT(nbt.getCompoundTag("inventory"));
    }

    @Nonnull
    public static BackpackInfo fromStack(@Nonnull ItemStack stack) {
        if (stack.isEmpty() || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("backpackInfo"))
            return new BackpackInfo();

        BackpackInfo backpackInfo = new BackpackInfo();
        backpackInfo.deserializeNBT(stack.getTagCompound().getCompoundTag("backpackInfo"));
        return backpackInfo;
    }
}
