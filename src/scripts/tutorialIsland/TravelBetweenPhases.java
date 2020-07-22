package scripts.tutorialIsland;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.Arrays;

public class TravelBetweenPhases {

    public boolean travelFromGuide(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.log("Traveling from Gielinor Guide");
        RS2Object door = script.getObjects().closest("Door");
        script.getCamera().toEntity(door);
        script.sleep(Utils.randomInteractionTime(false));
        script.log("Attempting to walk to door");
        script.getWalking().webWalk(new Position(3103, 3096, 0));
        Timing.waitCondition(() -> script.myPosition().equals(new Position(3103, 3096, 0)),
                500, 10000);
        script.log("Walked to Survival Expert");
        return true;
    }

    public boolean travelFromSurvivalExpert(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        Area area = new Area(new Position(3089, 3093, 0), new Position(3088, 3090, 0));
        script.log("Traveling from survival expert");
        while (!area.contains(script.myPosition())) {
            RS2Object gate = script.getObjects().closest("Gate");
            boolean isClosed = doorIsClosed(gate, script);
            script.getWalking().walk(new Position(3090, 3091, 0));
            Timing.waitCondition(() -> {
                try {
                    return doorIsClosed(gate, script);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }, 300, 6000);
            script.sleep(Utils.randomInteractionTime(false));
            gate.interact("Open");
            script.sleep(800);

            while (script.myPlayer().isMoving()) {
                Timing.waitCondition(() -> script.myPlayer().isMoving(), 250, 4000);
            }
        }
        script.sleep(Utils.randomInteractionTime(false));
        script.log("Walking away from gate");
        script.getWalking().walk(new Position(3079, 3084, 0));
        Timing.waitCondition(() -> script.myPlayer().isMoving(), 250, 8000);
        Timing.waitCondition(() -> script.getObjects().closest("Door").interact(), 100, 1500);
        script.sleep(Utils.randomInteractionTime(false));
        Timing.waitCondition(() -> TutorialIslandLocations.MASTER_NAVIGATOR.
                getLocation().contains(script.myPosition()), 200, 10000);
        return true;
    }

    public boolean travelFromMasterNavigator(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.log("Traveling from master navigator");
        script.getWalking().webWalk(new Position(3086, 3125, 0));
        return true;
    }

    public boolean travelFromQuestGuide(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.log("Traveling from Quest Guide");
        script.getWalking().webWalk(new Position(3080, 9505, 0));
        return true;
    }

    public boolean travelFromMining(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.log("Traveling from Mining Expert");
        script.getWalking().webWalk(new Position(3104, 9507, 0));
        return true;
    }

    public boolean travelFromCombat(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.log("Traveling from Combat Instructor");
//        script.getWalking().webWalk(new Position(3111, 9525, 0));
//        while (script.myPlayer().isMoving())
//            script.sleep(Utils.randomInteractionTime(false));
//        script.getObjects().closest("Ladder").interact("Climb");
//        script.sleep(Utils.randomInteractionTime(false));
        script.getWalking().webWalk(new Position(3122, 3123, 0));
        return true;
    }

    public boolean travelFromBank(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.getWalking().webWalk(new Position(3125, 3124, 0));
        return true;
    }

    public boolean travelFromAccountGuide(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.getWalking().webWalk(new Position(3126, 3107, 0));
        return true;
    }

    public boolean travelFromBrotherBrace(Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        script.getWalking().webWalk(new Position(3140, 3090, 0));
        return true;
    }

    public boolean doorIsClosed(RS2Object door, Script script) throws InterruptedException {
        Utils.interruptionCheck(script);
        try {
            return Arrays.asList(door.getDefinition().getActions()).contains("Open");
        } catch (NullPointerException e) {
            script.sleep(Utils.randomInteractionTime(false));
            return doorIsClosed(door, script);
        }
    }


}
