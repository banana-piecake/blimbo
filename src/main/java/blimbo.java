package blimbo;

import blimbo.entity.client.ModEntities;
import blimbo.items.ModItems;
import net.fabricmc.api.ModInitializer;

public class blimbo implements ModInitializer {

    public static final String MOD_ID = "blimbo";
    public static final Process LOGGER = ;

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModEntities.registerModEntities();
    }
}
