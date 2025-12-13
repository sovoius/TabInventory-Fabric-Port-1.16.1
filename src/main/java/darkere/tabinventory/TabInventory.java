package darkere.tabinventory;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.CommandBlockScreen;
import org.lwjgl.glfw.GLFW;

public class TabInventory implements ClientModInitializer {

    private boolean wasTabDown = false;

    @Override
    public void onInitializeClient() {
        // Register a simple tick loop via MinecraftClient
        MinecraftClient.getInstance().execute(this::tickLoop);
    }

    private void tickLoop() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player != null && client.currentScreen != null) {
            boolean tabDown = GLFW.glfwGetKey(
                    client.getWindow().getHandle(),
                    GLFW.GLFW_KEY_TAB
            ) == GLFW.GLFW_PRESS;

            if (tabDown && !wasTabDown) {
                // Must match inventory keybind
                if (client.options.inventoryKey.matchesKey(GLFW.GLFW_KEY_TAB, 0)
                        && !(client.currentScreen instanceof ChatScreen)
                        && !(client.currentScreen instanceof CommandBlockScreen)) {
                    client.player.closeScreen();
                }
            }

            wasTabDown = tabDown;
        }

        // Re-run next tick
        client.execute(this::tickLoop);
    }
}
