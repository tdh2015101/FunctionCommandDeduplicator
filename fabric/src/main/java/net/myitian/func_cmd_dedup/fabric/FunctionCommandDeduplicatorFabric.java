package net.myitian.func_cmd_dedup.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.myitian.func_cmd_dedup.FunctionCommandDeduplicator;

public class FunctionCommandDeduplicatorFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FunctionCommandDeduplicator.init();
    }
}
