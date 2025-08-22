package blimbo.items;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class BeeFunctions {

    // Helper builder for consumable items.
    // This method sets up the base properties for a consumable item.
    public static ConsumableComponent.Builder bumble() {
        return ConsumableComponent.builder()
                .consumeSeconds(1.6F)
                .useAction(UseAction.DRINK)
                .sound(SoundEvents.ENTITY_ITEM_BREAK)
                .consumeParticles(false);
    }

    // All consumable components are defined here.
    public static class ConsumableComponents {
        public static final ConsumableComponent BUMBLE;

        static {
            // Build the consumable component for the Bee Totem.
            BUMBLE = bumble()
                    .consumeEffect(new ConsumeEffect() {
                        @Override
                        public Type<? extends ConsumeEffect> getType() {
                            // This effect is not serializable, so we return null.
                            return null;
                        }

                        @Override
                        public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
                            // The if-check is crucial to ensure this code only runs on the server side.
                            if (!world.isClient) {
                                // --- Apply status effects to the user ---
                                // Grant a regeneration effect.
                                user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 3));
                                // Remove the poison effect. Note: `StatusEffects.POISON` is the correct way to reference it.
                                user.removeStatusEffect(StatusEffects.POISON);

                            // The if-check is crucial to ensure this code only runs on the server side.
                            // This prevents duplicate effects and potential desyncs.

                                // Cast the world to ServerWorld to use server-specific methods.
                                ServerWorld serverWorld = (ServerWorld) world;

                                // Define the number of bees to spawn in the circle.
                                final int lightningCount = 100;
                                // Define the radius of the circle in blocks.
                                final double radius = 2;

                                // Loop to spawn multiple bbes bolts.
                                for (int i = 0; i < lightningCount; i++) {
                                    // Calculate the angle for each bees  to be spaced evenly in a circle.
                                    double angle = (250.0 / lightningCount) * i;
                                    double radians = Math.toRadians(angle);

                                    // Calculate the x and z coordinates for the new lightning bolt position
                                    // based on the player's position and the radius.
                                    double spawnX = user.getX() + radius * Math.sin(radians);
                                    double spawnZ = user.getZ() + radius * Math.cos(radians);
                                    double spawnY = user.getY();

                                    // Create a new lightning bolt entity.
                                    BeeEntity bee = EntityType.BEE.create(serverWorld, SpawnReason.EVENT);

                                    // Check if the entity was created successfully to prevent errors.
                                    if (bee != null) {
                                        // Set the exact position for the bees.
                                        bee.refreshPositionAndAngles(
                                                spawnX,
                                                spawnY,
                                                spawnZ,
                                                user.getYaw(),
                                                user.getPitch()
                                        );

                                        // Add the lightning entity to the world.
                                        serverWorld.spawnEntity(bee);
                                    }
                                }
                            }
                            // Return true to indicate that the item was successfully consumed.
                            return true;
                        }
                    }).build();
        }
    }
}