package scripts;

import com.thoughtworks.xstream.mapper.Mapper;
import org.jetbrains.annotations.Nullable;
import org.osbot.*;
import org.osbot.rs07.api.Widgets;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import scripts.TUTCONSTS;
import scripts.Utils;

import java.util.Random;


public class CharacterCreation {

    public void characterCreation(Script i) {
        nameChoice(i);
        appearanceChoice(i);
    }
    public void nameChoice(Script i) {
        //Name choose
        try {
            RS2Widget displayName = i.getWidgets().get(
                    TUTCONSTS.characterCreationInterface,TUTCONSTS.characterCreationDisplayName);
            RS2Widget lookupName = i.getWidgets().
                    get(TUTCONSTS.characterCreationInterface, TUTCONSTS.characterCreationLookupName);
            RS2Widget randomNameMiddle = i.getWidgets().get(TUTCONSTS.characterCreationInterface,
                    TUTCONSTS.characterCreationMiddleRandomNameSelection);
            RS2Widget setNameButton = i.getWidgets().get
                    (TUTCONSTS.characterCreationInterface, TUTCONSTS.characterCreationSetNameButtonParent);
            if(displayName.isVisible()){
                displayName.interact();
            }
            else {
                throw new NullPointerException();
            }
            i.sleep(Utils.boundedInteractionTime(1000,1500));
            Utils.randomTypingIntervals("ExtraName", i);
            i.sleep(Utils.randomInteractionTime(false));
            i.log("Pressing enter to prompt name lookup");
            i.keyboard.typeEnter();
            i.sleep(Utils.randomInteractionTime(false));
            i.log("Attempting to select suggested name");
            if (randomNameMiddle == null) {
                while (randomNameMiddle == null) {
                    i.sleep(1000);
                }
            }
            else{
                randomNameMiddle.interact();
            }
            i.sleep(Utils.randomInteractionTime(false));
            String nameContent = randomNameMiddle.getMessage();
            int iterator = 0;
            while (!i.getWidgets().get(558,11).getMessage().equals(nameContent)) {
                i.log("Attempting to select " + nameContent);
                i.log("Failed random name click, retrying...");
                i.sleep(Utils.randomInteractionTime(false));
                iterator = iterator + 1;
                if (!randomNameMiddle.isHidden() || iterator == 5) {
                    i.log("Retrying name creation");
                    characterCreation(i);
                    return;
                }
                else
                    break;
            }
            i.sleep(Utils.boundedInteractionTime(2000, 3000));
            while (displayName != null) {
                i.log("Attempting to confirm " + nameContent);
                i.log("Failed confirmation click, retrying...");
                i.sleep(Utils.randomInteractionTime(false));
                if (setNameButton != null) {
                    setNameButton.interact();
                    i.sleep(Utils.randomInteractionTime(false));
                }
                if(i.getWidgets().get(TUTCONSTS.characterCreationInterface, TUTCONSTS.characterCreationError).
                        getMessage().contains("We have encountered an error")) {
                    nameChoice(i);
                }
            }
            i.sleep(Utils.boundedInteractionTime(2000, 6000));
        } catch (NullPointerException | InterruptedException e) {
            e.printStackTrace();
            i.log("Skipping name selection");
        }
    }

    public void appearanceChoice(Script script) {
        try {
            script.log("Successfully confirmed name, initiating random appearance");
            //Random appearance
            script.sleep(Utils.boundedInteractionTime(100, 200));

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
            e.printStackTrace();
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

