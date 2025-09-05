package blimbo.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.advancement.criterion.SummonedEntityCriterion;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.SummonCommand;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import blimbo.blimbo;

import java.util.List;
import java.util.function.Function;

public class ModItems {


    public static ConsumableComponent.Builder drink() {
        return ConsumableComponent.builder().consumeSeconds(0.4F).useAction(UseAction.DRINK).sound(SoundEvents.ENTITY_ITEM_BREAK).consumeParticles(false);
    }
    public class ConsumableComponents {
        public static final ConsumableComponent CUSTOM_ITEM2;


        static {
            CUSTOM_ITEM2= drink().consumeEffect(new ApplyEffectsConsumeEffect(List.of(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 3), new StatusEffectInstance(StatusEffects.SPEED, 600, 3), new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 0)))).build();
        }
    }

    public class FoodComponent {
        public static final net.minecraft.component.type.FoodComponent CUSTOM_ITEM2;


        static {
            CUSTOM_ITEM2= new net.minecraft.component.type.FoodComponent(1,1,true);
        }
    }

    public static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registerkey =  RegistryKey.of(RegistryKeys.ITEM, Identifier.of(blimbo.MOD_ID, name));
        return Items.register(registerkey, factory, settings);
    }

    public static final Item CUSTOM_ITEM = registerItem("custom_item", Item::new, new Item.Settings().food(FoodComponent.CUSTOM_ITEM2,ConsumableComponents.CUSTOM_ITEM2).useCooldown(35));
    public static final Item WARDEN = registerItem(
            "final_gambit",
            Item::new,
            new Item.Settings()
                    .food(FoodComponent.CUSTOM_ITEM2, TotemFunctions.ConsumableComponents.MAHORAGA)
                    .useCooldown(40)
    );


    public static final Item LIGHTNING = registerItem(
            "thunderous_totem",
            Item::new,
            new Item.Settings()
                    .food(FoodComponent.CUSTOM_ITEM2, ThunderFunctions.ConsumableComponents.TTOTEM)
                    .useCooldown(30)
    );

    public static final Item HONEY = registerItem("beekeepers_totem", Item::new, new Item.Settings().food(FoodComponent.CUSTOM_ITEM2,BeeFunctions.ConsumableComponents.BUMBLE).useCooldown(40));
    public static final Item BLIMBO = registerItem("blimbo", Item::new, new Item.Settings().equippable(EquipmentSlot.HEAD));
    public static final Item NEGATIVE = registerItem("negative_blade_cosmetic", Item::new, new Item.Settings());
    public static final Item SPEAR = registerItem("amethyst_spear_cosmetic", Item::new, new Item.Settings());
    public static final Item GAMBLING = registerItem("gambling_buddy", Item::new, new Item.Settings().food(FoodComponent.CUSTOM_ITEM2,GamblingFunctions.ConsumableComponents.GAMBLE).useCooldown(3));
    public static final Item WATER = registerItem("totem_of_the_seas", SeaTridentItem::new, new Item.Settings().sword(ToolMaterial.NETHERITE,4.5f,-2.6f).useCooldown(7.0F));
    public static final Item SHOUT = registerItem("shout", ShoutItem::new, new Item.Settings().useCooldown(5));
    public static final Item PRISMATIC = registerItem("prismatic", PrismTridentItem::new, new Item.Settings().useCooldown(5));
    public static final Item SWARD = registerItem("watermelon_sword", Item::new, new Item.Settings().sword(ToolMaterial.WOOD,5.7f, -2.2f).food(new net.minecraft.component.type.FoodComponent(7,7,true)));

    private static void customIngredients(FabricItemGroupEntries entries) {
        entries.add(CUSTOM_ITEM);
        entries.add(WARDEN);
        entries.add(HONEY);
        entries.add(BLIMBO);
        entries.add(GAMBLING);
        entries.add(WATER);
        entries.add(LIGHTNING);
        entries.add(NEGATIVE);
        entries.add(SPEAR);
        entries.add(SHOUT);
        entries.add(PRISMATIC);
        entries.add(SWARD);
    }


    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::customIngredients);
    }
}
