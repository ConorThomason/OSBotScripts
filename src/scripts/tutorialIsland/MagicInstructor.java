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
        Timing.waitCondition(() -> {
            try {
                return Utils.pendingContinuation(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 100, 3500);
        Timing.waitCondition(() -> {
            try {
                return Utils.continueToEnd(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 1000, 8000);
        script.sleep(Utils.randomInteractionTime(false));
        script.log("Interacting with spell tab");
        Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.spellTab).interact(),
                1000, 8000);
        script.sleep(Utils.randomInteractionTime(false));
        script.log("Attempting to talk to Magic Instructor");
        Utils.interactWithNpc(script.getNpcs().closest("Magic Instructor"), "Talk-to", script);
        Timing.waitCondition(() -> {
            try {
                return Utils.pendingContinuation(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 100, 3500);
        Timing.waitCondition(() -> {
            try {
                return Utils.continueToEnd(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 1000, 8000);
        magicPhase(script);
        postMagicPhase(script);
        return true;
    }

    public boolean magicPhase(Script script) throws InterruptedException {
        try {
            Timing.waitCondition(() -> {
                try {
                    return Utils.pendingContinuation(script);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }, 100, 3500);
            Timing.waitCondition(() -> {
                try {
                    return Utils.continueToEnd(script);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }, 1000, 8000);
            while (!script.getWidgets().containingText(TUTCONSTS.instructionsInterface, "You now have some runes")
                    .isEmpty()) {
                NPC chicken = script.getNpcs().closest("Chicken");
                Timing.waitCondition(() -> script.getMagic().castSpellOnEntity(
                        Spells.NormalSpells.WIND_STRIKE, chicken), 1950, 12000);
                script.sleep(Utils.randomInteractionTime(false));
            }

        } catch (IndexOutOfBoundsException e) {
            //nop
        }
        Utils.randomInteractionTime(false);
        return true;
    }

    public boolean postMagicPhase(Script script) throws InterruptedException {
        script.log("Attempting to talk to Magic Instructor after magic");
        Utils.interactWithNpc(script.getNpcs().closest("Magic Instructor"), "Talk-to", script);
        Timing.waitCondition(() -> {
            try {
                return Utils.pendingContinuation(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 100, 3500);
        Timing.waitCondition(() -> {
            try {
                return Utils.continueToEnd(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 1000, 8000);
        script.log("Selecting yes");
        Timing.waitCondition(() -> script.getDialogues().selectOption(1), 1000, 8000);
        Timing.waitCondition(() -> {
            try {
                return Utils.pendingContinuation(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 100, 3500);
        Timing.waitCondition(() -> {
            try {
                return Utils.continueToEnd(script);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }, 1000, 8000);
        script.sleep(Utils.randomInteractionTime(false));
        return true;
    }
}
