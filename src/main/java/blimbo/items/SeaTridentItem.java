package blimbo.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SeaTridentItem extends Item {

    public SeaTridentItem(Item.Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        Vec3d movementVector = new Vec3d(0, 30, 0);
        user.setVelocity(movementVector);
        return ActionResult.SUCCESS;
    }
}