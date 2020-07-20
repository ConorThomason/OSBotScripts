package scripts;

import org.osbot.rs07.api.Chatbox;
import org.osbot.rs07.api.NPCS;
import org.osbot.rs07.api.Tabs;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.api.util.CachedWidget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;
import org.osbot.rs07.utility.ConditionalSleep2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

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
        String optionsOrDoneComponent = script.getWidgets().get(
                TUTCONSTS.instructionsInterface, TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent)
                .getMessage();
        if (optionsOrDoneComponent != null && (optionsOrDoneComponent.contains("Please click on the" +
                " flashing spanner icon found") || optionsOrDoneComponent.contains("Don't worry" +
                "about these"))) {
            script.log("Skipping to options section");
            optionActions(script);
            return true;
        } else if (optionsOrDoneComponent != null && (optionsOrDoneComponent.contains("It's time to meet" +
                " your first instructor.") || optionsOrDoneComponent.contains("Follow the path to find the" +
                " next instructor"))) {
            script.log("Section already completed, attempting to proceed");
            return true;
        } else {
            //Initial interaction
            script.log("Attempting to talk to guide");
            new ConditionalSleep(5000) {
                @Override
                public boolean condition() throws InterruptedException {
                    return Utils.interactWithNpc(script.getNpcs().closest(TUTCONSTS.gielinorGuideID)
                            , "Talk-to Gielinor Guide", script) && script.getDialogues().isPendingContinuation();
                }
            }.sleep();
            script.sleep(Utils.randomInteractionTime(true));
            script.log("Successfully spoken to guide");
            //Iterating through first conversation
            while (script.getDialogues().isPendingContinuation()) {
                script.log("Continuing...");
                script.getDialogues().clickContinue();
                script.sleep(Utils.randomInteractionTime(true)); //ABC stuff
            }

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
            script.log("Clicked experience choice");
            script.sleep(800);
            script.sleep(Utils.randomInteractionTime(true));
            //Moving through post experience-selection dialogue
            while (!script.getWidgets().containingText("Click here to continue").isEmpty()) {
                script.getWidgets().getWidgetContainingText("Click here to continue").interact();
                script.sleep(Utils.randomInteractionTime(true));
            }
            script.sleep(Utils.randomInteractionTime(true));
            //*Should* be complete at this point, moving on to options section
            optionActions(script);
            return true;
        }
    }

    public void optionActions(Script script) throws InterruptedException {
        script.sleep(Utils.randomInteractionTime(true));
        script.log("Attempting to click options button");
        script.getWidgets().get(TUTCONSTS.bottomRowTabs, TUTCONSTS.optionsTab).interact();
        script.sleep(Utils.randomInteractionTime(true)); //Random ABC

        script.log("Attempting to switch UI modes");
        RS2Widget configureInterface = script.getWidgets().get(
                TUTCONSTS.optionsMenu, TUTCONSTS.optionsMenuAdvancedOptions,
                TUTCONSTS.optionsMenuAdvancedOptionsComponent);
        boolean result = configureInterface.interact();
        while (!result){
            script.sleep(Utils.boundedInteractionTime(1000, 1200));
            configureInterface.interact();
        }
        script.sleep(Utils.boundedInteractionTime(1000, 1200));
        if (configureInterface.isVisible()) {
            RS2Widget advancedInterface = script.getWidgets()
                    .containingActions(TUTCONSTS.advancedOptions, "Side-stones arrangement").get(0);

            advancedInterface.interact();
            script.sleep(Utils.boundedInteractionTime(800, 1200));
            advancedInterface = script.getWidgets().containingActions(TUTCONSTS.advancedOptions, "Close").get(0);
            advancedInterface.interact();
            script.sleep(Utils.randomInteractionTime(false));
        } else
            script.log("Error opening config");

        script.log("Attempting to talk to guide");
        Utils.interactWithNpc(script.getNpcs().closest(TUTCONSTS.gielinorGuideID), "Talk-to Gielinor Guide",
                script);
        script.sleep(Utils.randomInteractionTime(true));
        while (!script.getWidgets().containingText("Click here to continue").isEmpty()) {
            script.getWidgets().getWidgetContainingText("Click here to continue").interact();
            script.sleep(Utils.randomInteractionTime(true));
        }
    }

}
