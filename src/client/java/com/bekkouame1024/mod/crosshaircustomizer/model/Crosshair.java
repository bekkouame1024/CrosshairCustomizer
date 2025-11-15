package com.bekkouame1024.mod.crosshaircustomizer.model;

import net.minecraft.util.Identifier;

import java.io.File;

public class Crosshair {
    private final String name;
    private final Identifier textureId;
    private final File file;
    
    public Crosshair(String name, Identifier textureId, File file) {
        this.name = name;
        this.textureId = textureId;
        this.file = file;
    }
    
    public String getName() {
        return name;
    }
    
    public Identifier getTextureId() {
        return textureId;
    }
    
    public File getFile() {
        return file;
    }
}
