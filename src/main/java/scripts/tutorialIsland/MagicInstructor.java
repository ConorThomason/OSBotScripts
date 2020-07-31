package scripts.tutorialIsland;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MagicInstructor {
    public boolean magicInstructor(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 620:
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
            case 630:
                script.log("Interacting with spell tab");
                Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.spellTab).interact(),
                        1000, 8000);
                script.sleep(Utils.randomInteractionTime(false));
            case 640:
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
            case 650:
                magicPhase(script);
            case 670:
                postMagicPhase(script);
        }
        return true;
    }

    public boolean magicPhase(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 650:
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
                        NPC chicken = script.getNpcs().closest("Chicken");
                        Timing.waitCondition(() -> script.getMagic().castSpellOnEntity(
                                    Spells.NormalSpells.WIND_STRIKE, chicken), 3000, 12000);
                        script.sleep(Utils.randomInteractionTime(false));
                        Utils.interruptionCheck(script);
                        script.sleep(Utils.randomInteractionTime(true));
                        break;
                    }
                } catch (IndexOutOfBoundsException e) {
                    //nop
                }
                Utils.randomInteractionTime(false);
            default:
                break;
        }
        return true;
    }

    public boolean postMagicPhase(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 670:
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
        }
        return true;
    }
}
