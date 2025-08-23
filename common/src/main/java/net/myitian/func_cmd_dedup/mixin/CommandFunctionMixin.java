package net.myitian.func_cmd_dedup.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import net.minecraft.command.SourcedCommandAction;
import net.minecraft.server.command.AbstractServerCommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.myitian.func_cmd_dedup.FunctionCommandDeduplicator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CommandFunction.class)
public interface CommandFunctionMixin {
    @Inject(method = "parse", at = @At("RETURN"), cancellable = true)
    private static <T extends AbstractServerCommandSource<T>> void parse(
            CommandDispatcher<T> dispatcher,
            T source,
            StringReader reader,
            CallbackInfoReturnable<SourcedCommandAction<T>> ci) {
        if (source instanceof ServerCommandSource scs
                && scs.getWorld() == null
                && scs.getEntity() == null
                && scs.getServer() == null) {
            ci.setReturnValue(
                    (SourcedCommandAction<T>) FunctionCommandDeduplicator.appendAction(
                            (SourcedCommandAction<ServerCommandSource>) ci.getReturnValue()));
        }
    }
}
