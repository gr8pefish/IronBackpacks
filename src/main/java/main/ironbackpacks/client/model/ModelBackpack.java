package main.ironbackpacks.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBackpack extends ModelBase {

    public ModelRenderer main;
    public ModelRenderer top;
    public ModelRenderer front;
    public ModelRenderer right;
    public ModelRenderer left;

    public int getModelHeight() { return 12; }

    public ModelBackpack(boolean addStraps) {

        textureWidth = 32;
        textureHeight = 32;

        main = new ModelRenderer(this, 0, 8);
        main.addBox(-5F, 0F, -3F, 10, 9, 5);
        main.setRotationPoint(0F, 15F, 0F);

        top = new ModelRenderer(this, 0, 0);
        top.addBox(-5F, -3F, 0F, 10, 3, 5);
        top.setRotationPoint(0F, 15F, -3F);

        front = new ModelRenderer(this, 0, 22);
        front.addBox(-4F, 0F, 0F, 8, 6, 2);
        front.setRotationPoint(0F, 17F, 2F);

        if (addStraps) {
            right = new ModelRenderer(this, 20, 22);
            right.addBox(0F, 0F, -1F, 1, 8, 1);
            right.setRotationPoint(-4F, 13F, -3F);

            left = new ModelRenderer(this, 20, 22);
            left.addBox(0F, 0F, -1F, 1, 8, 1);
            left.setRotationPoint(3F, 13F, -3F);
        }

    }

    public void renderAll() {
        float scale = 1 / 16.0F;
        render(scale);
    }
    protected void render(float scale) {
        render(main, scale);
        render(top, scale);
        render(front, scale);
        render(right, scale);
        render(left, scale);
    }
    protected void render(ModelRenderer renderer, float scale) {
        if (renderer != null)
            renderer.render(scale);
    }

    public void setLidRotation(float angle) {
        if (top != null)
            top.rotateAngleX = angle;
    }

}

