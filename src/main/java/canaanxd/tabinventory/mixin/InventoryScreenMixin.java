package canaanxd.tabinventory.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.AbstractCommandBlockScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {

    @Inject(method = "keyPressed", at = @At("TAIL"), cancellable = true)
    private void tabinventory$onKeyPressed(
            int keyCode,
            int scanCode,
            int modifiers,
            CallbackInfoReturnable<Boolean> cir
    ) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;

        // Only TAB
        if (keyCode != GLFW.GLFW_KEY_TAB) return;

        // Must match inventory keybind
        if (!client.options.keyInventory.matchesKey(keyCode, scanCode)) return;

        // Safety exclusions (parity with original Forge mod)
        if ((Object) this instanceof ChatScreen) return;
        if ((Object) this instanceof AbstractCommandBlockScreen) return;

        // Close inventory AFTER InventoryScreen logic
        client.player.closeScreen();

        // Consume key so nothing else re-triggers
        cir.setReturnValue(true);
    }
}
