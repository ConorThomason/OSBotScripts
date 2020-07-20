package scripts;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

public enum TutorialIslandLocations {
    GUIDE_ROOM(new Area(new Position(3091, 3110, 0), new Position(3097, 3105, 0))),
    SURVIVAL_EXPERT(new Area(new Position(3101, 3097, 0), new Position(3104, 3094, 0))),
    MASTER_NAVIGATOR(new Area(new Position(3078, 3082, 0), new Position(3074, 3086, 0))),
    QUEST_GUIDE(new Area(new Position(3089, 3119, 0), new Position(3080, 3127, 0))),
    MINING_INSTRUCTOR(new Area(new Position(3078, 9508, 0), new Position(3082, 9504, 0))),
    COMBAT_INSTRUCTOR(new Area(new Position(3100, 9503, 0), new Position(3108, 9512, 0))),
    ACCOUNT_GUIDE(new Area(new Position(3125, 3124, 0), new Position(3129, 3125, 0))),
    BROTHER_BRACE(new Area(new Position(3120, 3110, 0), new Position(3128, 3103, 0))),
    BANK(new Area(new Position(3118, 3125, 0), new Position(3124, 3119, 0))),
    MAGIC_INSTRUCTOR(new Area(new Position(3141, 3084, 0), new Position(3143, 3090, 0)));

    private Area location;

    TutorialIslandLocations(Area location) {
        this.location = location;
    }

    public Area getLocation() {
        return this.location;
    }

    public static TutorialIslandLocations getLocationName(Position playerPos) {
        if (GUIDE_ROOM.getLocation().contains(playerPos)) {
            return GUIDE_ROOM;
        } else if (SURVIVAL_EXPERT.getLocation().contains(playerPos)) {
            return SURVIVAL_EXPERT;
        } else if (MASTER_NAVIGATOR.getLocation().contains(playerPos)) {
            return MASTER_NAVIGATOR;
        } else if (QUEST_GUIDE.getLocation().contains(playerPos)) {
            return QUEST_GUIDE;
        } else if (MINING_INSTRUCTOR.getLocation().contains(playerPos)) {
            return MINING_INSTRUCTOR;
        } else if (COMBAT_INSTRUCTOR.getLocation().contains(playerPos)) {
            return COMBAT_INSTRUCTOR;
        } else if (ACCOUNT_GUIDE.getLocation().contains(playerPos)) {
            return ACCOUNT_GUIDE;
        } else if (BROTHER_BRACE.getLocation().contains(playerPos)) {
            return BROTHER_BRACE;
        } else if (BANK.getLocation().contains(playerPos)) {
            return BANK;
        } else {
            return MAGIC_INSTRUCTOR;
        }

    }
}
