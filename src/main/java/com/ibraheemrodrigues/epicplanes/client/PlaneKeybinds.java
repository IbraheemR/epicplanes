package com.ibraheemrodrigues.epicplanes.client;

import com.ibraheemrodrigues.epicplanes.Util;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

public class PlaneKeybinds {
    public static PlaneKeybinds INSTANCE = new PlaneKeybinds();

    public static FabricKeyBinding freecamKeybind;
    public boolean  freecamToggle = false;
    public boolean  freecamKeyPressed = false;
    private static String FREECAM_TOGGLE_MESSAGE_KEY = "key.epicplanes.freecam.toggle_message";

    // TODO: implement this in basic plane

    public static void clientInit() {
        freecamKeybind = FabricKeyBinding.Builder.create(
                Util.getID("freecam"),
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F,
                "Epic Planes Keybinds"
        ).build();

        KeyBindingRegistry.INSTANCE.register(freecamKeybind);

        ClientTickCallback.EVENT.register(e -> INSTANCE.handleFreecamKeyPress());
    }

    private void handleFreecamKeyPress() {
        if (freecamKeybind.wasPressed()) {
            this.freecamToggle = !this.freecamToggle;
                MinecraftClient.getInstance().player.sendMessage(new TranslatableText(FREECAM_TOGGLE_MESSAGE_KEY, this.freecamToggle ? "on" : "off", freecamKeybind.getBoundKey().getName()));
        }
        this.freecamKeyPressed = freecamKeybind.isPressed();
    }

}
