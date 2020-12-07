package net.silentchaos512.supermultidrills;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.silentchaos512.gear.util.GearGenerator;
import net.silentchaos512.supermultidrills.init.SmdItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Random;

@Mod(SuperMultiDrills.MOD_ID)
public class SuperMultiDrills {
    public static final String MOD_ID = "supermultidrills";
    public static final String MOD_NAME = "Super Multi-Drills";

    public static final Random random = new Random();
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static SuperMultiDrills INSTANCE;
    public static IProxy PROXY;

    public SuperMultiDrills() {
        INSTANCE = this;
        PROXY = DistExecutor.safeRunForDist(() -> SideProxy.Client::new, () -> SideProxy.Server::new);
    }

    public static String getVersion() {
        Optional<? extends ModContainer> o = ModList.get().getModContainerById(MOD_ID);
        if (o.isPresent()) {
            return o.get().getModInfo().getVersion().toString();
        }
        return "0.0.0";
    }

    public static boolean isDevBuild() {
        // TODO: Is there a better way? Guess it works though...
        String version = getVersion();
        return "NONE".equals(version);
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return GearGenerator.create(SmdItems.DRILL.get(), 3);
        }
    };
}
