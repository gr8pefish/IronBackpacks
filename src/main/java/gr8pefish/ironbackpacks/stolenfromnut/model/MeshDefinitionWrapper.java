package gr8pefish.ironbackpacks.stolenfromnut.model;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class MeshDefinitionWrapper implements ItemMeshDefinition {

    private final IModeled.Advanced modeledItem;

    public MeshDefinitionWrapper(IModeled.Advanced modeledItem) {
        this.modeledItem = modeledItem;
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        return modeledItem.getModelLocation(stack);
    }
}
