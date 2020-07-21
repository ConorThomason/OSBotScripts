package scripts.tutorialIsland;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class CombatInstructor {

    public boolean combatInstructor(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        String bronzeDaggerComponent = script.getWidgets().get(
                TUTCONSTS.instructionsInterface, TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent)
                .getMessage();
        if (bronzeDaggerComponent != null && (bronzeDaggerComponent.contains("Click on the flashing crossed swords icon" +
                " to open the combat interface."))) {
            script.log("Skipping to melee section");
            meleeSection(script);
            return true;
        } else if (bronzeDaggerComponent != null && (bronzeDaggerComponent.contains
                ("Pass through the gate and talk to the combat instructor. He will give you ") || (bronzeDaggerComponent.contains
                ("Now you have a bow and some arrows.")))) {
            rangedSection(script);
            return true;
        } else if (bronzeDaggerComponent != null && (bronzeDaggerComponent.contains
                ("You have completed the tasks here"))) {
            return true;
        }
        script.log("Attempting to talk to combat instructor");
        Utils.interactWithNpc(script.getNpcs().closest("Combat Instructor"), "Talk-to", script);
        script.sleep(1000);
        while (!Utils.pendingContinuation(script)) {
            script.sleep(1000);
            Utils.interactWithNpc(script.getNpcs().closest("Combat Instructor"), "Talk-to", script);
        }
        script.sleep(1000);
        script.log("Continuing to end");
        Utils.continueToEnd(script);
        script.sleep(Utils.randomInteractionTime(false));
        script.log("Attempting to open equipment tab");
        boolean checkedEquipment = false;
        while (!checkedEquipment) {
            script.log("Checking equipment tab");
            checkedEquipment = script.getWidgets().get(TUTCONSTS.topRowTabs, TUTCONSTS.equipmentTab).interact();
            script.sleep(Utils.boundedInteractionTime(800, 1200));
            if (!script.getTabs().isOpen(Tab.EQUIPMENT)) {
                new ConditionalSleep(5000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        script.sleep(100);
                        return script.getTabs().isOpen(Tab.EQUIPMENT);
                    }
                }.sleep();
            }
        }
        script.sleep(Utils.boundedInteractionTime(1000, 1500));
        script.log("Attempting to open detailed information window");
        while (script.getWidgets().get(TUTCONSTS.detailedEquipmentInterface,
                TUTCONSTS.detailedEquipmentChild) == null) {
            script.getWidgets().get(TUTCONSTS.equipmentWindow, TUTCONSTS.detailedEquipmentWindow).interact();
            script.sleep(Utils.boundedInteractionTime(300, 700));
        }
        script.log("Attempting to close detailed information window");
        while (script.getWidgets().get(TUTCONSTS.detailedEquipmentInterface,
                TUTCONSTS.detailedEquipmentChild) != null) {
            script.getWidgets().containingActions(TUTCONSTS.detailedEquipmentInterface, "Close").get(0)
                    .interact();
            script.sleep(Utils.boundedInteractionTime(300, 700));
        }
        script.log("Attempting to equip bronze dagger");
        while (script.getInventory().contains("Bronze dagger") && !script.getEquipment().contains("Bronze dagger")) {
            script.getInventory().interact("Wield", "Bronze dagger");
            script.sleep(Utils.boundedInteractionTime(300, 700));
        }

        script.sleep(Utils.boundedInteractionTime(800, 1600));
        Utils.interactWithNpc(script.getNpcs().closest("Combat Instructor"), "Talk-to", script);
        script.sleep(Utils.randomInteractionTime(false));
        meleeSection(script);
        rangedSection(script);
        return true;
    }

    public boolean meleeSection(Script script) throws InterruptedException {
        script.log("Attempting to equip both bronze sword and wooden shield");
        while (!(script.getEquipment().isWearingItem(EquipmentSlot.WEAPON, "Bronze sword") &&
                script.getEquipment().isWearingItem(EquipmentSlot.SHIELD, "Wooden shield"))) {
            script.getEquipment().equip(EquipmentSlot.WEAPON, "Bronze sword");
            script.sleep(Utils.boundedInteractionTime(800, 1600));
            script.getEquipment().equip(EquipmentSlot.SHIELD, "Wooden shield");
            script.sleep(Utils.boundedInteractionTime(800, 1600));
        }
        script.sleep(Utils.randomInteractionTime(false));

        script.log("Attempting to open attack tab");
        boolean checkedAttackTab = false;
        while (!checkedAttackTab) {
            script.log("Checking attack tab");
            checkedAttackTab = script.getWidgets().containingActions(TUTCONSTS.topRowTabs, "Combat Options").get(0)
                    .interact("Combat Options");
            script.sleep(Utils.boundedInteractionTime(800, 1200));
            if (!script.getTabs().isOpen(Tab.ATTACK)) {
                new ConditionalSleep(5000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        script.sleep(100);
                        return script.getTabs().isOpen(Tab.ATTACK);
                    }
                }.sleep();
            }
        }
        script.log("Attempting to walk into giant rat area");
        script.getCamera().toEntity(script.getNpcs().closest("Combat Instructor"));
        script.sleep(Utils.randomInteractionTime(false));
        script.getWalking().webWalk(new Position(3110, 9518, 0));
        script.sleep(Utils.randomInteractionTime(false));

        //Attack rat
        String swordMessageComponent = script.getWidgets().get(
                TUTCONSTS.instructionsInterface, TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent)
                .getMessage();
