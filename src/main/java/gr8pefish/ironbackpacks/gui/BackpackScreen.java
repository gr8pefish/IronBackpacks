package gr8pefish.ironbackpacks.gui;

import gr8pefish.ironbackpacks.IronBackpacksClient;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import org.lwjgl.glfw.GLFW;

public class BackpackScreen extends CottonInventoryScreen<BackpackController> {
    private final PlayerEntity player;

    public BackpackScreen(BackpackController container, PlayerEntity player) {
        super(container, player);
        this.player = player;
    }

    @Override
    public boolean keyPressed(int ch, int keyCode, int modifiers) {
        boolean result = super.keyPressed(ch, keyCode, modifiers);
        if (ch == GLFW.GLFW_KEY_ESCAPE || MinecraftClient.getInstance().options.keyInventory.matchesKey(ch, keyCode)) {
            player.getEntityWorld().playSound(player.getX(), player.getY(), player.getZ(),
                    IronBackpacksClient.BACKPACK_CLOSE, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
        }
        return result;
    }
}
