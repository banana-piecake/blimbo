package blimbo.items;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ShoutItem extends Item {

    public ShoutItem(Settings settings) {
        super(settings);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        Vec3d movementVector = user.getRotationVector();
        Vec3d dashVector = movementVector.multiply(5.0);
        Vec3d upVector = new Vec3d(0,0.3f,0);

        Vec3d position = user.getPos();
        double radius = 6;
        Box entitydetect =new Box(
                position.x - radius, position.y - radius, position.z - radius,
                position.x + radius, position.y + radius, position.z + radius
        );
        List<Entity> nearbyEntities = world.getOtherEntities(
                user, // Exclude the user themselves
                entitydetect,
                (entity) -> entity instanceof Entity
        );

        for (Entity entity : nearbyEntities) {
            entity.addVelocity(dashVector);
            entity.addVelocity(upVector);
        }
        return ActionResult.SUCCESS;
    }
}