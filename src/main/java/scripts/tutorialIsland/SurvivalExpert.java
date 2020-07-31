package scripts.tutorialIsland;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class SurvivalExpert {

    public boolean survivalExpert(Script script) throws InterruptedException {
        NPC fishingSpots = script.getNpcs().closest("Fishing spot");
        switch (script.getConfigs().get(281)) {
            case 30:
                Utils.interruptionCheck(script);
                RS2Widget instructionsInterface = script.getWidgets().get(TUTCONSTS.instructionsInterface,
                        TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent);
                //First chat
                script.log("Started Survival Expert phase");
                script.log("Attempting to talk to Survival Expert");
                Utils.interactWithNpc(script.getNpcs().closest("Survival Expert"), "Talk-to"
                        , script);
                if (!script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"))) {
                    Timing.waitCondition(() -> script.myPlayer().isInteracting(script.getNpcs()
                            .closest("Survival Expert")), 500, 5000);
                }
                script.sleep(Utils.boundedInteractionTime(800, 1200));
                Utils.continueToEnd(script);
                script.sleep(Utils.randomInteractionTime(false));
                if (!script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"))) {
                    Timing.waitCondition(() -> !script.myPlayer().isInteracting(script.getNpcs().closest(
                            "Survival Expert")), 500, 5000);
                    script.sleep(Utils.randomInteractionTime(false));
                }
                //Fishing

                //Checking inventory
                do {
                    script.log("Checking inventory");
                    Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.inventoryTab)
                            .interact(), 400, 2000);
                    script.sleep(Utils.randomInteractionTime(false));
                    if (!script.getTabs().isOpen(Tab.INVENTORY)) {
                        Timing.waitCondition(() -> script.getTabs().isOpen(Tab.INVENTORY), 100, 5000);
                    }
                    script.log("Failed to open inventory, retrying...");
                } while (!script.getTabs().isOpen(Tab.INVENTORY));

            case 40:
                //Inventory tab checked
                script.log("Attempting to fish");
                while (!script.myPlayer().isAnimating()) {
                    Timing.waitCondition(() -> fishingSpots.interact(), 1500, 10000);
                }

                //While not inactive
                if (!(script.myPlayer().isAnimating() && script.getInventory().contains("Raw shrimps"))) {
                    Timing.waitCondition(() -> (!script.myPlayer().isAnimating() &&
                            script.getInventory().contains("Raw shrimps")), 500, 8000);
                }
                //At this point fishing should be done
                script.sleep(Utils.randomInteractionTime(false));
            case 50:
                //Check experience tab
                do {
                    script.log("Attempting to open skills tab");
                    Timing.waitCondition(() -> script.getTabs().open(Tab.SKILLS), 200, 1600);
                    script.sleep(Utils.randomInteractionTime(false));
                    Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.skillsTab).interact()
                            , 200, 1600);
                    script.sleep(Utils.randomInteractionTime(false));
                } while (!script.getTabs().isOpen(Tab.SKILLS));
                script.sleep(Utils.randomInteractionTime(false));
                //Experience tab checked
            case 60:
                //Second interaction
                //Config state: 70
                while (script.getConfigs().get(281) == 60) {
                    script.log("Attempting to talk to Survival expert");
                    script.sleep(Utils.randomInteractionTime(false));
                    Utils.interactWithNpc(script.getNpcs().closest("Survival Expert"), "Talk-to"
                            , script);
                    if (!script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"))) {
                        Timing.waitCondition(() -> script.myPlayer().isInteracting(
                                script.getNpcs().closest("Survival Expert")), 500, 5000);
                    }
                    script.sleep(Utils.randomInteractionTime(false));
                    Utils.continueToEnd(script);
                    script.sleep(Utils.randomInteractionTime(true));
                    if (script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"))) {
                        Timing.waitCondition(() -> script.myPlayer().isInteracting(
                                script.getNpcs().closest("Survival Expert")), 500, 5000);
                    }
                }
                script.sleep(Utils.randomInteractionTime(true));
                //Woodcutting
            case 70:
                while (!script.getInventory().contains("Logs")) {
                    RS2Object tree = script.getObjects().closest("Tree");
                    script.log("Attempting to cut down tree");
                    Timing.waitCondition(() -> script.getCamera().toEntity(tree), 300, 2100);
                    tree.interact("Chop down Tree");
                    Timing.waitCondition(() -> {
                        if (script.myPlayer().getAnimation() == -1 && !(script.getInventory().contains("Logs")))
                            tree.interact("Chop down");
                        try {
                            script.sleep(Utils.randomInteractionTime(false));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return script.myPlayer().getAnimation() == -1 && script.getInventory().contains("Logs");
                    }, 500, 7500);
                    script.sleep(Utils.randomInteractionTime(true));
                }
            case 80:
                //Firemaking
                Area farAwayArea = new Area(new Position(3091, 3091, 0), new Position(3093, 3095, 0));
                Position randomTile = farAwayArea.getRandomPosition();
                while (!script.getObjects().get(randomTile.getX(), randomTile.getY()).isEmpty()) {
                    randomTile = farAwayArea.getRandomPosition();
                    script.sleep(Utils.randomInteractionTime(false));
                }
                script.getWalking().walk(farAwayArea);
                script.log("Attempting to reach firemaking area");
                script.sleep(Utils.randomInteractionTime(false));
                Position finalRandomTile = randomTile;
                Timing.waitCondition(() -> script.myPosition().distance(finalRandomTile) == 0,
                        250, 5000);

                //Opening inventory again
                while (!script.getTabs().isOpen(Tab.INVENTORY)) {
                    script.log("Checking inventory");
                    Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.inventoryTab)
                            .interact(), 200, 2000);
                    script.sleep(Utils.randomInteractionTime(false));
                    if (!script.getTabs().isOpen(Tab.INVENTORY)) {
                        Timing.waitCondition(() -> script.getTabs().isOpen(Tab.INVENTORY), 100, 5000);
                    }
                    script.log("Failed to open inventory, retrying...");
                }

                script.getInventory().getItem("Tinderbox").interact("Use");
                script.sleep(Utils.randomInteractionTime(false));
                script.getInventory().getItem("Logs").interact("Use");
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> {
                    if (script.getDialogues().isPendingContinuation()) {
                        try {
                            Utils.continueNextDialogue(script);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        script.getWalking().walk(script.myPlayer().getPosition().translate(-1, -1));
                    }
                    return false;
                }, 250, 5000);
            case 90:
                //Cooking
                script.getInventory().getItem("Raw shrimps").interact("Use");
                script.sleep(Utils.randomInteractionTime(false));
                RS2Object campfire = script.getObjects().closest("Fire");
                campfire.interact("Use");
                script.log("Attempting to cook shrimps");
                script.sleep(Utils.randomInteractionTime(false));
                script.sleep(Utils.randomInteractionTime(true));
            case 120:
                script.log("Finished survival expert section, walking...");
            default:
                break;
        }
        return true;
    }
}
