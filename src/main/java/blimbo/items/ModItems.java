package blimbo.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import blimbo.blimbo;

import java.util.function.Function;

public class ModItems {

    public static final Item CUSTOM_ITEM = registerItem("custom_item", Item::new, new Item.Settings().equippable(EquipmentSlot.HEAD).food(new FoodComponent(1,1,true)));

    public static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registerkey =  RegistryKey.of(RegistryKeys.ITEM, Identifier.of(blimbo.MOD_ID, name));
        return Items.register(registerkey, factory, settings);
    }

    private static void customIngredients(FabricItemGroupEntries entries) {
        entries.add(CUSTOM_ITEM);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::customIngredients);
    }
}
