package it.polimi.se2019.ui;

/**
 * @author Galli
 */
import it.polimi.se2019.model.deck.*;
import it.polimi.se2019.model.handler.Identificator;
import it.polimi.se2019.model.player.ColorRYB;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.clientView.ClientView;
import it.polimi.se2019.view.clientView.NotCharacterException;
import it.polimi.se2019.view.remoteView.EnemyView;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

/**
 * Parse of the command on the CLI
 * Convert the string of the users in messages for the controller
 * If a command does not exist an error message will be printed
 */
class ParserCLI {
    private final CLI cli;
    private ClientView view;
    boolean open = true;
    private Scanner in = new Scanner(System.in);
    private Player player;
    private List<WeaponCard> weaponDeck = new WeaponDeck().getUnusedCard();

    private static final String SPECIFY_WEAPON = "Retry specifying the name of the weapon.";
    private static final String SPECIFY_PLAYER= "Retry specifying the name of the player.";

    ParserCLI(ClientView view, CLI cli) {
        this.cli = cli;
        this.view = view;
        new Thread(() -> {
            while(open) {
                if(in.hasNext()) {
                    String command = in.nextLine();
                    split(command);
                }
            }
            in.close();
        }).start();
    }

    private void split(String line) {
        String[] command = line.toUpperCase().split(" ");
        mainParse(command);
    }

