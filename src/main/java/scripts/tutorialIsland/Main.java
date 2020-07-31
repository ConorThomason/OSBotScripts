package scripts.tutorialIsland;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.io.IOException;

@ScriptManifest(author = "Hywok", name = "Tutorial Island 0.4", info = "Capable of start to finish " +
        "Tut Island completion",
        version = 0.4, logo = "")
public final class Main extends Script {
    @Override
    public int onLoop() throws InterruptedException {
        this.sleep(10);
        log("Starting character creation");
        CharacterCreation characterCreation = new CharacterCreation();
        this.sleep(100);
        TutorialIslandLocations location = TutorialIslandLocations.getLocationName(this.myPosition(), this);
        TravelBetweenPhases travel = new TravelBetweenPhases();
                switch (location) {
            case GUIDE_ROOM:
                location = TutorialIslandLocations.getLocationName(this.myPosition(), this);
                log("Starting " + location);
                do {
                    characterCreation.characterCreation(this);
                    GielinorGuide guide = new GielinorGuide();
                    sleep(100);
                    sleep(Utils.randomInteractionTime(false));
                    if (guide.gielinorGuide(this) && travel.travelFromGuide(this)) {
                        this.log("Gielinor Guide phase concluded");
                    } else {
                        this.log("Attempting to recover Gielinor Guide");
                    }
                } while (getConfigs().get(281) >= 0 && getConfigs().get(281) <= 10);
            case SURVIVAL_EXPERT:
                location = TutorialIslandLocations.getLocationName(this.myPosition(), this);
                log("Starting " + location);
                while (getConfigs().get(281) >= 20 && getConfigs().get(281) <= 120) {
                    SurvivalExpert survivalExpert = new SurvivalExpert();
                    sleep(100);
                    if (survivalExpert.survivalExpert(this) && travel.travelFromSurvivalExpert(this)) {
                        this.log("Survival Expert phase concluded");
                    }
                }
            case MASTER_NAVIGATOR:
                location = TutorialIslandLocations.getLocationName(this.myPosition(), this);
                log("Starting " + location);
                while (getConfigs().get(281) >= 120 && getConfigs().get(281) <= 170) {
                    MasterNavigator masterNavigator = new MasterNavigator();
                    sleep(100);
                    if (masterNavigator.masterNavigator(this) && travel.travelFromMasterNavigator(this)) {
                        this.log("Master Navigator phase concluded");
                    }
                }
            case QUEST_GUIDE:
                location = TutorialIslandLocations.getLocationName(this.myPosition(), this);
                log("Starting " + location);
                while (getConfigs().get(281) >= 170 && getConfigs().get(281) <= 250) {
                    QuestGuide questGuide = new QuestGuide();
                    sleep(100);
                    if (questGuide.questGuide(this) && travel.travelFromQuestGuide(this)) {
                        this.log("Quest Guide phase concluded");
                    }
                }
            case MINING_INSTRUCTOR:
                location = TutorialIslandLocations.getLocationName(this.myPosition(), this);
                log("Starting " + location);
                while (getConfigs().get(281) >= 260 && getConfigs().get(281) <= 360) {
                    MiningInstructor miningInstructor = new MiningInstructor();
                    sleep(100);
                    if (miningInstructor.miningInstructor(this) && travel.travelFromMining(this)) {
                        this.log("Mining Expert phase concluded");
                    }
                }
            case COMBAT_INSTRUCTOR:
                location = TutorialIslandLocations.getLocationName(this.myPosition(), this);
                log("Starting " + location);
                while (getConfigs().get(281) >= 360 && getConfigs().get(281) <= 500) {
                    CombatInstructor combatInstructor = new CombatInstructor();
                    sleep(100);
                    if (combatInstructor.combatInstructor(this) && travel.travelFromCombat(this)) {
                        this.log("Combat Instructor phase concluded");
                    }
                }
            case BANK:
                location = TutorialIslandLocations.getLocationName(this.myPosition(), this);
                log("Starting " + location);
                while (getConfigs().get(281) >= 510 && getConfigs().get(281) <= 525) {
                    Bank bank = new Bank();
                    sleep(100);
                    if (bank.bank(this) && travel.travelFromBank(this)) {
                        this.log("Bank phase concluded");
                    }
                }
            case ACCOUNT_GUIDE:
                location = TutorialIslandLocations.getLocationName(this.myPosition(), this);
                log("Starting " + location);
                while (getConfigs().get(281) >= 530 && getConfigs().get(281) <= 540) {
                    AccountGuide accountGuide = new AccountGuide();
                    sleep(100);
                    if (accountGuide.accountGuide(this) && travel.travelFromAccountGuide(this)) {
                        this.log("Account Guide phase concluded");
                    }
                }
            case BROTHER_BRACE:
                location = TutorialIslandLocations.getLocationName(this.myPosition(), this);
                log("Starting " + location);
                while (getConfigs().get(281) >= 550 && getConfigs().get(281) <= 610) {
                    BrotherBrace brotherBrace = new BrotherBrace();
                    sleep(100);
                    if (brotherBrace.brotherBrace(this) && travel.travelFromBrotherBrace(this)) {
                        this.log("Brother Brace phase concluded");
                    }
                }
            case MAGIC_INSTRUCTOR:
                location = TutorialIslandLocations.getLocationName(this.myPosition(), this);
                log("Starting " + location);
                while (getConfigs().get(281) >= 620) {
                    MagicInstructor magicInstructor = new MagicInstructor();
                    sleep(100);
                    if (magicInstructor.magicInstructor(this)) {
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