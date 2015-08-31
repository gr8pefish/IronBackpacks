package main.ironbackpacks.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

public class ModelBackpack extends ModelBase {

    private ArrayList<ModelRenderer> parts;

    public ModelBackpack() {
        textureWidth = 32;
        textureHeight = 32;

        parts = new ArrayList<ModelRenderer>();

        ModelRenderer main = new ModelRenderer(this, 0, 0);
        main.addBox(-2, -3, -1, 4, 6, 2);
        main.setRotationPoint(0, 0, 0);
        parts.add(main);

        ModelRenderer bottomPouch = new ModelRenderer(this, 0, 8);
        bottomPouch.addBox(-1.5F, -1, -0.5F, 3, 2, 1);
        bottomPouch.setRotationPoint(0, 1.5F, -1.5F);
        parts.add(bottomPouch);

        ModelRenderer bottomPouchAddition = new ModelRenderer(this, 0, 11);
        bottomPouchAddition.addBox(-1.5F, -1F, -0.5F, 3, 2, 1);             //TODO: config option for HD or non hd model
        bottomPouchAddition.setRotationPoint(0, .5F, -1F);
        parts.add(bottomPouchAddition);

    }

    @Override
    public void render(Entity entity, float val1, float val2, float val3, float val4, float val5, float mult) {
        for (ModelRenderer part : parts) {
            part.render(mult);
        }
    }

}
