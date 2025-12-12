package darkere.tabinventory;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.CommandBlockScreen;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class TabInventory implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    private void onTick(MinecraftClient client) {
        if (client.player == null) return;
        if (client.currentScreen == null) return;

        // Must be TAB
        if (!KeyBinding.isKeyPressed(GLFW.GLFW_KEY_TAB)) return;

        // Must match inventory keybind
        if (!client.options.inventoryKey.matchesKey(GLFW.GLFW_KEY_TAB, 0)) return;

        // Must not be chat or command block
        if (client.currentScreen instanceof ChatScreen) return;
        if (client.currentScreen instanceof CommandBlockScreen) return;

        client.player.closeScreen();
    }
}
