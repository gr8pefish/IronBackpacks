package main.ironbackpacks.integration;

import net.minecraft.entity.player.EntityPlayer;
import openblocks.common.entity.EntityHangGlider;

public class OpenBlocksGliderSupport extends OpenBlocksNoSupport {

    @Override
    public boolean isGliding(EntityPlayer player){
        return !EntityHangGlider.isGliderDeployed(player);
    }
}
