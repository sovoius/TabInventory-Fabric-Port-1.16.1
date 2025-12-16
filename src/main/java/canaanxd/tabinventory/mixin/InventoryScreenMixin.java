package canaanxd.tabinventory.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public abstract class InventoryScreenMixin {

    @Inject(
        method = "keyPressed",
        at = @At("HEAD"),
        cancellable = true
    )
    private void tabinventory$onKeyPressed(
        int keyCode,
        int scanCode,
        int modifiers,
        CallbackInfoReturnable<Boolean> cir
    ) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;
        if (client.currentScreen == null) return;

        // Only affect survival container screens (inventory, chests, crafting table, etc.)
        if (!(client.currentScreen instanceof HandledScreen)) return;

        // Do not interfere with chat
        if (client.currentScreen instanceof ChatScreen) return;

        // Only TAB
        if (keyCode != GLFW.GLFW_KEY_TAB) return;

        // Must match inventory keybind
        if (!client.options.keyInventory.matchesKey(keyCode, scanCode)) return;

        client.player.closeHandledScreen();
        cir.setReturnValue(true);
    }
}
