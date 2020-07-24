package scripts.tutorialIsland;

import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;

public class BrotherBrace {
    public boolean brotherBrace(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.log("Attempting to talk to Brother Brace");
        Utils.interactWithNpc(script.getNpcs().closest("Brother Brace"), "Talk-to", script);
        Timing.waitCondition(() -> {
            try {
                return Utils.pendingContinuation(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 100, 6000);
        Utils.continueToEnd(script);
        script.log("Attempting to open prayer tab");

        boolean checkedPrayer = false;
        while (!checkedPrayer) {
            script.log("Checking prayer tab");
            checkedPrayer = Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.topRowTabs,
                    TUTCONSTS.prayerTab).interact(), 1000, 5000);
            script.sleep(Utils.randomInteractionTime(false));
            if (!script.getTabs().isOpen(Tab.PRAYER)) {
                Timing.waitCondition(() -> script.getTabs().isOpen(Tab.PRAYER), 100, 5000);
            }
        }
        script.sleep(Utils.randomInteractionTime(true));

        script.log("Attempting to talk to Brother Brace");
        Utils.interactWithNpc(script.getNpcs().closest("Brother Brace"), "Talk-to", script);
        Timing.waitCondition(() -> {
            try {
                return Utils.pendingContinuation(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 100, 6000);
        Utils.continueToEnd(script);
        script.sleep(Utils.randomInteractionTime(true));
        script.log("Attempting to open friends list");

        boolean checkedFriends = false;
        while (!checkedFriends) {
            script.log("Checking friends tab");
            checkedFriends = script.getWidgets().containingActions(TUTCONSTS.bottomRowTabsAlternative,
                    "Friends List").get(0).interact();
            script.sleep(Utils.randomInteractionTime(false));
            if (!script.getTabs().isOpen(Tab.FRIENDS)) {
                Timing.waitCondition(() -> script.getTabs().isOpen(Tab.FRIENDS), 100, 5000);
            }
        }
        script.sleep(Utils.randomInteractionTime(false));
        script.log("Attempting to talk to Brother Brace");
        Utils.interactWithNpc(script.getNpcs().closest("Brother Brace"), "Talk-to", script);
        Timing.waitCondition(() -> {
            try {
                return Utils.pendingContinuation(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 100, 6000);
        Utils.continueToEnd(script);
        return true;
    }
}
