package scripts.tutorialIsland;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.io.IOException;

@ScriptManifest(author = "Hywok", name = "Tutorial Island 0.3", info = "Capable of start to finish " +
        "Tut Island completion",
        version = 0.3, logo = "")
public final class Main extends Script {
    @Override
    public int onLoop() throws InterruptedException {
        this.sleep(10);
        TutorialIslandLocations state = TutorialIslandLocations.GUIDE_ROOM;
        log("Starting character creation");
        CharacterCreation characterCreation = new CharacterCreation();
        characterCreation.characterCreation(this);
        this.sleep(100);
        TutorialIslandLocations location = TutorialIslandLocations.getLocationName(this.myPosition());
        TravelBetweenPhases travel = new TravelBetweenPhases();
        switch (location) {
            case GUIDE_ROOM:
                while (getConfigs().get(281) >= 2 || getConfigs().get(281) <= 20) {
                    GielinorGuide guide = new GielinorGuide();
                    sleep(100);
                    sleep(Utils.randomInteractionTime(false));
                    if (guide.gielinorGuide(this) && travel.travelFromGuide(this)) {
                        state = TutorialIslandLocations.SURVIVAL_EXPERT;
                        this.log("Gielinor Guide phase concluded");
                    } else {
                        this.log("Attempting to recover Gielinor Guide");
                    }
                }
            case SURVIVAL_EXPERT:
                while (getConfigs().get(281) >= 30 || getConfigs().get(281) <= 120) {
                    SurvivalExpert survivalExpert = new SurvivalExpert();
                    sleep(100);
                    if (survivalExpert.survivalExpert(this) && travel.travelFromSurvivalExpert(this)) {
                        state = TutorialIslandLocations.MASTER_NAVIGATOR;
                        this.log("Survival Expert phase concluded");
                    }
                }
            case MASTER_NAVIGATOR:
                while (getConfigs().get(281) >= 120 || getConfigs().get(281) <= 170) {
                    MasterNavigator masterNavigator = new MasterNavigator();
                    sleep(100);
                    if (masterNavigator.masterNavigator(this) && travel.travelFromMasterNavigator(this)) {
                        state = TutorialIslandLocations.QUEST_GUIDE;
                        this.log("Master Navigator phase concluded");
                    }
                }
            case QUEST_GUIDE:
                while (getConfigs().get(281) >= 170 || getConfigs().get(281) <= 260) {
                    QuestGuide questGuide = new QuestGuide();
                    sleep(100);
                    if (questGuide.questGuide(this) && travel.travelFromQuestGuide(this)) {
                        state = TutorialIslandLocations.MINING_INSTRUCTOR;
                        this.log("Quest Guide phase concluded");
                    }
                }
            case MINING_INSTRUCTOR:
                while (getConfigs().get(281) >= 260 || getConfigs().get(281) <= 360) {
                    MiningInstructor miningInstructor = new MiningInstructor();
                    sleep(100);
                    if (miningInstructor.miningInstructor(this) && travel.travelFromMining(this)) {
                        state = TutorialIslandLocations.COMBAT_INSTRUCTOR;
                        this.log("Mining Expert phase concluded");
                    }
                }
            case COMBAT_INSTRUCTOR:
                while (getConfigs().get(281) >= 360 || getConfigs().get(281) <= 510) {
                    CombatInstructor combatInstructor = new CombatInstructor();
                    sleep(100);
                    if (combatInstructor.combatInstructor(this) && travel.travelFromCombat(this)) {
                        state = TutorialIslandLocations.ACCOUNT_GUIDE;
                        this.log("Combat Instructor phase concluded");
                    }
                }
            case BANK:
                while (getConfigs().get(281) >= 510 || getConfigs().get(281) <= 530) {
                    Bank bank = new Bank();
                    sleep(100);
                    if (bank.bank(this) && travel.travelFromBank(this)) {
                        state = TutorialIslandLocations.ACCOUNT_GUIDE;
                        this.log("Bank phase concluded");
                    }
                }
            case ACCOUNT_GUIDE:
                while (getConfigs().get(281) >= 530 || getConfigs().get(281) <= 550) {
                    AccountGuide accountGuide = new AccountGuide();
                    sleep(100);
                    if (accountGuide.accountGuide(this) && travel.travelFromAccountGuide(this)) {
                        state = TutorialIslandLocations.BROTHER_BRACE;
                        this.log("Account Guide phase concluded");
                    }
                }
            case BROTHER_BRACE:
                while (getConfigs().get(281) >= 550 || getConfigs().get(281) <= 620) {
                    BrotherBrace brotherBrace = new BrotherBrace();
                    sleep(100);
                    if (brotherBrace.brotherBrace(this) && travel.travelFromBrotherBrace(this)) {
                        state = TutorialIslandLocations.MAGIC_INSTRUCTOR;
                        this.log("Brother Brace phase concluded");
                    }
                }
            case MAGIC_INSTRUCTOR:
                while (getConfigs().get(281) >= 620) {
                    MagicInstructor magicInstructor = new MagicInstructor();
                    sleep(100);
                    if (magicInstructor.magicInstructor(this)) {
                        state = TutorialIslandLocations.MAGIC_INSTRUCTOR;
                        this.log("Magic Instructor phase concluded");
                        break;
                    }
                }
        }
        sleep(5000);
        getLogoutTab().logOut();
        sleep(2500);
        stop();
        return 0;
    }
}