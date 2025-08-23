package net.myitian.func_cmd_dedup.neoforge;

import net.neoforged.fml.common.Mod;
import net.myitian.func_cmd_dedup.FunctionCommandDeduplicator;

@Mod(FunctionCommandDeduplicator.MOD_ID)
public class FunctionCommandDeduplicatorNeoForge {
    public FunctionCommandDeduplicatorNeoForge() {
        FunctionCommandDeduplicator.init();
    }
}
