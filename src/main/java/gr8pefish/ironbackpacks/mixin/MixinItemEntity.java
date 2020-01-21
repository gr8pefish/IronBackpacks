package gr8pefish.ironbackpacks.mixin;

import gr8pefish.ironbackpacks.item.BaseBackpackItem;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static gr8pefish.ironbackpacks.IronBackpacks.UPGRADE_EVERLASTING;
import static gr8pefish.ironbackpacks.IronBackpacks.UPGRADE_FIREPROOF;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity {
    @Shadow
    private int age;

    @Shadow public abstract ItemStack getStack();

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V", at = @At("RETURN"))
    private void init(World world, double x, double y, double z, ItemStack stack, CallbackInfo ci) {
        if (stack.getItem() instanceof BaseBackpackItem) {
            if (((BaseBackpackItem) stack.getItem()).hasUpgrade(stack, UPGRADE_EVERLASTING)) {
                age = -32768; // Don't despawn
            }
        }
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;isInvulnerableTo(Lnet/minecraft/entity/damage/DamageSource;)Z"), cancellable = true)
    public void isFireproof(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = this.getStack();
        if (stack.getItem() instanceof BaseBackpackItem) {
            if (((BaseBackpackItem) stack.getItem()).hasUpgrade(stack, UPGRADE_FIREPROOF)) {
                cir.setReturnValue(true);
            }
        }
    }
}
