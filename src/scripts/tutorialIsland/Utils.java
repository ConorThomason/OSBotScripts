package scripts.tutorialIsland;

import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

import java.util.Random;

public class Utils {
    public static int randomInteractionTime(boolean chat) {
        Random rnd = new Random();
        if (chat)
            return (int) Math.floor((rnd.nextGaussian()*800) + 1000);
        else
            return (int) Math.floor((rnd.nextGaussian()*800) + 300);
    }

    public static int boundedInteractionTime(int low, int high){
        Random rnd = new Random();
        return (int) Math.floor((rnd.nextGaussian()*(high-low)) + low);
    }

    public static void randomTypingIntervals(String typing, Script script) throws InterruptedException {
        script.keyboard.typeString(typing);
    }

    public static boolean interactWithNpc(NPC npc, String options, Script script) throws InterruptedException {
        if (npc != null) {
            if (npc.isOnScreen()) {
//                script.sleep(Utils.boundedInteractionTime(500,1000));
                return npc.interact("Talk-to");
            } else {
                script.getCamera().toEntity(npc);
//                script.sleep(Utils.boundedInteractionTime(500, 1000));
                return interactWithNpc(npc, options, script);
            }
        }
        return false;
    }

    public static boolean continueNextDialogue(Script script) throws InterruptedException {
        if (!script.getWidgets().containingText("Click here to continue").isEmpty()) {
            script.log("Continuing...");
            script.getWidgets().getWidgetContainingText("Click here to continue").interact();
            script.sleep(Utils.randomInteractionTime(true));
            if (!script.getWidgets().containingText("Click here to continue").isEmpty())
                return true;
            else
                return false;
        }
        else
            return false;
    }

    //Interact inventory item with environmental object (Furnace, range, etc)
    public static boolean interactItemWithObject(Item item1, RS2Object object1, String result, Script script)
            throws InterruptedException {

        Timing.waitCondition(() -> script.getCamera().toPosition(object1.getPosition()), 500, 1500);
        while(!script.getInventory().contains(result)) {
            Timing.waitCondition(() -> script.getInventory().getItem(item1.getName()).interact("Use"),
                    Utils.randomInteractionTime(false));
            object1.interact("Use");
            script.sleep(Utils.boundedInteractionTime(5000, 10000));
            try {
                if (script.getWidgets().containingText("Nothing interesting happens.").get(0) != null) {
                    Timing.waitCondition(() -> script.getWidgets().containingText("Click to continue").get(0).
                            interact(), 200, 800);
                }
            } catch (IndexOutOfBoundsException e){
                //nop
            }
        }
        return true;
    }

    public static boolean continueToEnd(Script script) throws InterruptedException {
        int iterator = 0;
        for (int i = 0; i < 2; i++) {
            try {
                while (!script.getWidgets().containingText("Click here to continue").isEmpty()) {
                    script.log("Continuing..." + (++iterator));
                    script.getWidgets().getWidgetContainingText("Click here to continue").interact();
                    script.sleep(Utils.boundedInteractionTime(800, 1600));
                }
            } catch (NullPointerException e) {
            }
            iterator = 0;
            try {
                while (script.getWidgets().get(TUTCONSTS.playerHeadResponse,
                        TUTCONSTS.playerHeadResponseChildPos2) != null ||
                script.getWidgets().get(TUTCONSTS.playerHeadResponse, TUTCONSTS.playerHeadResponse) != null) {
                    script.log("Continuing..." + (++iterator));
                    script.getWidgets().getWidgetContainingText("Click here to continue").interact();
                    script.sleep(Utils.boundedInteractionTime(800, 1600));
                }
            } catch (NullPointerException e) {
            }
            iterator = 0;
            try {
                while (script.getWidgets().get(231, 3) != null) {
                    script.log("Continuing..." + (++iterator));
                    script.getWidgets().getWidgetContainingText("Click here to continue").interact();
                    script.sleep(Utils.boundedInteractionTime(800, 1600));
                }
            } catch (NullPointerException e) {
            }
            script.sleep(50); //Sanity check
        }
        return true;
    }

    public static boolean pendingContinuation(Script script) throws InterruptedException{
        try {
            if (!script.getWidgets().containingText("Click here to continue").isEmpty()) {
                return true;
            }
        } catch (NullPointerException e){
        }
        try {
            if (!script.getWidgets().get(217, 3).isHidden()) {
                return true;
            }
        } catch (NullPointerException e){
        }
        try {
            if (!script.getWidgets().get(231, 3).isHidden()) {
                return true;
            }
            else
                return false;
        } catch (NullPointerException e){
        }
        return false;
    }
//    public static int[] interfaceContentCheck(String content) {
//        RSInterfaceMaster[] allInterfaces = Interfaces.getAll();
//        for (int i = 0; i < allInterfaces.length; i++) {
//            if (allInterfaces[i].getText().contains(content))
//                return new int[]{i};
//            RSInterfaceChild[] mastersChildren = allInterfaces[i].getChildren();
//            try {
//                for (int j = 0; j < mastersChildren.length; j++) {
//                    if (mastersChildren[j].getText().contains(content))
//                        return new int[]{i, j};
//                    RSInterfaceComponent[] childrenComponents = mastersChildren[j].getChildren();
//                    try {
//                        for (int k = 0; k < childrenComponents.length; k++)
//                            if (childrenComponents[k].getText().contains(content))
//                                return new int[]{i, j, k};
//                    } catch (NullPointerException e) {
//                        continue;
//                    }
//                }
//            } catch (NullPointerException e) {
//                continue;
//            }
//        }
//        return new int[]{-1, -1, -1};
//    }
}
