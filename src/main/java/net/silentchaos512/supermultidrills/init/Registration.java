package net.silentchaos512.supermultidrills.init;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.silentchaos512.supermultidrills.SuperMultiDrills;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Registration {
    public static final DeferredRegister<Item> ITEMS = create(ForgeRegistries.ITEMS);
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = create(ForgeRegistries.RECIPE_SERIALIZERS);

    private Registration() {throw new IllegalAccessError("Utility class");}

    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);

        ModItems.register();
        ModRecipes.register();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Item> List<T> getItems(Class<T> clazz) {
        return ITEMS.getEntries().stream()
                .map(RegistryObject::get)
                .filter(clazz::isInstance)
                .map(item -> (T) item)
                .collect(Collectors.toList());
    }

    public static List<Item> getItems(Predicate<Item> predicate) {
        return ITEMS.getEntries().stream()
                .map(RegistryObject::get)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return new DeferredRegister<>(registry, SuperMultiDrills.MOD_ID);
    }
}
