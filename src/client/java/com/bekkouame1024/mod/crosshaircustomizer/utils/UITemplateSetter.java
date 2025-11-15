package com.bekkouame1024.mod.crosshaircustomizer.utils;

import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.core.Insets;

public class UITemplateSetter {
    public static void setButtonRenderer(ButtonComponent button) {
        button.textShadow(false)
                .renderer(ButtonComponent.Renderer.flat(0xD0101010, 0xD0101010, 0xD0101010))
                .margins(Insets.of(2));
    }
}
