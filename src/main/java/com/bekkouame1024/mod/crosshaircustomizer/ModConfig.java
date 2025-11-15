package com.bekkouame1024.mod.crosshaircustomizer;


import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    public int version = 1;
    public Identifier currentCrosshair = null;
    public Identifier currentTargetCrosshair = null;
    public List<CrosshairEntry> crosshairs = new ArrayList<>();

    public static class CrosshairEntry {
        public String file;
        public int order;

        public CrosshairEntry() {}

        public CrosshairEntry(String file, int order) {
            this.file = file;
            this.order = order;
        }
    }
}
