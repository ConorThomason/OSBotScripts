package scripts.tutorialIsland;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

public class Bank {
    public boolean bank(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.log("Attempting to open bank");
        script.getObjects().closest("Bank booth").interact("Use");
        script.sleep(Utils.boundedInteractionTime(500, 1000));
        if (!script.getBank().isOpen()){
            script.getBank().open();
            script.sleep(Utils.boundedInteractionTime(1000, 2000));
        }
        script.sleep(Utils.boundedInteractionTime(1000, 1500));
        script.log("Attempting to close bank");
        script.getBank().close();
        script.sleep(Utils.randomInteractionTime(true));
        RS2Object pollBooth = script.getObjects().closest("Poll booth");
        script.log("Attempting to interact with poll booth");
        pollBooth.interact();
        script.sleep(4000);
        Utils.continueToEnd(script);
        script.log("Attempting to close poll booth");
        script.getWidgets().containingActions(TUTCONSTS.pollBooth, "Close").get(0).interact("Close");
        return true;
    }
}
