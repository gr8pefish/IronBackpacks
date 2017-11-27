package gr8pefish.ironbackpacks.core;

import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class UpgradeEventHandler {

    @SubscribeEvent
    public static void onItemExpire(ItemExpireEvent event) {
        if (event.getEntityItem().getItem().getItem() instanceof IBackpack) {
            BackpackInfo info = ((IBackpack) event.getEntityItem().getItem().getItem()).getBackpackInfo(event.getEntityItem().getItem());
            if (info.hasUpgrade(RegistrarIronBackpacks.UPGRADE_EVERLASTING)) {
                event.setExtraLife(6000);
                event.getEntityItem().setNoDespawn();
                event.setCanceled(true);
            }
        }
    }
}
