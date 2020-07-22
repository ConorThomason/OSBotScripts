//package scripts.testFiles;
//
//import org.osbot.rs07.api.map.Position;
//import org.osbot.rs07.api.model.NPC;
//import org.osbot.rs07.script.Script;
//import org.osbot.rs07.script.ScriptManifest;
//import scripts.tutorialIsland.Utils;
//
//@ScriptManifest(author = "Hywok", name = "Nearest NPC to Position", info = "Searches for and returns list of NPCs" +
//        " at the position that contains an entity of the same name passed nearest to the position entered.",
//        version = 0.3, logo = "")
//public final class LocationTest extends Script {
//    @Override
//    public int onLoop() throws InterruptedException {
//        NPC rat = Utils.closestToPosition(new Position(3107, 9511, 0), "Giant rat",
//                this).get(0);
//        log(rat.getName() + rat.getPosition());
//        sleep(2000);
//        return 0;
//    }
//}
//Commented out to exclude from build and consequently script list