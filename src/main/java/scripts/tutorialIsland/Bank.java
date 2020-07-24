package scripts.tutorialIsland;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import scripts.tutorialIsland.Timing;
import scripts.tutorialIsland.Utils;

public class Bank {
    public boolean bank(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.log("Attempting to open bank");
        Timing.waitCondition(() -> script.getObjects().closest("Bank booth").interact("Use"),
                1000, 10000);
        script.sleep(Utils.randomInteractionTime(false));
        Timing.waitCondition(() -> {
            try {
                return script.getBank().open();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 1000, 10000);
        script.sleep(scripts.tutorialIsland.Utils.randomInteractionTime(false));
        script.log("Attempting to close bank");
        Timing.waitCondition(() -> script.getBank().close(), 1000, 10000);
        script.sleep(Utils.randomInteractionTime(true));
        RS2Object pollBooth = script.getObjects().closest("Poll booth");

        script.log("Attempting to interact with poll booth");
        Timing.waitCondition(() -> pollBooth.interact(), 1000, 10000);
        Timing.waitCondition(() -> {
            try {
                return Utils.pendingContinuation(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 100, 6000);
        script.sleep(Utils.randomInteractionTime(false));
        Utils.continueToEnd(script);
        script.log("Attempting to close poll booth");
        Timing.waitCondition(() -> script.getWidgets().containingActions(TUTCONSTS.pollBooth, "Close")
                .get(0).interact("Close"), 1000, 10000);
        return true;
    }
}
