package com.bekkouame1024.mod.crosshaircustomizer;

import io.wispforest.owo.ui.core.OwoUIDrawContext;
import io.wispforest.owo.ui.core.ParentComponent;
import io.wispforest.owo.ui.core.Surface;

public class TopOutlineSurface implements Surface {
    private final int color;

    public TopOutlineSurface(int color) {
        this.color = color;
    }
    
    @Override
    public void draw(OwoUIDrawContext context, ParentComponent component) {
        int x = component.x();
        int y = component.y();
        int width = component.width();
        context.fill(x, y, x + width, y + 1, color);
    }

    @Override
    public Surface and(Surface surface) {
        return Surface.super.and(surface);
    }
}