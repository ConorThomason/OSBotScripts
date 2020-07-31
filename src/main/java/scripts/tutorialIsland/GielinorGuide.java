package scripts.tutorialIsland;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

//We want a state to maintain current progression
//Got stuck at trying to talk to the guide and select an option - Look into RSInterface

public class GielinorGuide {
    final String StandardDialoguePreChoiceSpeech = "Before we get going, if you could be so kind to let me know how much experience you have with Old School Runescape, that would be wonderful!";
    final String StandardDialogueLastSpeech = "Now then, let's start by looking at your options menu.";
    final String preOptionsSpeech = "You will notice a flashing icon of a spanner. Please click on this to continue the tutorial.";
    final String postOptionsSpeech = "Looks like you're making good progress! The menu you've just opened is one of many. You'll learn about the rest as you progress through the tutorial.";
    final String finishedInteractingSpeech = "To continue the tutorial go through that door over there and speak to your first instructor!";
    final String brandNewPlayer = "I am brand new! This is my first time here.";

    public boolean gielinorGuide(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 2:
                Utils.interruptionCheck(script);
                //Initial interaction
                script.log("Attempting to talk to guide");
                Utils.interactWithNpc(script.getNpcs().closest(TUTCONSTS.gielinorGuideID)
                        , "Talk-to Gielinor Guide", script);
                Timing.waitCondition(() -> {
                    try {
                        return Utils.pendingContinuation(script);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                }, 500, 6000);
                script.sleep(Utils.randomInteractionTime(false));
                script.log("Successfully spoken to guide");
                //Iterating through first conversation
                Utils.continueToEnd(script);
                script.sleep(Utils.randomInteractionTime(true));
                //Selecting experience
                if (!script.getDialogues().isPendingContinuation()) {
                    new ConditionalSleep(5000) {
                        @Override
                        public boolean condition() throws InterruptedException {
                            script.getWidgets().containingText(TUTCONSTS.experienceChoice,
                                    "I am brand new! This is my first time here.").get(0).interact();
                            script.sleep(Utils.randomInteractionTime(true));
                            return script.getWidgets().get(TUTCONSTS.playerHeadResponse,
                                    TUTCONSTS.playerHeadResponseChild).isVisible();
                        }
                    }.sleep();
                }
            case 3:
                script.sleep(Utils.randomInteractionTime(false));
                script.log("Clicked experience choice");
                //Moving through post experience-selection dialogue
                Utils.continueToEnd(script);
                script.sleep(Utils.randomInteractionTime(false));
                //*Should* be complete at this point, moving on to options section
            case 7:
                optionActions(script);
                break;
            default:
                break;
        }
        return true;
    }

    public void optionActions(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 7:
                script.log("Attempting to click options button");
                Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.bottomRowTabs, TUTCONSTS.optionsTab).interact(),
                        500, 2000);
                script.sleep(Utils.randomInteractionTime(false));
                script.log("Attempting to switch UI modes");
                RS2Widget configureInterface = script.getWidgets().get(
                        TUTCONSTS.optionsMenu, TUTCONSTS.optionsMenuAdvancedOptions,
                        TUTCONSTS.optionsMenuAdvancedOptionsComponent);
//        boolean result = configureInterface.interact();
//        while (!result){
//            script.sleep(Utils.boundedInteractionTime(1000, 1200));
//            configureInterface.interact();
//        }

                Timing.waitCondition(() -> configureInterface.interact(), 500, 4000);
                Timing.waitCondition(() -> !script.getWidgets().containingActions(TUTCONSTS.advancedOptions,
                        "Side-stones arrangement").isEmpty(), 400, 8000);
                script.sleep(Utils.randomInteractionTime(false));
                final RS2Widget advancedInterface = script.getWidgets()
                        .containingActions(TUTCONSTS.advancedOptions, "Side-stones arrangement").get(0);
                Timing.waitCondition(() -> advancedInterface.interact(), 500, 4000);
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> script.getWidgets().containingActions(
                        TUTCONSTS.advancedOptions, "Close").get(0).interact(), 500, 4000);
                script.sleep(Utils.randomInteractionTime(false));
//            advancedInterface.interact();
//            script.sleep(Utils.boundedInteractionTime(800, 1200));
//            advancedInterface = script.getWidgets().containingActions(TUTCONSTS.advancedOptions, "Close").get(0);
//            advancedInterface.interact();
//            script.sleep(Utils.randomInteractionTime(false));

                script.log("Attempting to talk to guide");
                Utils.interactWithNpc(script.getNpcs().closest(TUTCONSTS.gielinorGuideID), "Talk-to Gielinor Guide",
                        script);
                Timing.waitCondition(() -> {
                    boolean result = false;
                    try {
                        result = Utils.pendingContinuation(script);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return result;
                }, 400, 6000);
                script.sleep(Utils.randomInteractionTime(false));
                while (!script.getWidgets().containingText("Click here to continue").isEmpty()) {
                    script.getWidgets().getWidgetContainingText("Click here to continue").interact();
                    script.sleep(Utils.randomInteractionTime(true));
                }
            case 10:
                //nop
            default:
                //nop
                break;
        }
    }
}
