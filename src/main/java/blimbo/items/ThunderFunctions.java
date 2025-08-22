package blimbo.items;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThunderFunctions {

    // Helper builder for consumable items.
    // This method sets up the base properties for a consumable item.
    public static ConsumableComponent.Builder thund() {
        return ConsumableComponent.builder()
                .consumeSeconds(1.6F)
                .useAction(UseAction.DRINK)
                .sound(SoundEvents.ENTITY_ITEM_BREAK)
                .consumeParticles(false);
    }

    // All consumable components are defined here.
    public static class ConsumableComponents {
        public static final ConsumableComponent TTOTEM;

        static {
            // Build the consumable component for the Thunder Totem.
            TTOTEM = thund()
                    .consumeEffect(new ConsumeEffect() {
                        @Override
                        public Type<? extends ConsumeEffect> getType() {
                            // This effect is not serializable, so we return null.
                            // This is expected for transient effects.
                            return null;
                        }

                        @Override
                        public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
                            // The if-check is crucial to ensure this code only runs on the server side.
                            // This prevents duplicate effects and potential desyncs.
                            if (!world.isClient) {
                                // Cast the world to ServerWorld to use server-specific methods.
                                ServerWorld serverWorld = (ServerWorld) world;

                                // Define the number of lightning bolts to spawn in the circle.
                                final int lightningCount = 36;
                                // Define the radius of the circle in blocks.
                                final double radius = 5;

                                // Loop to spawn multiple lightning bolts.
                                for (int i = 0; i < lightningCount; i++) {
                                    // Calculate the angle for each lightning bolt to be spaced evenly in a circle.
                                    double angle = (360.0 / lightningCount) * i;
                                    double radians = Math.toRadians(angle);

                                    // Calculate the x and z coordinates for the new lightning bolt position
                                    // based on the player's position and the radius.
                                    double spawnX = user.getX() + radius * Math.cos(radians);
                                    double spawnZ = user.getZ() + radius * Math.sin(radians);
                                    double spawnY = user.getY();

                                    // Create a new lightning bolt entity.
                                    LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(serverWorld, SpawnReason.EVENT);

                                    // Check if the entity was created successfully to prevent errors.
                                    if (lightning != null) {
                                        // Set the exact position for the lightning bolt.
                                        lightning.refreshPositionAndAngles(
                                                spawnX,
                                                spawnY,
                                                spawnZ,
                                                user.getYaw(),
                                                user.getPitch()
                                        );

                                        // Add the lightning entity to the world.
                                        serverWorld.spawnEntity(lightning);
                                    }
                                }
                            }
                            // Return true to indicate that the item was successfully consumed.
                            return true;
                        }
                    })
                    .build();
        }
    }
}