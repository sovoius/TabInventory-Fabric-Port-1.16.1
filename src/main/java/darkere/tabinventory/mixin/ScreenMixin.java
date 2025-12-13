package darkere.tabinventory.mixin;

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

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void tabinventory$onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client == null || client.player == null) return;

        // TAB key
        if (keyCode != GLFW.GLFW_KEY_TAB) return;

        // Must match inventory keybind
        if (!client.options.keyInventory.matchesKey(keyCode, scanCode)) return;

        // Exclusions (match Forge mod exactly)
        if ((Object) this instanceof ChatScreen) return;
        if ((Object) this instanceof AbstractCommandBlockScreen) return;

        client.player.closeScreen();
        cir.setReturnValue(true); // consume key
    }
}
