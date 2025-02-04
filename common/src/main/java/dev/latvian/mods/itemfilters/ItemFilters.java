package dev.latvian.mods.itemfilters;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.Registries;
import dev.architectury.utils.EnvExecutor;
import dev.latvian.mods.itemfilters.api.ItemFiltersItems;
import dev.latvian.mods.itemfilters.client.ItemFiltersClient;
import dev.latvian.mods.itemfilters.gui.InventoryFilterMenu;
import dev.latvian.mods.itemfilters.net.ItemFiltersNet;
import net.fabricmc.api.EnvType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemFilters {
	public static final String MOD_ID = "itemfilters";

	public static ItemFiltersCommon proxy;
	public static CreativeModeTab creativeTab;

	public void setup() {
		proxy = EnvExecutor.getEnvSpecific(() -> ItemFiltersClient::new, () -> ItemFiltersCommon::new);
		creativeTab = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "main"), () -> new ItemStack(ItemFiltersItems.ALWAYS_TRUE.get()));

		ItemFiltersItems.init();
		InventoryFilterMenu.TYPE = Registries.get(MOD_ID).get(Registry.MENU_REGISTRY)
				.register(new ResourceLocation(MOD_ID, "inventory_filter"), () -> MenuRegistry.ofExtended(InventoryFilterMenu::new));

		ItemFiltersNet.init();
		EnvExecutor.runInEnv(EnvType.CLIENT, () -> proxy::setup);
	}
}