//        while (swordMessageComponent.contains("Click here to continue") || swordMessageComponent.contains(
//                "While you are fighting you will see a bar over your head") || swordMessageComponent.contains("" +
//                        "It's time to slay some rats!")
//        ) {
        script.log("Attempting to attack giant rat (Melee)");
        NPC giantRat = script.getNpcs().closest("Giant rat");
        script.sleep(Utils.randomInteractionTime(false));

        while (giantRat.isUnderAttack() || giantRat.getHealthPercent() < 100) {
            giantRat = script.getNpcs().closest("Giant rat");
        }
        giantRat.interact("Attack");
        while (giantRat.getHealthPercent() != 0) {
            script.sleep(Utils.randomInteractionTime(false));
        }
        script.log("Giant rat should be dead");
        script.sleep(100);
        script.sleep(Utils.randomInteractionTime(false));
        script.log("Walking to Combat Instructor");
        boolean interaction = script.getObjects().closest("Gate").interact();
        script.sleep(Utils.randomInteractionTime(false));
        while (!interaction) {
            interaction = script.getObjects().closest("Gate").interact();
            if (script.myPosition().equals(new Position(3111, 9518, 0)) || script.myPosition().equals(
                    new Position(3111, 9519, 0)
            ))
                break;
            script.sleep(Utils.randomInteractionTime(false));
        }
        script.getWalking().walk(script.getNpcs().closest("Combat Instructor").getPosition());
        while (script.myPlayer().isMoving()) {
            script.sleep(Utils.randomInteractionTime(false));
        }
        return true;
    }

    public boolean rangedSection(Script script) throws InterruptedException {
        try {
            while (script.getWidgets().getWidgetContainingText(TUTCONSTS.instructionsInterface, "Pass through the" +
                    " gate and talk to the combat instructor").getMessage().contains("Pass through the" +
                    " gate and talk to the combat instructor")) {
                Utils.interactWithNpc(script.getNpcs().closest("Combat Instructor"), "Talk-to", script);
                script.sleep(1000);
                new ConditionalSleep(5000) {
                    @Override
                    public boolean condition() throws InterruptedException {
                        return Utils.continueToEnd(script);
                    }
                }.sleep();
            }
        } catch (NullPointerException e) {
            //nop
        }
        script.sleep(3000);
        //Equip bow and arrows
        script.log("Attempting to equip shortbow and bronze arrows");
        while (!script.getEquipment().isWearingItem(EquipmentSlot.WEAPON, "Shortbow") &&
                !script.getEquipment().isWearingItem(EquipmentSlot.ARROWS, "Bronze arrow")) {
            try {
                script.getEquipment().equip(EquipmentSlot.WEAPON, "Shortbow");
            } catch (NullPointerException e) {
                //nop
            }
            script.sleep(Utils.boundedInteractionTime(800, 1600));
            try {
                script.getEquipment().equip(EquipmentSlot.ARROWS, "Bronze arrow");
            } catch (NullPointerException e) {
            }
            script.sleep(Utils.boundedInteractionTime(800, 1600));
        }
        script.sleep(Utils.randomInteractionTime(false));
        script.getWalking().walk(new Position(3107, 9511, 0));
        script.sleep(Utils.boundedInteractionTime(1000, 1800));
        script.log("Attempting to attack Giant rat (Ranged)");
        NPC rangedGiantRat = script.getNpcs().closest("Giant rat");
        String bowMessageComponent = script.getWidgets().get(
                TUTCONSTS.instructionsInterface, TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent)
                .getMessage();
//        while (bowMessageComponent.contains("Now you have a bow and some arrows")) {
        //Now you have a bow and some arrows
        while (rangedGiantRat.isUnderAttack() || rangedGiantRat.getHealthPercent() < 100) {
            rangedGiantRat = script.getNpcs().closest("Giant rat");
        }
        rangedGiantRat.interact("Attack");
        script.sleep(1000);
        while (rangedGiantRat.getHealthPercent() != 0) {
            script.sleep(Utils.randomInteractionTime(false));
            try {
                script.getWidgets().getWidgetContainingText(TUTCONSTS.cantReachThatInterface,
                        "Click here to continue").interact();
            } catch (NullPointerException e) {
                //nop
            }
        }
        script.log("Giant rat should be dead");
        try {
            script.getWidgets().getWidgetContainingText(TUTCONSTS.cantReachThatInterface,
                    "Click here to continue").interact();
        } catch (NullPointerException e) {
            //nop
        }
        bowMessageComponent = script.getWidgets().get(
                TUTCONSTS.instructionsInterface, TUTCONSTS.instructionsChild, TUTCONSTS.instructionsComponent)
                .getMessage();
        script.sleep(100);
//        }
        script.log("Combat Instructor phase complete, traveling...");
        //Should now be attacking rat with bow. Upon completion, while loop will stop and return true
        //Travel should take over after this
        return true;
    }
}
