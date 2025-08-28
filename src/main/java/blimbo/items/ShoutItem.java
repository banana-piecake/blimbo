package blimbo.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ShoutItem extends Item {

    public ShoutItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        Vec3d movementVector = user.getRotationVector();
        Vec3d dashVector = movementVector.multiply(4.0);
        Vec3d upVector = new Vec3d(0,4,0);
        user.addVelocity(dashVector);
        user.addVelocity(upVector);
        return ActionResult.SUCCESS;
    }
}