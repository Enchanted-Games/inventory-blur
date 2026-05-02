//? if fabric {
package games.enchanted.eg_inventory_blur.fabric;

import games.enchanted.eg_inventory_blur.common.ModEntry;
import net.fabricmc.api.ModInitializer;

public class FabricEntry implements ModInitializer {
    @Override
    public void onInitialize() {
        ModEntry.init();
    }
}
//?}