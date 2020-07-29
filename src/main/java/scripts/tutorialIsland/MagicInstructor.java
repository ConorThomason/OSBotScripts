package scripts.tutorialIsland;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.List;

public class MagicInstructor {
    public boolean magicInstructor(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        if (!script.getWidgets().containingText(TUTCONSTS.instructionsInterface, "You now have some runes")
                .isEmpty()) {
            magicPhase(script);
            postMagicPhase(script);
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
            //X: 3138-3140 Y: 3092-3094
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
            Area chickenAttack = new Area(new Position(3138, 3092, 0),
                    new Position(3140, 3094, 0));
            while (!script.getWidgets().containingText(TUTCONSTS.instructionsInterface, "You now have some runes")
                    .isEmpty()) {
                List<NPC> chickens = script.getNpcs().getAll();
                for (NPC chicken: chickens){
                    if (chickenAttack.contains(chicken.getPosition()))
                        Timing.waitCondition(() -> script.getMagic().castSpellOnEntity(
                                Spells.NormalSpells.WIND_STRIKE, chicken), 1950, 12000);
                    script.sleep(Utils.randomInteractionTime(false));
                    Utils.interruptionCheck(script);
                    script.sleep(Utils.randomInteractionTime(true));
                    break;
                }
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
