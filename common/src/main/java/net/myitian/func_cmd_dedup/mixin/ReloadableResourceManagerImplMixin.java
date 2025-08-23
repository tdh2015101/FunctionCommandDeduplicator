package net.myitian.func_cmd_dedup.mixin;

import net.minecraft.registry.CombinedDynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.ServerDynamicRegistryType;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceReload;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.DataPackContents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Unit;
import net.myitian.func_cmd_dedup.FunctionCommandDeduplicator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(DataPackContents.class)
public class ReloadableResourceManagerImplMixin {
    @Inject(method = "reload", at = @At("HEAD"))
    private static void beforeReload(
            ResourceManager resourceManager,
            CombinedDynamicRegistries<ServerDynamicRegistryType> dynamicRegistries,
            List<Registry.PendingTagLoad<?>> pendingTagLoads,
            FeatureSet enabledFeatures,
            CommandManager.RegistrationEnvironment environment,
            int functionPermissionLevel,
            Executor prepareExecutor,
            Executor applyExecutor,
            CallbackInfoReturnable<CompletableFuture<DataPackContents>> ci) {
        FunctionCommandDeduplicator.LOGGER.info("{} started", FunctionCommandDeduplicator.MOD_ID);
    }

    @Inject(method = "reload", at = @At("RETURN"))
    private static void afterReload(
            ResourceManager resourceManager,
            CombinedDynamicRegistries<ServerDynamicRegistryType> dynamicRegistries,
            List<Registry.PendingTagLoad<?>> pendingTagLoads,
            FeatureSet enabledFeatures,
            CommandManager.RegistrationEnvironment environment,
            int functionPermissionLevel,
            Executor prepareExecutor,
            Executor applyExecutor,
            CallbackInfoReturnable<CompletableFuture<DataPackContents>> ci) {
        ci.getReturnValue().whenComplete(
                (object, throwable) -> FunctionCommandDeduplicator.clearActionCache());
    }
}
