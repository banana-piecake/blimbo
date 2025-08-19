package blimbo.entity.client;

import blimbo.blimbo;
import blimbo.entity.custom.BlimboEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<BlimboEntity> BLIMBO = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(blimbo.MOD_ID, "blimbo"),
            EntityType.Builder.create(BlimboEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1f,2f).build());

    public static void registerModEntities() {
        blimbo.LOGGER.info("Registering Mod Entities." + blimbo.MOD_ID)
    }
}
