package com.bekkouame1024.mod.crosshaircustomizer;

import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.StackLayout;
import io.wispforest.owo.ui.core.*;
import io.wispforest.owo.ui.event.MouseDown;
import io.wispforest.owo.ui.util.UISounds;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class UiComponentFactory {
    public static StackLayout button(Identifier textureId, ButtonSprite buttonSprite, Text tooltip, MouseDown onMouseDown) {
        StackLayout container = Containers.stack(Sizing.fixed(20), Sizing.fixed(20));
        container.margins(Insets.of(2)).surface(Surface.flat(0xD0101010)).tooltip(tooltip);
        container.horizontalAlignment(HorizontalAlignment.CENTER).verticalAlignment(VerticalAlignment.CENTER);
        
        container.mouseDown().subscribe((mouseX, mouseY, button) -> {
            UISounds.playButtonSound();
            
            if (onMouseDown != null) {
                return onMouseDown.onMouseDown(mouseX, mouseY, button);
            }

            return false;
        });

        TextureComponent texture = Components.texture(textureId, buttonSprite.u, buttonSprite.v, buttonSprite.regionWidth, buttonSprite.regionHeight);
        texture.sizing(Sizing.fixed(16));
        
        container.child(texture);

        return container;
    }

    public record ButtonSprite(int u, int v, int regionWidth, int regionHeight) {}
}
