package scripts.tutorialIsland;

import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class MagicInstructor {
    public boolean magicInstructor(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        if (!script.getWidgets().containingText(TUTCONSTS.instructionsInterface, "You now have some runes")
                .isEmpty()) {
            magicPhase(script);
            return true;
        }
        script.log("Attempting to talk to Magic Instructor");
        Utils.interactWithNpc(script.getNpcs().closest("Magic Instructor"), "Talk-to", script);
        script.sleep(1000);
        while (!Utils.pendingContinuation(script)) {
            script.sleep(1000);
            Utils.interactWithNpc(script.getNpcs().closest("Magic Instructor"), "Talk-to", script);
        }
        script.sleep(1000);
        script.log("Continuing to end");
        Utils.continueToEnd(script);
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        script.log("Interacting with spell tab");
        script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.spellTab).interact();
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        script.log("Attempting to talk to Magic Instructor");
        new ConditionalSleep(5000) {
            @Override
            public boolean condition() throws InterruptedException {
                return Utils.interactWithNpc(script.getNpcs().closest("Magic Instructor"),
                        "Talk-to", script);
            }
        }.sleep();
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        Utils.continueToEnd(script);
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        magicPhase(script);
        script.sleep(1000);
        postMagicPhase(script);
        return true;
    }

    public boolean magicPhase(Script script) throws InterruptedException {
        try {
            if (Utils.pendingContinuation(script)) {
                Utils.continueToEnd(script);
            }
            NPC chicken = script.getNpcs().closest("Chicken");
            boolean result = script.getMagic().castSpellOnEntity(Spells.NormalSpells.WIND_STRIKE, chicken);
            while(!result){
                script.sleep(Utils.boundedInteractionTime(1500, 2400));
                result = script.getMagic().castSpellOnEntity(Spells.NormalSpells.WIND_STRIKE, chicken);
            }
        } catch (IndexOutOfBoundsException e) {
            //nop
        }
        return true;
    }

    public boolean postMagicPhase(Script script) throws InterruptedException {
        script.log("Attempting to talk to Magic Instructor after magic");
        new ConditionalSleep(5000) {
            @Override
            public boolean condition() throws InterruptedException {
                return Utils.interactWithNpc(script.getNpcs().closest("Magic Instructor"),
                        "Talk-to", script);
            }
        }.sleep();
        script.sleep(Utils.boundedInteractionTime(1000, 1400));
        Utils.continueToEnd(script);
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        script.log("Selecting yes");
        script.getDialogues().selectOption(1);
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        Utils.continueToEnd(script);
        script.sleep(Utils.boundedInteractionTime(800, 1200));
        return true;
    }
}
