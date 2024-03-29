package scripts.tutorialIsland;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class MiningInstructor {
    public boolean miningInstructor(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 260:
                Utils.interruptionCheck(script);
                String bronzeDaggerComponent = script.getWidgets().get(
                        TUTCONSTS.instructionsInterface, TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent)
                        .getMessage();
                while (!script.getInventory().contains("Bronze pickaxe")) {
                    Utils.interactWithNpc(script.getNpcs().closest("Mining Instructor"), "Talk-to", script);
                    Timing.waitCondition(() -> {
                        try {
                            return Utils.pendingContinuation(script);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }, 100, 3500);
                    Timing.waitCondition(() -> {
                        try {
                            return Utils.continueToEnd(script);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }, 1000, 8000);
                }
                script.sleep(Utils.randomInteractionTime(false));
            case 300:
            case 310:
                RS2Object tinOre = script.getObjects().closest(10080);
                while (!(script.getInventory().contains("Tin ore") && script.getInventory().contains("Copper ore"))) {
                    script.log("Attempting to mine Tin");
                    tinOre.interact("Mine");
                    Timing.waitCondition(() -> script.getInventory().contains("Tin ore"), 100, 8000);
                    script.sleep(Utils.randomInteractionTime(false));
                    RS2Object copperOre = script.getObjects().closest(10079);
                    script.log("Attempting to mine Copper");
                    copperOre.interact("Mine");
                    Timing.waitCondition(() -> script.getInventory().contains("Copper ore"), 100, 8000);
                    script.sleep(Utils.randomInteractionTime(false));
                }
            case 320:
                RS2Object furnace = script.getObjects().closest("Furnace");
                script.log("Attempting to smith Bronze Bar");
                while (!script.getInventory().contains("Bronze bar")) {
                    Utils.interactItemWithObject(script.getInventory().getItem("Tin ore"),
                            script.getObjects().closest("Furnace"), "Bronze bar", script);
                    Timing.waitCondition(() -> script.getInventory().contains("Bronze bar"), 200, 8000);
                    script.sleep(Utils.randomInteractionTime(false));
                }
            case 330:
            case 340:
            case 350:
                daggerSection(script);
            case 360:
                //nop
            default:
                break;
        }
        return true;
    }

    public void daggerSection(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 330:
            case 340:
                while (!script.getInventory().contains("Hammer")) {
                    Utils.interactWithNpc(script.getNpcs().closest("Mining Instructor"), "Talk-to", script);
                    Timing.waitCondition(() -> {
                        try {
                            return Utils.pendingContinuation(script);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }, 100, 3500);
                    Timing.waitCondition(() -> {
                        try {
                            return Utils.continueToEnd(script);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }, 1000, 8000);
                }
                script.sleep(Utils.randomInteractionTime(false));
                RS2Object anvil = script.getObjects().closest("Anvil");
                script.log("Attempting to make Bronze Dagger");
                anvil.interact("Smith");
                if (script.getWidgets().getWidgets(TUTCONSTS.smithingInterface) == null)
                    Timing.waitCondition(() -> script.getWidgets().getWidgets(TUTCONSTS.smithingInterface) != null,
                            100, 10000);
            case 350:
                script.sleep(Utils.randomInteractionTime(false));
                RS2Widget daggerSelection = script.getWidgets().get(TUTCONSTS.smithingInterface,
                        TUTCONSTS.smithingDaggerSelection, TUTCONSTS.smithingDaggerSelectionComponent);
                daggerSelection.interact();
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> script.myPlayer().isAnimating(), 100, 3000);
                Timing.waitCondition(() -> !script.myPlayer().isAnimating(), 100, 8000);
            case 360:
                script.sleep(Utils.randomInteractionTime(true));
        }
    }
}
