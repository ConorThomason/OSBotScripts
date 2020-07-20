package scripts.tutorialIsland;

import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class MasterNavigator {
    public boolean masterNavigator(Script script) throws InterruptedException {
        if (script.getWidgets().get(TUTCONSTS.instructionsInterface, TUTCONSTS.instructionsChild,
                TUTCONSTS.instructionsComponent).getMessage().contains("You've baked your first loaf of bread.")) {
            script.log("Skipping Master Chef phase");
            return true;
        }
        script.log("Attempting to talk to Master Chef");
        Utils.interactWithNpc(script.getNpcs().closest("Master Chef"), "Talk-to", script);
        script.sleep(Utils.randomInteractionTime(true));
        Utils.continueToEnd(script);

        script.log("Opening inventory (if required)");
        //Making sure inventory is open
        while (!script.getTabs().isOpen(Tab.INVENTORY)) {
            script.log("Checking inventory");
            script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.inventoryTab).interact();
            script.sleep(Utils.boundedInteractionTime(800, 1200));
            if (!script.getTabs().isOpen(Tab.INVENTORY)) {
                new ConditionalSleep(5000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        script.sleep(100);
                        return script.getTabs().isOpen(Tab.INVENTORY);
                    }
                }.sleep();
            }
        }
        script.log("Making dough");
        while (!script.getInventory().contains("Bread dough")) {
            script.getInventory().getItem("Bucket of water").interact("Use");
            script.sleep(Utils.randomInteractionTime(false));
            script.getInventory().getItem("Pot of flour").interact("Use");
            script.sleep(Utils.randomInteractionTime(false));
        }
        //Dough should be made
        //Bake bread
        script.log("Baking bread");
        Utils.interactItemWithObject(script.getInventory().getItem("Bread dough"),
                script.getObjects().closest("Range"), "Bread", script);
        while (script.myPlayer().isAnimating()) {
            script.sleep(1000);
        }
        return true;
    }
}
