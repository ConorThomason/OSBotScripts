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
        Utils.interactWithNpc(script.getNpcs().closest
                ("Master Chef"), "Talk-to", script);
        Timing.waitCondition(() -> {
            try {
                return Utils.pendingContinuation(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 100, 3000);

        script.sleep(Utils.randomInteractionTime(true));
        Timing.waitCondition(() -> {
            try {
                return Utils.continueToEnd(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 1000, 8000);


        script.log("Opening inventory (if required)");
        //Making sure inventory is open
        while (!script.getTabs().isOpen(Tab.INVENTORY)) {
            script.log("Checking inventory");
            Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.inventoryTab)
                    .interact(), 400, 2000);
            script.sleep(Utils.randomInteractionTime(false));
            if (!script.getTabs().isOpen(Tab.INVENTORY)) {
                Timing.waitCondition(() -> script.getTabs().isOpen(Tab.INVENTORY), 100, 5000);
            }
            script.log("Failed to open inventory, retrying...");
        } ;

        script.log("Making dough");
        while (!script.getInventory().contains("Bread dough")) {
            Timing.waitCondition(() -> script.getInventory().getItem("Bucket of water")
                    .interact("Use"), 50, 3000);
            script.sleep(Utils.randomInteractionTime(false));
            Timing.waitCondition(() -> script.getInventory().getItem("Pot of flour")
                    .interact("Use"), 50, 3000);
            script.sleep(Utils.randomInteractionTime(false));
        }
        //Dough should be made
        //Bake bread
        script.log("Baking bread");
        Timing.waitCondition(() -> {
            try {
                return Utils.interactItemWithObject(script.getInventory().getItem("Bread dough"),
                        script.getObjects().closest("Range"), "Bread", script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 200, 5000);
        return true;
    }
}
