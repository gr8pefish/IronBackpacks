package gr8pefish.ironbackpacks.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class BackpackInfo implements INBTSerializable<NBTTagCompound> {

    @Nonnull
    private BackpackType backpackType;
    @Nonnull
    private final Set<BackpackUpgrade> upgrades;
    @Nonnegative
    private BackpackSpecialty specialty;


    private BackpackInfo(@Nonnull BackpackType backpackType, @Nonnull Set<BackpackUpgrade> upgrades, @Nonnull BackpackSpecialty specialty) {
        Preconditions.checkNotNull(backpackType);
        Preconditions.checkNotNull(upgrades);
        Preconditions.checkNotNull(specialty);

        this.backpackType = backpackType;
        this.upgrades = upgrades;
        this.specialty = specialty;
    }

    public BackpackInfo(@Nonnull BackpackType backpackType, BackpackSpecialty specialty) {
        this(backpackType, Sets.newHashSet(), specialty);
    }

    private BackpackInfo() {
        //noinspection ConstantConditions - null/null is automatically registered, so we know it's always there.
        this(IronBackpacksHelper.getBackpackType(IronBackpacksHelper.NULL), Sets.newHashSet(), BackpackSpecialty.NONE);
    }

    @Nonnull
    public BackpackType getBackpackType() {
        return backpackType;
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

        tag.setString("backpackType", backpackType.getIdentifier().toString());
        tag.setString("specialty", specialty.name());

        // Serialize upgrades
        NBTTagList installedUpgrades = new NBTTagList();
        for (BackpackUpgrade backpackUpgrade : upgrades)
            installedUpgrades.appendTag(new NBTTagString(backpackUpgrade.getIdentifier().toString()));
        tag.setTag("installedUpgrades", installedUpgrades);

        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        backpackType = IronBackpacksHelper.getBackpackType(new ResourceLocation(nbt.getString("backpackType")));
        specialty = BackpackSpecialty.getSpecialty(nbt.getString("specialty"));

        NBTTagList installedUpgrades = nbt.getTagList("installedUpgrades", 8);
        for (int i = 0; i < installedUpgrades.tagCount(); i++) {
            ResourceLocation identifier = new ResourceLocation(installedUpgrades.getStringTagAt(i));
            BackpackUpgrade backpackUpgrade = IronBackpacksHelper.getUpgrade(identifier);
            if (!backpackUpgrade.isNull())
                upgrades.add(backpackUpgrade);
        }
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
