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

    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Vec3d movementVector = target.getRotationVector();
        Vec3d dashVector = movementVector.multiply(3.0);
        target.addVelocity(dashVector);
    }
}