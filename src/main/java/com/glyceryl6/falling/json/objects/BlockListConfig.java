package com.glyceryl6.falling.json.objects;

import java.util.List;

public class BlockListConfig {

    protected List<BlockListEntry> blockListEntries;

    public BlockListConfig(List<BlockListEntry> blockListEntries) {
        this.blockListEntries = blockListEntries;
    }

    public List<BlockListEntry> getBlockListConfig() {
        return blockListEntries;
    }

}