package scripts.tutorialIsland;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class QuestGuide {
    public boolean questGuide(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 220:
            case 230:
                Utils.interruptionCheck(script);
                script.sleep(Utils.randomInteractionTime(false));
                String questGuideDoneComponent = script.getWidgets().get(
                        TUTCONSTS.instructionsInterface, TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent)
                        .getMessage();
                Utils.interactWithNpc(script.getNpcs().closest("Quest Guide"),
                        "Talk-to", script);
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
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.questTab)
                        .interact(), 50, 3000);
                script.sleep(Utils.randomInteractionTime(false));
                while (!script.getTabs().isOpen(Tab.QUEST)) {
                    script.log("Checking Quest tab");
                    Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.questTab)
                            .interact(), 200, 2000);
                    script.sleep(Utils.randomInteractionTime(false));
                    if (!script.getTabs().isOpen(Tab.QUEST)) {
                        Timing.waitCondition(() -> script.getTabs().isOpen(Tab.QUEST), 100, 5000);
                    }
                    script.log("Failed to open quest tab, retrying...");
                }
                script.sleep(Utils.randomInteractionTime(false));
            case 240:
                Utils.interactWithNpc(script.getNpcs().closest("Quest Guide"), "Talk-to", script);
                script.sleep(Utils.randomInteractionTime(true));
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
                script.sleep(Utils.randomInteractionTime(true));
            case 250:
                RS2Object ladder = script.getObjects().closest("Ladder");
                Timing.waitCondition(() -> ladder.interact("Climb-down"), 1200, 6000);
                Timing.waitCondition(() -> script.myPosition().equals(new Position(3088, 9520, 0)), 500, 6000);
            default:
                break;
        }
        return true;
    }
}
