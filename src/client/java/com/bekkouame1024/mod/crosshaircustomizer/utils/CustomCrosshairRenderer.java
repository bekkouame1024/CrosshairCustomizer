package com.bekkouame1024.mod.crosshaircustomizer.utils;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

//? if =1.21.4 {
/*import net.minecraft.client.render.RenderLayer;
import java.util.function.Function;
*///?}

//? if >=1.21.6 <=1.21.8 {
import com.mojang.blaze3d.pipeline.RenderPipeline;
//?}

public class CustomCrosshairRenderer {
    //? if >=1.21 <=1.21.1 {
    /*public static void drawCrosshair(DrawContext context, Identifier texture, int centerX, int centerY, int width, int height) {
        int x = centerX - width / 2;
        int y = centerY - height / 2;

        context.drawTexture(texture, x, y, 0, 0, width, height, width, height);
    }
    *///?}

    //? if =1.21.4 {
    /*public static void drawCrosshair(DrawContext context, Function<Identifier, RenderLayer> renderLayers , Identifier texture, int centerX, int centerY, int width, int height) {
        int x = centerX - width / 2;
        int y = centerY - height / 2;

        context.drawTexture(renderLayers, texture, x, y, 0, 0, width, height, width, height);
    }
    *///?}
    
    //? if >=1.21.6 <=1.21.8 {
    public static void drawCrosshair(DrawContext context, RenderPipeline pipeline, Identifier texture, int centerX, int centerY, int width, int height) {
        int x = centerX - width / 2;
        int y = centerY - height / 2;
        
        context.drawTexture(pipeline, texture, x, y, 0, 0, width, height, width, height);
    }
    //?}
}
