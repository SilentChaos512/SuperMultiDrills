package net.silentchaos512.supermultidrills.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

@JeiPlugin
public class SuperMultiDrillsPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_UID = SuperMultiDrills.getId("plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_UID;
    }
}
