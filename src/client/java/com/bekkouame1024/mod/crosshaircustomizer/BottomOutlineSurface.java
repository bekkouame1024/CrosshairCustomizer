package com.bekkouame1024.mod.crosshaircustomizer;

import io.wispforest.owo.ui.core.OwoUIDrawContext;
import io.wispforest.owo.ui.core.ParentComponent;
import io.wispforest.owo.ui.core.Surface;

public class BottomOutlineSurface implements Surface {
    private final int color;

    public BottomOutlineSurface(int color) {
        this.color = color;
    }
    
    @Override
    public void draw(OwoUIDrawContext context, ParentComponent component) {
        int x = component.x();
        int y = component.y();
        int width = component.width();
        int height = component.height();
        
        context.fill(x, y + height - 1, x + width, y + height, color);
    }

    @Override
    public Surface and(Surface surface) {
        return Surface.super.and(surface);
    }
}