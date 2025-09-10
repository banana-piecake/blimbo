package blimbo.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class VampItem extends Item {

    public VampItem(Settings settings) {
        super(settings);
    }

    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addDeathParticles();

        if (attacker instanceof PlayerEntity player) {

                player.heal(3f);

                player.getWorld().playSound(
                        null,
                        player.getBlockPos(),
                        net.minecraft.sound.SoundEvents.ENTITY_GHAST_SCREAM,
                        net.minecraft.sound.SoundCategory.PLAYERS,
                        1.0F,   // volume
                        0.3F    // pitch (lower for creepier effect)
                );
            }
        }
    }