package com.bekkouame1024.mod.crosshaircustomizer.managers;

import com.bekkouame1024.mod.crosshaircustomizer.ConfigManager;
import com.bekkouame1024.mod.crosshaircustomizer.CrosshairCustomizer;
import com.bekkouame1024.mod.crosshaircustomizer.ModConfig;
import com.bekkouame1024.mod.crosshaircustomizer.model.MenuType;
import com.bekkouame1024.mod.crosshaircustomizer.utils.FileNameValidator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class CrosshairManager {
    private static final Map<String, Identifier> CROSSHAIRS = new HashMap<>();
    private static MenuType currentMenuType = MenuType.MAIN;
    
    public static Identifier getCrosshair(String name) {
        return CROSSHAIRS.get(name);
    }
    
    public static List<String> getCrosshairNames() {
        return List.copyOf(CROSSHAIRS.keySet());
    }

    public static void setCurrentCrosshair(String name) {
        ModConfig config = CrosshairCustomizer.CONFIG;
        
        Identifier id = CROSSHAIRS.get(name);
        if (id != null) {
            config.currentCrosshair = id;
            ConfigManager.save(config);
        } else {
            CrosshairCustomizer.LOGGER.warn("Crosshair not found: {}", name);
        }
    }
    
    public static void setCurrentTargetCrosshair(String name) {
        ModConfig config = CrosshairCustomizer.CONFIG;
        
        Identifier id = CROSSHAIRS.get(name);
        if (id != null) {
            config.currentTargetCrosshair = id;
            ConfigManager.save(config);
        } else {
            CrosshairCustomizer.LOGGER.warn("Crosshair not found: {}", name);
        }
    }
    
    public static void resetCurrentCrosshairToDefault(){
        ModConfig config = CrosshairCustomizer.CONFIG;
        config.currentCrosshair = null;
        ConfigManager.save(config);
    }
    
    public static void resetCurrentTargetCrosshairToDefault(){
        ModConfig config = CrosshairCustomizer.CONFIG;
        config.currentTargetCrosshair = null;
        ConfigManager.save(config);
    }

    public static void importCrosshair(File imageFile) {
        if (imageFile == null || !imageFile.exists() || !imageFile.isFile()) {
            CrosshairCustomizer.LOGGER.warn("Import failed: file does not exist - {}", imageFile);
            return;
        }
        
        String crosshairName = imageFile.getName().substring(0,  imageFile.getName().length() - 4);
        
        if (!FileNameValidator.isValidFileName(crosshairName + ".png")) {
            CrosshairCustomizer.LOGGER.warn("Import failed: invalid crosshair name - {}", crosshairName);
            return;
        }

        File folder = new File("config/" + CrosshairCustomizer.MOD_ID + "/crosshairs");
        if (!folder.exists() && !folder.mkdirs()) {
            CrosshairCustomizer.LOGGER.error("Failed to create crosshairs folder.");
            return;
        }

        File destFile = new File(folder, crosshairName + ".png");

        try {
            BufferedImage img = ImageIO.read(imageFile);
            if (img == null) {
                CrosshairCustomizer.LOGGER.warn("Import failed: could not read image - {}", imageFile.getName());
                return;
            }
            ImageIO.write(img, "PNG", destFile);
            
            ModConfig config = CrosshairCustomizer.CONFIG;
            config.crosshairs.add(new ModConfig.CrosshairEntry(destFile.getName(), config.crosshairs.size()));
            ConfigManager.save(config);
            
            loadCrosshair(destFile);

            CrosshairCustomizer.LOGGER.info("Successfully imported crosshair: {}", destFile.getName());
        } catch (IOException e) {
            CrosshairCustomizer.LOGGER.error("Import failed: error copying image", e);
        }
    }

    public static void reloadCrosshairs() {
        ModConfig config = CrosshairCustomizer.CONFIG;
        ConfigManager.load();
        
        CROSSHAIRS.clear();
        
        checkCrosshairExists();

        File folder = new File("config/" + CrosshairCustomizer.MOD_ID + "/crosshairs");
        if (!folder.exists() && !folder.mkdirs()) {
            CrosshairCustomizer.LOGGER.error("Failed to create crosshairs folder.");
            return;
        }
        
        if(config.crosshairs == null || config.crosshairs.isEmpty()) {
            CrosshairCustomizer.LOGGER.info("No crosshairs defined in the configuration.");
            return;
        }
        
        for(ModConfig.CrosshairEntry entry : config.crosshairs) {
            File file = new File(folder, entry.file);
            
            if(!file.exists()) {
                CrosshairCustomizer.LOGGER.warn("Crosshair file not found: {}", file.getAbsolutePath());
                continue;
            }
            
            loadCrosshair(file);
        }
    }
    
    public static void deleteCrosshair(String name) {
        ModConfig config = CrosshairCustomizer.CONFIG;
        
        Identifier id = CROSSHAIRS.get(name);
        if (id == null) {
            CrosshairCustomizer.LOGGER.warn("Crosshair not found: {}", name);
            return;
        }
        
        File file = new File("config/" + CrosshairCustomizer.MOD_ID + "/crosshairs", name + ".png");
        if (file.exists() && !file.delete()) {
            CrosshairCustomizer.LOGGER.error("Failed to delete crosshair file: {}", file.getAbsolutePath());
            return;
        }
        
        config.crosshairs.removeIf(entry -> entry.file.equals(name + ".png"));
        if (config.currentCrosshair != null && config.currentCrosshair.equals(id)) {
            config.currentCrosshair = null;
        }
        ConfigManager.save(config);
        
        AbstractTexture texture = MinecraftClient.getInstance().getTextureManager().getTexture(id);
        if (texture instanceof NativeImageBackedTexture nativeImageBackedTexture) {
            nativeImageBackedTexture.close();
        }
        MinecraftClient.getInstance().getTextureManager().destroyTexture(id);
        
        CROSSHAIRS.remove(name);
        
        CrosshairCustomizer.LOGGER.info("Deleted crosshair: {}", name);
    }
    
    private static void loadCrosshair(File crosshairImageFile) {
        if (!crosshairImageFile.isFile()) {
            return;
        }
        
        String fileName = crosshairImageFile.getName();
        if (!FileNameValidator.isValidFileName(fileName)) {
            CrosshairCustomizer.LOGGER.warn("Invalid file name: {} (Only alphanumeric characters and underscores are allowed, and the extension must be .png)", crosshairImageFile.getName());
            return;
        }

        String name = fileName.substring(0, fileName.length() - 4);
        
        try {
            BufferedImage bufferedImage = ImageIO.read(crosshairImageFile);
            if (bufferedImage == null) {
                CrosshairCustomizer.LOGGER.error("Failed to read image file: {}", crosshairImageFile.getAbsolutePath());
                return;
            }

            NativeImage image = new NativeImage(bufferedImage.getWidth(), bufferedImage.getHeight(), true);
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    //? if >=1.21 <=1.21.1 {
                    /*image.setColor(x, y, bufferedImage.getRGB(x, y));
                    *///?}
                    
                    //? if >=1.21.4 <=1.21.8 {
                    image.setColorArgb(x, y, bufferedImage.getRGB(x, y));
                    //?}
                }
            }
            
            //? if >=1.21 <=1.21.4 {
            /*NativeImageBackedTexture texture = new NativeImageBackedTexture(image);
            *///?}
            
            //? if >=1.21.6 <=1.21.8 {
            NativeImageBackedTexture texture = new NativeImageBackedTexture(() -> UUID.randomUUID().toString(), image);
            //?}
            Identifier id = Identifier.of(CrosshairCustomizer.MOD_ID, name);

            MinecraftClient.getInstance().getTextureManager().registerTexture(id, texture);
            CROSSHAIRS.put(name, id);
        } catch (Exception e) {
            CrosshairCustomizer.LOGGER.error("Failed to load crosshair: {}", name, e);
        }
    }
    
    private static void checkCrosshairExists() {
        ModConfig config = CrosshairCustomizer.CONFIG;
        Iterator<ModConfig.CrosshairEntry> iterator = config.crosshairs.iterator();
        while (iterator.hasNext()) {
            ModConfig.CrosshairEntry entry = iterator.next();
            File file = new File("config/" + CrosshairCustomizer.MOD_ID + "/crosshairs", entry.file);
            if (file.exists()) {
                continue;
            }
            iterator.remove();
            ConfigManager.save(config);
            CrosshairCustomizer.LOGGER.warn("Removed missing crosshair from config: {}", entry.file);
        }
    }
    
    public static MenuType getCurrentMenuType() {
        return currentMenuType;
    }
    
    public static void setCurrentMenuType(MenuType menuType) {
        currentMenuType = menuType;
    }
}
