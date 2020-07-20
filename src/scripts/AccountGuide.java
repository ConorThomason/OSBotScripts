package scripts;

import org.osbot.rs07.script.Script;

public class AccountGuide {
    public boolean accountGuide(Script script) throws InterruptedException {
        try {
            if (script.getWidgets().containingText(TUTCONSTS.instructionsInterface, "Moving on").get(0)
                    .getMessage().contains("Moving on")) {
                return true;
            }
        } catch (IndexOutOfBoundsException e){
            //nop
        }
        script.log("Attempting to talk to Account Guide");
        Utils.interactWithNpc(script.getNpcs().closest("Account guide"), "Talk-to", script);
        script.sleep(Utils.randomInteractionTime(true));
        Utils.continueToEnd(script);
        script.sleep(Utils.randomInteractionTime(true));
        script.log("Attempting to click Account tab");
        script.getWidgets().get(TUTCONSTS.bottomRowTabsAlternative, TUTCONSTS.accountTab).interact();
        script.sleep(Utils.randomInteractionTime(true));
        script.log("Attempting to talk to Account Guide");
        Utils.interactWithNpc(script.getNpcs().closest("Account guide"), "Talk-to", script);
        script.sleep(Utils.randomInteractionTime(true));
        Utils.continueToEnd(script);
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        return true;
    }
}
