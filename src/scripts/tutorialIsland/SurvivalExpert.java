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
        RS2Widget instructionsInterface = script.getWidgets().get(TUTCONSTS.instructionsInterface,
                TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent);
        if (instructionsInterface.getMessage().contains("you've just cooked your first") ||
                instructionsInterface.getMessage().contains("Follow the path until you get to the door with the yellow" +
                        " arrow above it")) {
            script.log("Skipping Survival Expert phase");
            return true;
        }
        //First chat
        script.log("Started Survival Expert phase");
        script.log("Attempting to talk to Survival Expert");
        Utils.interactWithNpc(script.getNpcs().closest("Survival Expert"), "Talk-to"
                , script);
        if (!script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"))) {
            new ConditionalSleep(5000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"));
                }
            }.sleep();
        }
        script.sleep(Utils.boundedInteractionTime(1000, 1200));
        Utils.continueToEnd(script);
        if (!script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"))) {
            new ConditionalSleep(5000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return !script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"));
                }
            }.sleep();
        }
        script.sleep(Utils.randomInteractionTime(false));

        //Fishing
        NPC fishingSpots = script.getNpcs().closest("Fishing spot");
        script.log("Found: " + fishingSpots);
        script.log(fishingSpots.getActions());

        //Checking inventory
        do {
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
            script.log("Failed to open inventory, retrying...");
        } while(!script.getTabs().isOpen(Tab.INVENTORY));

        //Inventory tab checked
        script.log("Attempting to fish");
        while(!script.myPlayer().isAnimating()) {
            script.sleep(Utils.boundedInteractionTime(1000, 3000));
            fishingSpots.interact("Net");
            script.sleep(Utils.boundedInteractionTime(1000, 3000));
        }

        //While not inactive
        if (!(script.myPlayer().isAnimating() && script.getInventory().contains("Raw shrimps"))) {
            new ConditionalSleep(10000) {
                @Override
                public boolean condition() throws InterruptedException {
                    script.sleep(10);
                    return (!script.myPlayer().isAnimating() &&
                            script.getInventory().contains("Raw shrimps"));
                }
            }.sleep();
        }
        //At this point fishing should be done
        script.sleep(Utils.boundedInteractionTime(1000, 3000));

        //Check experience tab
        do {
            script.log("Attempting to open skills tab");
            script.getTabs().open(Tab.SKILLS);
            script.sleep(Utils.boundedInteractionTime(400, 800));
            script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.skillsTab).interact();
            script.sleep(Utils.boundedInteractionTime(500, 1000));
        } while (!script.getTabs().isOpen(Tab.SKILLS));

        script.sleep(Utils.boundedInteractionTime(5000, 10000));
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        //Experience tab checked

        //Second interaction
        //Config state: 70
        while(script.getConfigs().get(281) == 60) {
            script.log("Attempting to talk to Survival expert");
            script.sleep(Utils.randomInteractionTime(false));
            Utils.interactWithNpc(script.getNpcs().closest("Survival Expert"), "Talk-to"
                    , script);
            if (!script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"))) {
                new ConditionalSleep(5000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        script.sleep(100);
                        return script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"));
                    }
                }.sleep();
            }
            script.sleep(Utils.boundedInteractionTime(1000, 1500));
            Utils.continueToEnd(script);
            if (script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"))) {
                new ConditionalSleep(5000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        script.sleep(100);
                        return !script.myPlayer().isInteracting(script.getNpcs().closest("Survival Expert"));
                    }
                }.sleep();
            }
        }
        script.sleep(Utils.randomInteractionTime(true));
        //Woodcutting
        while (!script.getInventory().contains("Logs")) {
        RS2Object tree = script.getObjects().closest("Tree");
        script.log("Attempting to cut down tree");
        script.getCamera().toEntity(tree);
        tree.interact("Chop down Tree");
        new ConditionalSleep(5000, 10000) {
            @Override
            public boolean condition() throws InterruptedException {
                script.sleep(100);
                if (script.myPlayer().getAnimation() == -1 && !(script.getInventory().contains("Logs")))
                    tree.interact("Chop down");
                script.sleep(Utils.randomInteractionTime(false));
                return script.myPlayer().getAnimation() == -1 && script.getInventory().contains("Logs");
            }
        }.sleep();
        script.sleep(Utils.randomInteractionTime(true));
        }

        //Firemaking
        Area farAwayArea = new Area(new Position(3091, 3091, 0), new Position(3093, 3095, 0));
        Position randomTile = farAwayArea.getRandomPosition();
        while (!script.getObjects().get(randomTile.getX(), randomTile.getY()).isEmpty()) {
            randomTile = farAwayArea.getRandomPosition();
            script.sleep(Utils.boundedInteractionTime(5000, 10000));
        }
        script.getWalking().walk(farAwayArea);
        script.log("Attempting to reach firemaking area");
        script.sleep(Utils.randomInteractionTime(false));
        Position finalRandomTile = randomTile;
        new ConditionalSleep(5000, 10000) {
            @Override
            public boolean condition() throws InterruptedException {
                script.sleep(100);
                return script.myPlayer().getPosition().distance(finalRandomTile) == 0;
            }
        }.sleep();
        //Opening inventory again
        while(!script.getTabs().isOpen(Tab.INVENTORY)) {
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

        script.getInventory().getItem("Tinderbox").interact("Use");
        script.sleep(Utils.randomInteractionTime(false));
        script.getInventory().getItem("Logs").interact("Use");
        script.sleep(Utils.randomInteractionTime(false));
        new ConditionalSleep(5000, 10000) {
            @Override
            public boolean condition() throws InterruptedException {
                script.sleep(100);
                if (script.getDialogues().isPendingContinuation()) {
                    Utils.continueNextDialogue(script);
                    script.getWalking().walk(script.myPlayer().getPosition().translate(-1, -1));
                }
                return false;
            }
        }.sleep();
        script.getInventory().getItem("Raw shrimps").interact("Use");
        script.sleep(Utils.randomInteractionTime(false));
        RS2Object campfire = script.getObjects().closest("Fire");
        campfire.interact("Use");
        script.log("Attempting to cook shrimps");
        script.sleep(Utils.randomInteractionTime(false));
        script.sleep(Utils.randomInteractionTime(true));
        script.log("Finished survival expert section, walking...");
        return true;
    }
}
