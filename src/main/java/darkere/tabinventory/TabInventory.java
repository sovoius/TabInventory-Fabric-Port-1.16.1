package darkere.tabinventory;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.AbstractCommandBlockScreen;
import org.lwjgl.glfw.GLFW;

public class TabInventory implements ClientModInitializer {

    private boolean wasTabDown = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    private void onTick(MinecraftClient client) {
        if (client.player == null || client.currentScreen == null) return;

        boolean tabDown = GLFW.glfwGetKey(
                client.getWindow().getHandle(),
                GLFW.GLFW_KEY_TAB
        ) == GLFW.GLFW_PRESS;

        // Edge-triggered key press (matches Forge behavior)
        if (tabDown && !wasTabDown) {
            if (client.options.keyInventory.matchesKey(GLFW.GLFW_KEY_TAB, 0)
                    && !(client.currentScreen instanceof ChatScreen)
                    && !(client.currentScreen instanceof AbstractCommandBlockScreen)) {

                client.player.closeScreen();
            }
        }

        wasTabDown = tabDown;
    }
}
