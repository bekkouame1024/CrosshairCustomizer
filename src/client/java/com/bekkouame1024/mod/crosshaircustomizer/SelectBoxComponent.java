package com.bekkouame1024.mod.crosshaircustomizer;

import io.wispforest.owo.ui.component.SmallCheckboxComponent;
import io.wispforest.owo.ui.core.OwoUIDrawContext;
import io.wispforest.owo.ui.core.Sizing;

public class SelectBoxComponent extends SmallCheckboxComponent {
    private static int CHECKBOX_SIZE = 12;
    
    public SelectBoxComponent(int size) {
        super();
        CHECKBOX_SIZE = size;
        this.width = CHECKBOX_SIZE;
        this.height = CHECKBOX_SIZE;
    }

    @Override
    public void draw(OwoUIDrawContext context, int mouseX, int mouseY, float partialTicks, float delta) {
        drawRectangleCheckbox(context);
    }

    private void drawRectangleCheckbox(OwoUIDrawContext context) {
        if (this.checked) {
            context.fill(this.x, this.y, this.x + CHECKBOX_SIZE, this.y + CHECKBOX_SIZE, 0xFFFFFFFF);
        }else{
            context.drawRectOutline(this.x, this.y, this.width, this.height, 0xFFFFFFFF);
        }
    }

    @Override
    protected int determineHorizontalContentSize(Sizing sizing) {
        return CHECKBOX_SIZE;
    }

    @Override
    protected int determineVerticalContentSize(Sizing sizing) {
        return CHECKBOX_SIZE;
    }
}
