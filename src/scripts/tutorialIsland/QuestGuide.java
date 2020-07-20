package scripts.tutorialIsland;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class QuestGuide {
    public boolean questGuide(Script script) throws InterruptedException {
        String questGuideDoneComponent = script.getWidgets().get(
                TUTCONSTS.instructionsInterface, TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent)
                .getMessage();
        if (questGuideDoneComponent != null && (questGuideDoneComponent.contains("Next let's get you a weapon, or more"))) {
            script.log("Skipping to mining instructor section");
        }
        else {
            boolean result = false;
            while (!result) {
                result = Utils.interactWithNpc(script.getNpcs().closest("Quest Guide"),
                        "Talk-to", script);
                script.sleep(Utils.boundedInteractionTime(1000, 2000));
            }
            Utils.continueToEnd(script);
            script.sleep(Utils.boundedInteractionTime(1000, 2000));
            script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.questTab).interact();
            script.sleep(Utils.randomInteractionTime(false));
            while (!script.getTabs().isOpen(Tab.QUEST)) {
                script.log("Checking quest");
                script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.questTab).interact();
                script.sleep(Utils.boundedInteractionTime(800, 1200));
                if (!script.getTabs().isOpen(Tab.QUEST)) {
                    new ConditionalSleep(5000) {
                        @Override
                        public boolean condition() throws InterruptedException {
                            script.sleep(100);
                            return script.getTabs().isOpen(Tab.QUEST);
                        }
                    }.sleep();
                }
            }
            script.sleep(Utils.boundedInteractionTime(1000, 2000));
            Utils.interactWithNpc(script.getNpcs().closest("Quest Guide"), "Talk-to", script);
            script.sleep(Utils.randomInteractionTime(true));
            Utils.continueToEnd(script);
            script.sleep(Utils.randomInteractionTime(true));
        }
        RS2Object ladder = script.getObjects().closest("Ladder");
        ladder.interact("Climb-down");
        new ConditionalSleep(8000) {
            @Override
            public boolean condition() throws InterruptedException {
                return script.myPosition().equals(new Position(3088, 9520, 0));
            }
        }.sleep();
        return true;
    }
}
