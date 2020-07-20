package scripts;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class MiningInstructor {
    public boolean miningInstructor(Script script) throws InterruptedException {
        String bronzeDaggerComponent = script.getWidgets().get(
                TUTCONSTS.instructionsInterface, TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent)
                .getMessage();
        if (bronzeDaggerComponent != null && (bronzeDaggerComponent.contains("You've made a bronze bar!"))) {
            script.log("Skipping to bronze dagger section");
            daggerSection(script);
            return true;
        } else if (bronzeDaggerComponent != null && (bronzeDaggerComponent.contains
                ("Speak to the mining instructor for a recap at any time"))) {
            script.log("Skipping to combat section");
            return true;
        }
        while(!script.getInventory().contains("Bronze pickaxe")) {
            Utils.interactWithNpc(script.getNpcs().closest("Mining Instructor"), "Talk-to", script);
            if (!script.myPlayer().isInteracting(script.getNpcs().closest("Mining Instructor"))) {
                script.sleep(300);
                Utils.interactWithNpc(script.getNpcs().closest("Mining Instructor"), "Talk-to", script);
                Utils.continueToEnd(script);
            }
        }
        script.sleep(Utils.boundedInteractionTime(1000, 2000));
        RS2Object tinOre = script.getObjects().closest(10080);
        script.sleep(Utils.randomInteractionTime(true));
        while (!(script.getInventory().contains("Tin ore") && script.getInventory().contains("Copper ore"))) {
            script.log("Attempting to mine Tin");
            tinOre.interact("Mine");
            if (script.myPlayer().isAnimating()) {
                while (script.myPlayer().isAnimating()) {
                    script.sleep(1000);
                }
            }
            script.sleep(Utils.boundedInteractionTime(1400, 2800));
            RS2Object copperOre = script.getObjects().closest(10079);
            script.sleep(Utils.randomInteractionTime(true));
            script.log("Attempting to mine Copper");
            copperOre.interact("Mine");
            script.sleep(5000);
            if (script.myPlayer().isAnimating()) {
                while (script.myPlayer().isAnimating()) {
                    script.sleep(1000);
                }
            }
            script.sleep(Utils.boundedInteractionTime(800, 1600));
        }
        script.sleep(Utils.boundedInteractionTime(1400, 2800));
        RS2Object furnace = script.getObjects().closest("Furnace");
        furnace.interact();
        script.log("Attempting to smith Bronze Bar");
        script.sleep(2500);
        if (script.myPlayer().isAnimating()) {
            while (script.myPlayer().isAnimating()) {
                script.sleep(1000);
            }
        }
        script.sleep(Utils.boundedInteractionTime(1000, 2000));
        daggerSection(script);
        return true;
    }

    public void daggerSection(Script script) throws InterruptedException {
        while(!script.getInventory().contains("Hammer")) {
            Utils.interactWithNpc(script.getNpcs().closest("Mining Instructor"), "Talk-to", script);
            script.sleep(Utils.boundedInteractionTime(1000, 2000));
            if (!script.myPlayer().isInteracting(script.getNpcs().closest("Mining Instructor"))) {
                Utils.continueToEnd(script);
                script.sleep(1000);
            }
        }
        script.sleep(1000);
        RS2Object anvil = script.getObjects().closest("Anvil");
        script.sleep(Utils.boundedInteractionTime(1000, 2000));
        script.log("Attempting to make Bronze Dagger");
        anvil.interact("Smith");
        if (script.getWidgets().getWidgets(TUTCONSTS.smithingInterface) == null) {
            new ConditionalSleep(10000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return script.getWidgets().getWidgets(TUTCONSTS.smithingInterface) != null;
                }
            }.sleep();
        }
        script.sleep(Utils.boundedInteractionTime(2000, 4000));
        RS2Widget daggerSelection = script.getWidgets().get(TUTCONSTS.smithingInterface,
                TUTCONSTS.smithingDaggerSelection, TUTCONSTS.smithingDaggerSelectionComponent);
        script.sleep(Utils.boundedInteractionTime(1000, 2000));
        daggerSelection.interact();
        script.sleep(Utils.boundedInteractionTime(3000, 6000));
    }
}
