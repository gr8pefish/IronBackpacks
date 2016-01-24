package gr8pefish.ironbackpacks.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

public class ModelBackpackHD extends ModelBase {

    private ArrayList<ModelRenderer> parts;

    public ModelBackpackHD() {
        textureWidth = 32;
        textureHeight = 32;

        parts = new ArrayList<>();

        ModelRenderer main = new ModelRenderer(this, 0, 0);
        main.addBox(-2, -3, -1, 4, 6, 2);
        main.setRotationPoint(0, 0, 0);
        parts.add(main);

        ModelRenderer bottomPouch = new ModelRenderer(this, 0, 8);
        bottomPouch.addBox(-1.5F, -1, -0.5F, 3, 2, 1);
        bottomPouch.setRotationPoint(0, 1.5F, -1.5F);
        parts.add(bottomPouch);

        ModelRenderer bottomPouchAddition = new ModelRenderer(this, 0, 11);
        bottomPouchAddition.addBox(-1.5F, -1, -0.5F, 3, 2, 1);
        bottomPouchAddition.setRotationPoint(0, .2F, -1.1F);
        parts.add(bottomPouchAddition);

        ModelRenderer bottomPouchAddition2 = new ModelRenderer(this, 0, 8);
        bottomPouchAddition2.addBox(-1.5F, -1, -0.5F, 3, 2, 1);
        bottomPouchAddition2.setRotationPoint(0, -.3F, -0.7F);
        parts.add(bottomPouchAddition2);

        ModelRenderer sidePouch1 = new ModelRenderer(this, 0, 14);
        sidePouch1.addBox(-0.5F, -1, -0.5F, 1, 2, 1);
        sidePouch1.setRotationPoint(2, 1.5F, -0.2F);
        parts.add(sidePouch1);

        ModelRenderer sidePouch2 = new ModelRenderer(this, 0, 14);
        sidePouch2.addBox(-0.5F, -1, -0.5F, 1, 2, 1);
        sidePouch2.setRotationPoint(-2, 1.5F, -0.2F);
        parts.add(sidePouch2);

    }

    @Override
    public void render(Entity entity, float val1, float val2, float val3, float val4, float val5, float mult) {
        for (ModelRenderer part : parts) {
            part.render(mult);
        }
    }

}

