package net.myitian.func_cmd_dedup.forge;

import net.minecraftforge.fml.common.Mod;
import net.myitian.func_cmd_dedup.FunctionCommandDeduplicator;

@Mod(FunctionCommandDeduplicator.MOD_ID)
public class FunctionCommandDeduplicatorForge {
    public FunctionCommandDeduplicatorForge() {
        FunctionCommandDeduplicator.init();
    }
}
