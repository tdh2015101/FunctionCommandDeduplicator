package net.myitian.func_cmd_dedup;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.command.SourcedCommandAction;
import net.minecraft.server.command.AbstractServerCommandSource;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FunctionCommandDeduplicator {
    public static final String MOD_ID = "func_cmd_dedup";
    public static final Logger LOGGER = LoggerFactory.getLogger("FunctionCommandDeduplicator");
    private static final Object2ObjectOpenHashMap<String, SourcedCommandAction<ServerCommandSource>> actionCache = new Object2ObjectOpenHashMap<>();
    private static int counter = 0;
    private static long savedLength = 0;
    private static long usedLength = 0;

    public static void init() {
        LOGGER.info("{} loaded!", MOD_ID);
    }

    public static synchronized SourcedCommandAction<ServerCommandSource> appendAction(SourcedCommandAction<ServerCommandSource> action) {
        String command = action.toString();
        SourcedCommandAction<ServerCommandSource> v = actionCache.get(command);
        counter++;
        if (v != null) {
            savedLength += command.length();
            return v;
        } else {
            usedLength += command.length();
            actionCache.put(command, action);
            return action;
        }
    }

    public static void clearActionCache() {
        LOGGER.info("{} saved {}/{} SourcedCommandAction<ServerCommandSource> instances (total command string length {}/{})",
                MOD_ID,
                counter - actionCache.size(),
                counter,
                savedLength,
                savedLength + usedLength);
        actionCache.clear();
        actionCache.trim();
        counter = 0;
        savedLength = 0;
        usedLength = 0;
    }
}