    private void mainParse(String[] command) {
        player = view.getPlayerCopy();
        if(cli.endGame) {
            if(command[0].equalsIgnoreCase("EXIT")) parseExit();
            else cli.println("The game is ended, you can't do that.\n" +
                    "For exit digit 'EXIT'");
        } else {
            try {
                String[] command2 = Arrays.copyOfRange(command, 1, command.length);
                switch (command[0]) {
                    case "ACTION":
                        parseAction(command2);
                        break;
                    case "POWERUP":
                        parsePowerup(command2, true);
                        break;
                    case "ENEMY":
                    case "PLAYER":
                        parsePlayer(command2);
                        break;
                    case "CELL":
                        parseCell(command2);
                        break;
                    case "DISCARD":
                        parseDiscard(command2);
                        break;
                    case "WEAPON":
                        parseWeapon(command2, true);
                        break;
                    case "FIRE!":
                    case "FIRE":
                        parseFire(command2);
                        break;
                    case "INFO":
                        parseInfo(command2);
                        break;
                    case "UPDATE":
                        parseUpdate(command2);
                        break;
                    case "END":
                    case "PASS":
                        parsePass(command2);
                        break;
                    case "RELOAD":
                        parseReload(command2);
                        break;
                    case "SKIP":
                        parseSkip(command2);
                        break;
                    case "FIREMODE":
                        parseFiremode(command2);
                        break;
                    case "QUIT":
                    case "EXIT":
                        parseExit();
                        break;
                    case "CHARACTER":
                        parseCharacter(command2);
                        break;
                    case "-H":
                    case "-HELP":
                        printGuide();
                        break;
                    default:
                        cli.println("Command '" + command[0] + "' dose not exist.\n" +
                                "Try whit -Help");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                cli.println("Command '" + Arrays.toString(command) + "' dose not exist.");
            }
        }
    }

    private void parseAction(String[] command) {
        try {
            switch (command[0]) {
                case "MOVE":
                    view.createActionMessage(Identificator.MOVE);
                    break;
                case "GRAB":
                    view.createActionMessage(Identificator.GRAB);
                    break;
                case "SHOOT":
                    view.createActionMessage(Identificator.SHOOT);
                    cli.println("You have this weapon reloaded: ");
                    cli.println("\t" + getWeapon());
                    break;
                default:
                    cli.println("Action '" + command[0] + "' dose not exist");
                    return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            cli.println("Missing info\n" +
                    "Retry specifying the type of action.");
            return;
        }
        if(command.length>1) mainParse(Arrays.copyOfRange(command, 1, command.length));
    }

    private void parsePowerup(String[] command, boolean use) {
        try {
            String[] command2 = Arrays.copyOfRange(command, 1, command.length);
            switch (command[0]) {
                case "TELEPORTER":
                    parseTeleporter(command2, use);
                    break;
                case "NEWTON":
                    parseNewton(command2, use);
                    break;
                case "TAGBACK":
                    if (command2[0].equalsIgnoreCase("GRENADE")) //If the next word is "grenade" ignore it
                        command2 = Arrays.copyOfRange(command2, 1, command.length);
                    parseTagback(command2, use);
                    break;
                case "TARGETING":
                    if (command2[0].equalsIgnoreCase("SCOPE")) //If the next word is "scope" ignore it
                        command2 = Arrays.copyOfRange(command2, 1, command.length);
                    parseTargetingScope(command2, use);
                    break;
                default:
                    cli.println("Powerup '" + command[0] + "' dose not exist");
                    return;
            }
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            cli.println("Missing color of powerup to use.");
        }
    }

    private void parseTeleporter(String[] command, boolean use) {
        String dontHaveIt = "You don't have this Teleporter card!";
        switch (command[0]) {
            case "BLUE":
                for(PowerupCard card : player.getPowerupCardList()) {
                    if(card.getIDtype()==Identificator.TELEPORTER_BLUE){
                        if(use) view.createTeleporterMessage((TeleporterCard) card);
                        else view.createDiscardPowerupMessage(card);
                        return;
                    }
                }
                cli.println(dontHaveIt);
                break;
            case "YELLOW":
                for(PowerupCard card : player.getPowerupCardList()) {
                    if(card.getIDtype()==Identificator.TELEPORTER_YELLOW){
                        if(use) view.createTeleporterMessage((TeleporterCard) card);
                        else view.createDiscardPowerupMessage(card);
                        return;
                    }
                }
                cli.println(dontHaveIt);
                break;
            case "RED":
                for(PowerupCard card : player.getPowerupCardList()) {
                    if(card.getIDtype()==Identificator.TELEPORTER_RED){
                        if(use) view.createTeleporterMessage((TeleporterCard) card);
                        else view.createDiscardPowerupMessage(card);
                        return;
                    }
                }
                cli.println(dontHaveIt);
                break;
            default:
                cli.println(command[0] + " is not a color.\n" +
                        "Please, retry with blue, red or yellow.");
                return;
        }
        if(command.length>1) mainParse(Arrays.copyOfRange(command, 1, command.length));
    }

    private void parseNewton(String[] command, boolean use) {
        String dontHaveIt = "You don't have this Newton card!";
        switch (command[0]) {
            case "BLUE":
                for(PowerupCard card : player.getPowerupCardList()) {
                    if(card.getIDtype()==Identificator.NEWTON_BLUE){
                        if(use) view.createNewtonMessage((NewtonCard) card);
                        else view.createDiscardPowerupMessage(card);
                        return;
                    }
                }
                cli.println(dontHaveIt);
                break;
            case "YELLOW":
                for(PowerupCard card : player.getPowerupCardList()) {
                    if(card.getIDtype()==Identificator.NEWTON_YELLOW){
                        if(use) view.createNewtonMessage((NewtonCard) card);
                        else view.createDiscardPowerupMessage(card);
                        return;
                    }
                }
                cli.println(dontHaveIt);
                break;
            case "RED":
                for(PowerupCard card : player.getPowerupCardList()) {
                    if(card.getIDtype()==Identificator.NEWTON_RED){
                        if(use) view.createNewtonMessage((NewtonCard) card);
                        else view.createDiscardPowerupMessage(card);
                        return;
                    }
                }
                cli.println(dontHaveIt);
                break;
            default:
                cli.println(command[0] + " is not a color.\n" +
                        "Please, retry with blue, red or yellow.");
                return;
        }
        if(command.length>1) mainParse(Arrays.copyOfRange(command, 1, command.length));
    }

    private void parseTagback(String[] command, boolean use) {
        String dontHaveIt = "You don't have this Tagback Grenade card!";
        switch (command[0]) {
            case "BLUE":
                for(PowerupCard card : player.getPowerupCardList()) {
                    if(card.getIDtype()==Identificator.TAGBACK_GRENADE_BLUE){
                        if(use) view.createTagbackGranadeMessage((TagbackGrenadeCard) card);
                        else view.createDiscardPowerupMessage(card);
                        return;
                    }
                }
                cli.println(dontHaveIt);
                break;
            case "YELLOW":
                for(PowerupCard card : player.getPowerupCardList()) {
                    if(card.getIDtype()==Identificator.TAGBACK_GRENADE_YELLOW){
                        if(use) view.createTagbackGranadeMessage((TagbackGrenadeCard) card);
                        else view.createDiscardPowerupMessage(card);
                        return;
                    }
                }
                cli.println(dontHaveIt);
                break;
            case "RED":
                for(PowerupCard card : player.getPowerupCardList()) {
                    if(card.getIDtype()==Identificator.TAGBACK_GRENADE_RED){
                        if(use) view.createTagbackGranadeMessage((TagbackGrenadeCard) card);
                        else view.createDiscardPowerupMessage(card);
                        return;
                    }
                }
                cli.println(dontHaveIt);
                break;
            default:
                cli.println(command[0] + " is not a color.\n" +
                        "Please, retry with blue, red or yellow.");
                return;
        }
        if(command.length>1) mainParse(Arrays.copyOfRange(command, 1, command.length));
    }

    private void parseTargetingScope(String[] command, boolean use) {
        String dontHaveIt = "You don't have this Targeting Scope card!";
        try {
            ColorRYB colorToPay = ColorRYB.colorByString(command[0]);
            switch (command[0]) {
                case "BLUE":
                    for(PowerupCard card : player.getPowerupCardList()) {
                        if(card.getIDtype()==Identificator.TARGETING_SCOPE_BLUE){
                            if(use) view.createTargetingScopeMessage((TargetingScopeCard) card, colorToPay);
                            else view.createDiscardPowerupMessage(card);
                            return;
                        }
                    }
                    cli.println(dontHaveIt);
                    break;
                case "YELLOW":
                    for(PowerupCard card : player.getPowerupCardList()) {
                        if(card.getIDtype()==Identificator.TARGETING_SCOPE_YELLOW){
                            if(use) view.createTargetingScopeMessage((TargetingScopeCard) card, colorToPay);
                            else view.createDiscardPowerupMessage(card);
                            return;
                        }
                    }
                    cli.println(dontHaveIt);
                    break;
                case "RED":
                    for(PowerupCard card : player.getPowerupCardList()) {
                        if(card.getIDtype()==Identificator.TARGETING_SCOPE_RED){
                            if(use) view.createTargetingScopeMessage((TargetingScopeCard) card, colorToPay);
                            else view.createDiscardPowerupMessage(card);
                            return;
                        }
                    }
                    cli.println(dontHaveIt);
                    break;
                default:
                    cli.println(command[0] + " is not a color.\n" +
                            "Please, retry with blue, red or yellow.");
                    cli.println("You don't have this Targeting Scope card!");
            }
        } catch (IllegalArgumentException e) {
            cli.println(command[0] + " is not a color.\n" +
                    "Please, retry with blue, red or yellow.");
        } catch (ArrayIndexOutOfBoundsException e) {
            cli.println("Please, retry specifying the color of the ammo you wanna use.");
        }
    }

    private void parsePlayer(String[] command) {
        try {
            EnemyView enemy = findEnemy(command[0]);
            view.createPlayerMessage(enemy.getID());
        } catch (IllegalArgumentException e) {
            cli.println("There's no enemy called " + command[0] + ".");
        } catch (ArrayIndexOutOfBoundsException e) {
            cli.println("Missing info\n" +
                    SPECIFY_PLAYER);
        }
    }

    private void parseCell(String[] command) {
        String[] coordinate = command[0].split(",");
        try {
            int x = Integer.parseInt(coordinate[0]);
            int y = Integer.parseInt(coordinate[1]);
            view.createCellMessage(x, y);
        } catch (NumberFormatException e) {
            cli.println(command[0] + " is not a valid coordinate.\n" +
                    "Please, use X,Y format.");
        } catch (ArrayIndexOutOfBoundsException e) {
            cli.println("Missing info\n" +
                    "Retry specifying the coordinates of the cell.");
        }
    }

    private void parseDiscard(String[] command) {
        try {
            String[] command2 = Arrays.copyOfRange(command, 1, command.length);
            switch (command[0]) {
                case "POWERUP":
                    parsePowerup(command2, false);
                    break;
                case "WEAPON":
                    parseWeapon(command2, false);
                    break;
                default:
                    cli.println("You can't discard " + command[0] +
                            "\n. Please, retry with 'powerup' or 'weapon'.");
            }
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            cli.println("What do you want discard?\n" +
                    "Retry specifying which card you wanna discard.");
        }
    }

    private void parseWeapon(String[] command, Boolean use) {
        try {
            WeaponCard card = findWeapon(command[0]);
            if(use) {
                view.createWeaponMessage(card);
                 synchronized (cli)  {
                    cli.println(card.toString());
                }
            }
            else view.createDiscardWeaponMessage(card);
        } catch (IllegalArgumentException e) {
            cli.println("There is no weapon called " + command[0] +".");
        } catch (ArrayIndexOutOfBoundsException e) {
            cli.println("Missing info\n" +
                    SPECIFY_WEAPON);
        }
    }

    private void parseFire(String[] command) {
        view.createFireMessage();
    }

    private void parseInfo(String[] command) {
        try {
            String[] command2 = Arrays.copyOfRange(command, 1, command.length);
            switch (command[0]) {
                case "WEAPON":
                    parseInfoWeapon(command2);
                    break;
                case "ENEMY":
                    parseInfoEnemy(command2);
                    break;
                default:
                    cli.println("No info available for " + command[0] + ".\n" +
                            "Please, retry specifying 'weapon' or 'enemy'.");
            }
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            cli.println("What about do you want more info?\n" +
                    "Retry specifying about what you want more info.");
        }
    }

    private void parseInfoWeapon(String[] command) {
        try {
            WeaponCard weapon = findWeapon(command[0]);
            cli.println("\n");
            cli.println(weapon.toString());
            cli.println("\n");
        } catch (IllegalArgumentException e) {
            cli.println("There is no weapon called " + command[0] + ".");
        } catch (ArrayIndexOutOfBoundsException e) {
            cli.println("Missing info\n" + SPECIFY_WEAPON);
        }
    }

    private void parseInfoEnemy(String[] command) {
        try {
            EnemyView enemy = findEnemy(command[0]);
            cli.println("\n");
            cli.println(enemy.toString());
            cli.println("\n");
        } catch (IllegalArgumentException e) {
            cli.println("There is no enemy called " + command[0] + ".");
        } catch (ArrayIndexOutOfBoundsException e) {
            cli.println("Missing info\n" +
                    SPECIFY_PLAYER);
        }
    }

    private void parseUpdate(String[] command) {
        try {
            String[] command2 = Arrays.copyOfRange(command, 1, command.length);
            switch (command[0]) {
                case "MAP":
                    cli.clearScreen();
                    cli.println("\n");
                    cli.printMap();
                    cli.println("\n");
                    break;
                case "ENEMY":
                    cli.println("\n");
                    parseUpdateEnemy(command2);
                    break;
                case "ME":
                    cli.println(player.toString());
                    break;
                default:
                    cli.println("Can't show update of "+ command[0] + ".");
            }
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            cli.println("What do you want update?\n" +
                    "Retry specifying what you want update.");
        }
    }

    private void parseUpdateEnemy(String[] command) {
        try {
            EnemyView enemy = findEnemy(command[0]);
            cli.println(enemy.toStringShort());
            cli.println("\n");
        } catch (IllegalArgumentException e) {
            cli.println("There in no enemy with that name.");
        } catch (ArrayIndexOutOfBoundsException e) {
            cli.println("Missing info\n" +
                    SPECIFY_PLAYER);
        }
    }

    private void parsePass(String[] command) {
        view.createPassTurnMessage();
        cli.setYourTurn(false);
    }

    private void parseReload(String[] command) {
        try {
            view.createReloadMessage(findWeapon(command[0]));
        } catch (IllegalArgumentException e) {
            cli.println("There is no weapon called " + command[0] + ".");
        } catch (ArrayIndexOutOfBoundsException e) {
            cli.println("Missing info\n" +
                    SPECIFY_WEAPON);
        }
    }

    private void parseSkip(String[] command) {
        view.createNopeMessage();
    }

    private void parseFiremode(String[] command) {
        try {
            switch (command[0]) {
                case "BASE":
                    view.createFireModeMessage(1);
                    break;
                case "ALT":
                case "ALTERNATIVE":
                    view.createFireModeMessage(2);
                    break;
                case "OPTIONAL":
                    view.createOptionalMessage(Integer.parseInt(command[1]));
                    break;
                default:
                    cli.println("Command 'FIREMODE " + command[0] + "' dose not exist.");
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            cli.println("Missing info\n" +
                    "Retry specifying which optional do you want (even if this weapon has only one optional).");
        }
    }

    private void parseExit() {
        cli.println("Are you sure do you wanna exit? \t:'(");
        String decision = in.nextLine();
        switch (decision.toUpperCase()) {
            case "Y":
            case "YES":
                open=false;
                cli.println("You will be disconnected soon.");
                view.shutdownServer();
                cli.disconnect(view.getMatchId());
                break;
            case "N":
            case "NO":
                cli.println("Enjoy the game! \t(@•̀␣•́)↵✧");
                break;
            default:
                    cli.println(decision + " is not a command.\n" +
                            "Please, retry with yes or no.");
        }
    }

    private void parseCharacter(String[] command) {
        if(view.getPlayerCopy().getCharacter()!=null)
            cli.println("You have already chosen your character.");
        try {
            view.createCharacterMessage(view.getPossibleCharacter().get(Integer.parseInt(command[0])-1).getId());
        } catch (NotCharacterException e) {
            cli.println("You can't choose this character.");
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            cli.println("Missing info.\n" +
                    "Retry specifying which character do you want (insert the relative number).");
        } catch (NullPointerException e) {
            cli.println("Wait your turn, you can't choose your character yet.");
        }
    }


    private void printGuide() {
        cli.clearScreen();
        cli.printLine();
        cli.println("Adrenaline by Cranio Creations\n");
        cli.printf("ACTION [MOVE | GRAB | SHOOT]", "Create the action specified.");
        cli.printf("POWERUP [NEWTON | TARGETING SCOPE | TELEPORTER | TAGBACK GRENADE] [RED | BLUE | YELLOW] *",
                "Use one of yours powerups of the type and color specified.");
        cli.printf("PLAYER [Enemy's name]", "Select a player, use this command for shooting or moving someone.");
        cli.printf("CELL [X],[Y]", "Select the cell with X,Y as coordinate." +
                "Use this for moving or grabbing somewhere.");
        cli.printf("WEAPON [Weapon's name]", "Select a weapon, use this for shooting or grabbing.");
        cli.printf("FIRE", "Use this when you have selected all your targets.");
        cli.printf("FIREMODE [BASE | ALTERNATIVE | OPTIONAL] [[Optional's number]]",
                "Select a firemode of the weapon just selected.");
        cli.printf("DISCARD [POWERUP | WEAPON] [Powerup's name & color | Weapon's name]",
                "Discard a card from your hand.");
        cli.printf("RELOAD [Weapon's name]", "Reload the weapon specified.");
        cli.printf("SKIP", "Use this if you don't have any target to shoot.");
        cli.printf("PASS TURN", "End your turn.");
        cli.printf("INFO [ENEMY | WEAPON] [Enemy's name | Weapon's name]",
                "Show all the information about an enemy/weapon.");
        cli.printf("UPDATE [MAP | ME | ENEMY] [[Enemy's name]]", "Show all the latest changes.");
        cli.printf("CHARACTER [Relative number]", "Select a character from the list." +
                " If you don't have the list you should'nt use it.");
        cli.printf("EXIT", "Quit the game.");
        cli.printLine();
        cli.println("* For TARGETING SCOPE indicate also the color of the ammo you wanna use.");
        cli.printLine();
    }

    private WeaponCard findWeapon(String name) {
        for(WeaponCard weaponCard : weaponDeck) {
            if(weaponCard.getName().toUpperCase().contains(name)) return weaponCard;
        }
        throw new IllegalArgumentException("No weapon called " + name);
    }

    private EnemyView findEnemy(String name) {
        for (EnemyView enemyView : cli.enemyViews) {
            if (enemyView.getNickname().equalsIgnoreCase(name)) return enemyView;
        }
        throw new IllegalArgumentException("No enemy called " + name);
    }

    private String getWeapon() {
        String s = "";
        for(WeaponCard weapon : view.getPlayerCopy().getWeaponCardList()) {
            if(weapon.isReloaded()) s += weapon.getName() + " ";
        }
        if(s.equalsIgnoreCase("")) s = "- No weapon loaded -";
        return s;
    }

}