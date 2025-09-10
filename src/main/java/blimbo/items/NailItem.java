package blimbo.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NailItem extends Item {

    public NailItem(Settings settings) {
        super(settings);
    }

    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            Vec3d dashVector = attacker.getRotationVector().multiply(-0.7);
            player.addVelocity(dashVector);
            player.velocityModified = true; // <--- tells the server to sync velocity with client
        }
    }
}