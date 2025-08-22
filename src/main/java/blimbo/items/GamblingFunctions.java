package blimbo.items;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class GamblingFunctions {

    // Helper builder for consumable items.
    // This method sets up the base properties for a consumable item.
    public static ConsumableComponent.Builder gamble() {
        return ConsumableComponent.builder()
                .consumeSeconds(0.4F)
                .useAction(UseAction.DRINK)
                .sound(SoundEvents.ENTITY_ITEM_BREAK)
                .consumeParticles(false);
    }

    // All consumable components are defined here.
    public static class ConsumableComponents {
        public static final ConsumableComponent GAMBLE;

        static {

            GAMBLE = gamble()
                    .consumeEffect(new ConsumeEffect() {
                                       @Override
                                       public Type<? extends ConsumeEffect> getType() {
                                           // This effect is not serializable, so we return null.
                                           return null;
                                       }

                                       @Override
                                       public boolean onConsume(World world, ItemStack stack, LivingEntity user) {

                                           if (!world.isClient) {
                                               if (user.getRandom().nextFloat() < 0.3f) {
                                                   user.giveOrDropStack(new ItemStack(user.getActiveItem().getItem(), 5));
                                               }

                                           }


                            return true;
                        }
                    }).build();
        }
    }
}