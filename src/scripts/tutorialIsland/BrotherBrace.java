package scripts.tutorialIsland;

import org.osbot.rs07.script.Script;

public class BrotherBrace {
    public boolean brotherBrace(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.log("Attempting to talk to Brother Brace");
        Utils.interactWithNpc(script.getNpcs().closest("Brother Brace"), "Talk-to", script);
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        Utils.continueToEnd(script);
        script.log("Attempting to open prayer tab");
        script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.prayerTab).interact();
        script.sleep(Utils.randomInteractionTime(true));
        script.log("Attempting to talk to Brother Brace");
        Utils.interactWithNpc(script.getNpcs().closest("Brother Brace"), "Talk-to", script);
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        Utils.continueToEnd(script);
        script.sleep(Utils.randomInteractionTime(true));
        script.log("Attempting to open friends list");
        script.getWidgets().containingActions(TUTCONSTS.bottomRowTabsAlternative, "Friends List").get(0)
        .interact();
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        script.log("Attempting to talk to Brother Brace");
        Utils.interactWithNpc(script.getNpcs().closest("Brother Brace"), "Talk-to", script);
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        Utils.continueToEnd(script);
        return true;
    }
}
