package darkere.tabinventory;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.AbstractCommandBlockScreen;
import org.lwjgl.glfw.GLFW;

public class TabInventory implements ClientModInitializer {

    private boolean wasTabDown = false;

    @Override
    public void onInitializeClient() {
        MinecraftClient.getInstance().execute(this::tickLoop);
    }

    private void tickLoop() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player != null && client.currentScreen != null) {
            boolean tabDown = GLFW.glfwGetKey(
                    client.getWindow().getHandle(),
                    GLFW.GLFW_KEY_TAB
            ) == GLFW.GLFW_PRESS;

            // Fire once per key press
            if (tabDown && !wasTabDown) {

                // Match inventory keybind (1.16.1 uses keyInventory)
                if (client.options.keyInventory.matchesKey(GLFW.GLFW_KEY_TAB, 0)
                        && !(client.currentScreen instanceof ChatScreen)
                        && !(client.currentScreen instanceof AbstractCommandBlockScreen)) {

                    client.player.closeScreen();
                }
            }

            wasTabDown = tabDown;
        }

        // Schedule next tick
        client.execute(this::tickLoop);
    }
}
