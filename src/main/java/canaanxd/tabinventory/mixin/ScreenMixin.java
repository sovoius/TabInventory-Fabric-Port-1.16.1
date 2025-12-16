package canaanxd.tabinventory.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractCommandBlockScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "keyPressed", at = @At("TAIL"), cancellable = true)
    private void tabinventory$onKeyPressed(
            int keyCode,
            int scanCode,
            int modifiers,
            CallbackInfoReturnable<Boolean> cir
    ) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;

        // TAB key only
        if (keyCode != GLFW.GLFW_KEY_TAB) return;

        // Must match the inventory keybind (1.16.1 uses keyInventory)
        if (!client.options.keyInventory.matchesKey(keyCode, scanCode)) return;

        // Exclusions (match original Forge behavior)
        if ((Object) this instanceof ChatScreen) return;
        if ((Object) this instanceof AbstractCommandBlockScreen) return;

        // Close AFTER vanilla handling, then consume the key so keybind logic doesn't re-open it
        client.player.closeScreen();
        cir.setReturnValue(true);
    }
}
