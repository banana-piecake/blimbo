package blimbo.items;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;

public class TotemFunctions {

    // Helper builder for consumable items
    public static ConsumableComponent.Builder summon() {
        return ConsumableComponent.builder()
                .consumeSeconds(1.6F)
                .useAction(UseAction.DRINK)
                .sound(SoundEvents.ENTITY_ITEM_BREAK)
                .consumeParticles(false);
    }

    // All consumable components
    public static class ConsumableComponents {
        public static final ConsumableComponent MAHORAGA;

        static {
            MAHORAGA = summon()
                    .consumeEffect(new ConsumeEffect() {
                        @Override
                        public Type<? extends ConsumeEffect> getType() {
                            return null; // Not serializing this effect
                        }

                        @Override
                        public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
                            if (!world.isClient) {
                                // --- Apply instant damage to the user ---
                                user.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 10));


                                // --- Spawn the Wither ---
                                ServerWorld serverWorld = (ServerWorld) world;

// Create the Wither entity
                                WitherEntity wither = EntityType.WITHER.create(serverWorld, SpawnReason.EVENT);

                                if (wither != null) {
                                    // Use BlockPos to ensure it spawns above the player and in free space
                                    BlockPos spawnPos = user.getBlockPos().up(); // 1 block above player
                                    if (!world.getBlockState(spawnPos).isAir()) {
                                        spawnPos = spawnPos.up(1); // move- higher if needed
                                    }

                                    // Set the exact spawn position before spawning
                                    wither.refreshPositionAndAngles(
                                            spawnPos.getX() + 0.5,
                                            spawnPos.getY(),
                                            spawnPos.getZ() + 0.5,
                                            user.getYaw(),
                                            user.getPitch()
                                    );

                                    // Spawn the Wither
                                    serverWorld.spawnEntity(wither);
                                }
                            }
                            return true;
                        }
                    })
                    .build();
        }
    }
}
