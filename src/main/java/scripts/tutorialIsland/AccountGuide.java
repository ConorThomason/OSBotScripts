package scripts.tutorialIsland;

import org.osbot.rs07.script.Script;

public class AccountGuide {
    public boolean accountGuide(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 530:
                Utils.interruptionCheck(script);
                try {
                    if (script.getWidgets().containingText(TUTCONSTS.instructionsInterface, "Moving on").get(0)
                            .getMessage().contains("Moving on")) {
                        return true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    //nop
                }
                script.log("Attempting to talk to Account Guide");
                Utils.interactWithNpc(script.getNpcs().closest("Account guide"), "Talk-to", script);
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
            case 531:
                script.log("Attempting to click Account tab");
                Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.bottomRowTabsAlternative, TUTCONSTS.accountTab)
                        .interact(), 1000, 10000);
                script.sleep(Utils.randomInteractionTime(true));
            case 532:
                script.log("Attempting to talk to Account Guide");
                Utils.interactWithNpc(script.getNpcs().closest("Account guide"), "Talk-to", script);
                Timing.waitCondition(() -> {
                    try {
                        return Utils.pendingContinuation(script);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                }, 100, 6000);
                script.sleep(Utils.randomInteractionTime(true));
                Utils.continueToEnd(script);
                script.sleep(Utils.randomInteractionTime(true));
            case 540:
                //nop
            default:
                break;
        }
        return true;
    }
}