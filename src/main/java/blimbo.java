package blimbo;

import blimbo.items.ModItems;
import net.fabricmc.api.ModInitializer;

public class blimbo implements ModInitializer {

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
    }
}
