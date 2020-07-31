package scripts.tutorialIsland;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class CombatInstructor {

    final String skipString1 = "Click on the flashing crossed swords icon to open the combat interface.";
    final String skipString2a = "Pass through the gate and talk to the combat instructor. He will give you ";
    final String skipString2b = "Now you have a bow and some arrows.";
    final String skipString3 = "You have completed the tasks here";

    public boolean combatInstructor(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 370:
                Utils.interruptionCheck(script);
                script.log("Attempting to talk to combat instructor");
                Utils.interactWithNpc(script.getNpcs().closest("Combat Instructor"), "Talk-to", script);
                script.sleep(1000);
                while (!Utils.pendingContinuation(script)) {
                    script.sleep(1000);
                    Utils.interactWithNpc(script.getNpcs().closest("Combat Instructor"), "Talk-to", script);
                }
                Timing.waitCondition(() -> {
                    try {
                        return Utils.interactWithNpc(script.getNpcs().closest("Combat Instructor"),
                                "Talk-to", script);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                }, 1000, 10000);
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> {
                    try {
                        return Utils.pendingContinuation(script);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                }, 1000, 10000);
                Script.sleep(Utils.randomInteractionTime(false));
                script.log("Continuing to end");
                Utils.continueToEnd(script);
                script.sleep(Utils.randomInteractionTime(false));
            case 390:
                script.log("Attempting to open equipment tab");
                boolean checkedEquipment = false;
                while (!checkedEquipment) {
                    script.log("Checking Equipment tab");
                    checkedEquipment = Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.equipmentTab)
                            .interact(), 1000, 5000);
                    script.sleep(Utils.randomInteractionTime(false));
                    if (!script.getTabs().isOpen(Tab.EQUIPMENT)) {
                        Timing.waitCondition(() -> script.getTabs().isOpen(Tab.EQUIPMENT), 100, 5000);
                    }
                }
            case 400:
                script.log("Equipment tab should be open");
                script.sleep(Utils.randomInteractionTime(false));
                script.log("Attempting to open detailed information window");
                Timing.waitCondition(() -> script.getWidgets().get(
                        TUTCONSTS.equipmentWindow, TUTCONSTS.detailedEquipmentWindow).interact(), 1250, 7500);
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.detailedEquipmentInterface,
                        TUTCONSTS.detailedEquipmentChild) != null, 100, 6000);
                script.sleep(Utils.randomInteractionTime(false));
            case 405:
                script.log("Attempting to close detailed information window");
                Timing.waitCondition(() -> script.getWidgets().containingActions(TUTCONSTS.detailedEquipmentInterface, "Close").get(0)
                        .interact(), 1250, 7500);
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.detailedEquipmentInterface,
                        TUTCONSTS.detailedEquipmentChild) == null, 100, 5000);
                script.sleep(Utils.randomInteractionTime(false));
            case 410:
                script.log("Attempting to equip bronze dagger");
                Timing.waitCondition(() -> script.getInventory().interact("Wield", "Bronze dagger"), 1000,
                        8000);
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> script.getInventory().contains("Bronze dagger") &&
                        !script.getEquipment().contains("Bronze dagger"), 100, 7500);
                script.sleep(Utils.randomInteractionTime(false));
            case 420:
            case 430:
            case 440:
            case 450:
            case 460:
            case 470:
                Utils.interactWithNpc(script.getNpcs().closest("Combat Instructor"), "Talk-to", script);
                Timing.waitCondition(() -> {
                    try {
                        return Utils.pendingContinuation(script);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                }, 100, 8000);
                script.sleep(Utils.randomInteractionTime(false));
                meleeSection(script);
            case 480:
                script.sleep(Utils.randomInteractionTime(false));
                rangedSection(script);
        }
        return true;
    }

    public boolean meleeSection(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 420:
                script.log("Attempting to equip both bronze sword and wooden shield");
                Timing.waitCondition(() -> script.getEquipment().equip(EquipmentSlot.WEAPON, "Bronze sword") ||
                                script.getEquipment().isWearingItem(EquipmentSlot.WEAPON, "Bronze Sword"),
                        1000, 8000);
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> script.getEquipment().equip(EquipmentSlot.SHIELD, "Wooden shield") ||
                                script.getEquipment().isWearingItem(EquipmentSlot.SHIELD, "Wooden shield"),
                        1000, 8000);
                script.sleep(Utils.randomInteractionTime(false));
                script.sleep(Utils.randomInteractionTime(false));
            case 430:
                script.log("Attempting to open attack tab");
                Timing.waitCondition(() -> script.getWidgets().containingActions(TUTCONSTS.topRowTabs,
                        "Combat Options").get(0).interact("Combat Options"), 1000, 8000);
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> script.getTabs().isOpen(Tab.ATTACK), 100, 5000);
                script.sleep(Utils.randomInteractionTime(false));
            case 440:
                while (!script.getTabs().isOpen(Tab.EQUIPMENT)) {
                    script.log("Checking eqipment tab");
                    Timing.waitCondition(() -> script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.equipmentTab)
                            .interact(), 1000, 5000);
                    script.sleep(Utils.randomInteractionTime(false));
                    if (!script.getTabs().isOpen(Tab.EQUIPMENT)) {
                        Timing.waitCondition(() -> script.getTabs().isOpen(Tab.EQUIPMENT), 100, 5000);
                    }
                }
                script.log("Attempting to walk into giant rat area");
                script.sleep(Utils.randomInteractionTime(false));
                script.getWalking().webWalk(new Position(3110, 9518, 0));
                script.sleep(Utils.randomInteractionTime(false));
                //Attack rat
                String swordMessageComponent = script.getWidgets().get(
                        TUTCONSTS.instructionsInterface, TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent)
                        .getMessage();
            case 450:
            case 460:
                script.log("Attempting to attack giant rat (Melee)");
                NPC giantRat = script.getNpcs().closest("Giant rat");
                while (!script.getWidgets().containingText("Sit back and watch").isEmpty() ||
                        !script.getWidgets().containingText("It's time to slay some").isEmpty()) {
                    int offset = 0;
                    do {
                        Utils.interruptionCheck(script);
                        giantRat = Utils.closestToPosition(script.myPosition(), "Giant rat", script, offset++).get(0);
                    } while (giantRat.isUnderAttack() || giantRat.getHealthPercent() < 100);
                    giantRat.interact("Attack");
                    while (giantRat.getHealthPercent() != 0) {
                        script.sleep(Utils.randomInteractionTime(false));
                    }
                    Utils.interruptionCheck(script);
                    if (!script.getWidgets().containingText("Well done, you've made your first kill").isEmpty() ||
                            !script.getWidgets().containingText("Perhaps you should move on to learn about ranged combat")
                                    .isEmpty()) {
                        Utils.interruptionCheck(script);
                        break;
                    }
                    script.sleep(Utils.randomInteractionTime(true));
                }
                script.log("Giant rat should be dead");
            case 470:
                script.log("Walking to Combat Instructor");
                Timing.waitCondition(() -> script.getObjects().closest("Gate").interact(),
                        1000, 10000);
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> script.myPosition().equals(new Position(3111, 9518, 0)) || script.myPosition().equals(
                        new Position(3111, 9519, 0)), 100, 12000);
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> script.getWalking().walk(script.getNpcs().closest("Combat Instructor")
                        .getPosition()), 1000, 10000);
                Timing.waitCondition(() -> script.myPlayer().isMoving(), 100, 10000);
                Timing.waitCondition(() -> {
                    try {
                        return Utils.interactWithNpc(script.getNpcs().closest("Combat Instructor"),
                                "Talk-to", script);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                }, 1000, 8000);
                script.sleep(Utils.randomInteractionTime(false));
                Timing.waitCondition(() -> {
                    try {
                        return Utils.pendingContinuation(script);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                }, 100, 10000);
                script.sleep(Utils.randomInteractionTime(false));
                Utils.continueToEnd(script);
        }
        return true;
    }
    public boolean rangedSection(Script script) throws InterruptedException {
        switch (script.getConfigs().get(281)) {
            case 480:
                //Equip bow and arrows
                script.log("Attempting to equip shortbow and bronze arrows");
                try {
                    Timing.waitCondition(() -> !(!script.getEquipment().isWearingItem(EquipmentSlot.WEAPON, "Shortbow") &&
                                    !script.getEquipment().equip(EquipmentSlot.WEAPON, "Shortbow")),
                            600, 10000); //DeMorgan's law trickery
                } catch (NullPointerException e) {
                    script.log("Attempt to equip Shortbow failed; not found");
                }
                script.sleep(Utils.randomInteractionTime(false));
                try {
                    Timing.waitCondition(() -> !(!script.getEquipment().isWearingItem(EquipmentSlot.ARROWS, "Bronze arrow")
                                    && !script.getEquipment().equip(EquipmentSlot.ARROWS, "Bronze arrow")), //DeMorgan's
                            1000, 10000);
                } catch (NullPointerException e) {
                    script.log("Attempt to equip Bronze arrow failed; not found");
                }
                script.sleep(Utils.randomInteractionTime(false));
                script.log("Attempting to move to ideal spot");
//        Timing.waitCondition(() -> script.getWalking().walk(new Position(3107, 9511, 0)),
//                1000, 10000);
//        script.sleep(Utils.randomInteractionTime(false));
//        script.log("Attempting to check position");
//        Timing.waitCondition(() -> script.myPosition().distance(new Position(3107, 9511, 0)) == 0
//                , 1000, 10000);
//        script.sleep(Utils.randomInteractionTime(false));
            case 490:
                //Now you have a bow and some arrows
                while (script.getConfigs().get(281) == 490) {
                    NPC rangedGiantRat = script.getNpcs().closest("Giant rat");
                    script.sleep(Utils.randomInteractionTime(false));
                    Position idealPos = new Position(3108, 9512, 0);
                    int offset = 0;
                    do {
                        rangedGiantRat = Utils.closestToPosition(idealPos, "Giant rat",
                                script, offset++).get(0);
                    } while (rangedGiantRat.isUnderAttack() || rangedGiantRat.getHealthPercent() < 100);
                    NPC finalRangedGiantRat = rangedGiantRat;
                    script.log("Attempting to attack Giant rat (Ranged)");
                    Timing.waitCondition(() -> finalRangedGiantRat.interact("Attack"), 1000, 10000);
                    while (rangedGiantRat.getHealthPercent() != 0) {
                        Utils.interruptionCheck(script);
                        script.sleep(Utils.randomInteractionTime(false));
                    }
                    script.log("Giant rat should be dead");
                    Utils.interruptionCheck(script);
                    script.sleep(Utils.randomInteractionTime(false));
                    script.log("Combat Instructor phase complete, traveling...");
                    //Should now be attacking rat with bow. Upon completion, while loop will stop and return true
                    //Travel should take over after this
                    script.sleep(Utils.randomInteractionTime(false));
                }
            case 500:
                //nop
            default:
                break;
        }
        return true;
    }
}
