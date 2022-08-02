package com.glyceryl6.falling.json;

import com.glyceryl6.falling.CustomGravityBlock;
import com.glyceryl6.falling.json.objects.BlockListConfig;
import com.glyceryl6.falling.json.objects.BlockListEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {

    private static final Gson gson = new Gson();
    private static final List<BlockListEntry> blockListEntries = new ArrayList<>();
    public static BlockListConfig blockListConfig = new BlockListConfig(blockListEntries);
    public static final File JSON_FILE = new File(FMLPaths.CONFIGDIR.get() + "/falling_blocks/blockList.json");

    public static void setup() {
        createDirectory();
        if (!JSON_FILE.exists()) {
            writeJson(JSON_FILE);
        }
        readJson(JSON_FILE);
    }

    public static void reload() {
        writeJson(JSON_FILE);
        readJson(JSON_FILE);
    }

    public static boolean containsEntry(BlockListEntry entry) {
        for (BlockListEntry blockListEntry : blockListConfig.getBlockListConfig()) {
            if (blockListEntry.getBlock().equals(entry.getBlock())) {
                return true;
            }
        }
        return false;
    }

    public static boolean addEntry(BlockListEntry entry) {
        if (!containsEntry(entry)) {
            blockListConfig.getBlockListConfig().add(entry);
            reload();
            return true;
        }
        return false;
    }

    public static boolean removeEntry(String entryName) {
        if (containsEntry(new BlockListEntry(entryName))) {
            blockListConfig.getBlockListConfig().removeIf(entry -> entry.getBlock().equals(entryName));
            reload();
            return true;
        }
        return false;
    }

    public static void writeJson(File jsonFile) {
        try (Writer writer = new FileWriter(jsonFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(blockListConfig, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readJson(File jsonFile) {
        try (Reader reader = new FileReader(jsonFile)) {
            blockListConfig = gson.fromJson(reader, BlockListConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectory() {
        Path configPath = FMLPaths.CONFIGDIR.get();
        Path compostConfigPath = Paths.get(configPath.toAbsolutePath().toString(), CustomGravityBlock.MOD_ID);
        try {Files.createDirectory(compostConfigPath);
        } catch (IOException ignored) {}
    }

    public static void removeNullBlock() {
        for (BlockListEntry entry : JsonHandler.blockListConfig.getBlockListConfig()) {
            ResourceLocation resourceLocation = new ResourceLocation(entry.getBlock());
            Block block = ForgeRegistries.BLOCKS.getValue(resourceLocation);
            if (block == null || block instanceof FallingBlock) {
                removeEntry(entry.getBlock());
            }
        }
    }

}