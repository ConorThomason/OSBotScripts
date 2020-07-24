package scripts.tutorialIsland;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;

import java.util.Random;


public class CharacterCreation {

    public void characterCreation(Script i) {
        nameChoice(i);
        appearanceChoice(i);
    }
    public void nameChoice(Script script) {
        //Name choose
        try {
            RS2Widget displayName = script.getWidgets().get(
                    TUTCONSTS.characterCreationInterface,TUTCONSTS.characterCreationDisplayName);
            RS2Widget lookupName = script.getWidgets().
                    get(TUTCONSTS.characterCreationInterface, TUTCONSTS.characterCreationLookupName);
            RS2Widget randomNameMiddle = script.getWidgets().get(TUTCONSTS.characterCreationInterface,
                    TUTCONSTS.characterCreationMiddleRandomNameSelection);
            RS2Widget setNameButton = script.getWidgets().get
                    (TUTCONSTS.characterCreationInterface, TUTCONSTS.characterCreationSetNameButtonParent);
            RS2Widget characterCreationNotice = script.getWidgets().get
                    (TUTCONSTS.characterCreationInterface, TUTCONSTS.characterCreationError);
            if (displayName == null){
                throw new NullPointerException();
            }
            Timing.waitCondition(() -> displayName.interact(), 1000, 10000);
            script.sleep(Utils.randomInteractionTime(false));
            Utils.randomTypingIntervals("ExtraName", script);
            String currentNotice = characterCreationNotice.getMessage();
            Timing.waitCondition(() -> lookupName.interact(), 1000, 10000);
            String finalCurrentNotice = currentNotice;
            Timing.waitCondition(() -> !characterCreationNotice.getMessage().equals(finalCurrentNotice), 100, 5000);
            script.sleep(Utils.randomInteractionTime(false));

            if (characterCreationNotice.getMessage().contains("not available") || characterCreationNotice.getMessage()
            .contains("error")){
                String retryNotice;
                while (!characterCreationNotice.getMessage().contains("Great!")) {
                    retryNotice = characterCreationNotice.getMessage();
                    Timing.waitCondition(() -> randomNameMiddle.interact(), 1000, 10000);
                    script.sleep(Utils.randomInteractionTime(false));
                    String finalRetryNotice = retryNotice;
                    Timing.waitCondition(() -> !finalRetryNotice.equals(finalCurrentNotice),
                            100, 5000);
                }
            }
            Timing.waitCondition(() -> setNameButton.interact(), 1000, 10000);
            script.sleep(Utils.randomInteractionTime(false));
            if (characterCreationNotice.getMessage().contains("error")){
                nameChoice(script);
            }
            try {
                Timing.waitCondition(() -> !characterCreationNotice.getMessage().contains("Requesting"),
                        100, 10000);
            } catch (NullPointerException f){
                //nop, name should be accepted
            }
        } catch (NullPointerException | InterruptedException e) {
            e.printStackTrace();
            script.log("Skipping name selection");
        }
    }

    public void appearanceChoice(Script script) {
        try {
            script.log("Successfully confirmed name, initiating random appearance");
            //Random appearance
            script.sleep(Utils.randomInteractionTime(false));

            final int[] leftArrows = {106, 107, 108, 109, 110, 111, 112, 105, 123, 122, 124, 125};
            final int[] rightArrows = {113, 114, 115, 116, 117, 118, 119, 121, 127, 129, 130, 131};
            final String[] options = {
                    "Change head",
                    "Change jaw",
                    "Change torso",
                    "Change arms",
                    "Change hands",
                    "Change legs",
                    "Change feet",
                    "Recolour hair",
                    "Recolour torso",
                    "Recolour legs",
                    "Recolour feet",
                    "Recolour skin"
            };
            for (int i = 0; i < 12; i++) {
                randomCharFeature(leftArrows[i], rightArrows[i], options[i], script);
                if (script.random(0, 5) == 5) {
                    int rand = script.random(0, Math.max(i - 1, 0));//0 <= rand <= 11
                    randomCharFeature(leftArrows[rand], rightArrows[rand], options[rand], script);
                }
            }
            Random randomGenerator = new Random();
            int extra_changes = (int)randomGenerator.nextGaussian()*3+1;
            for (int i = 0; i <= extra_changes; i++) {
                int rand = script.random(0, Math.max(i - 1, 0));
                randomCharFeature(leftArrows[rand], rightArrows[rand], options[rand], script);
            }
            script.getWidgets().get(269, 100).interact("Accept");
        } catch (NullPointerException | InterruptedException e) {
            script.log("Character already created, skipping to appropriate phase");
        }

    }

    private void randomCharFeature(int l, int r, String option, Script script) throws InterruptedException {//l is the interface child ID of the left arrow r is the ID of the right
        try {
            Random randomGenerator = new Random();
            RS2Widget left = script.getWidgets().get(269, l);
            RS2Widget right = script.getWidgets().get(269, r);
            int max_shifts = 5;
            int set_shifts = script.random(0, max_shifts);
            boolean swap = true;
            int shifts = 0;
            while (shifts < max_shifts) {//loop for randomizing character initialization
                for (int i = 0; i < set_shifts; i++) {
                    if (swap)
                        left.interact(option);
                    else
                        right.interact(option);
                    shifts++;
                    script.sleep(Utils.boundedInteractionTime(5, 20));
                }
                if (script.random(0, 1) == 1)
                    swap = true;
                else
                    swap = false;
                set_shifts = script.random(0, max_shifts - set_shifts);
            }
        } catch (NullPointerException e){

        }
    }//used in character creation
}

