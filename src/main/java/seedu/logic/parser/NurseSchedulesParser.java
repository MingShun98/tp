package seedu.logic.parser;

import seedu.exceptions.nurseschedules.WrongInputsException;
import seedu.logic.command.Command;
import seedu.logic.command.nurseschedule.*;
import seedu.ui.NurseScheduleUI;
import static seedu.ui.UI.smartCommandRecognition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class NurseSchedulesParser {

    static final String[] COMMANDS = {"add", "delete", "list", "return", "help"};

    /**
     * Gets user input.
     *
     * @return User input
     */
    public String getUserInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    /**
     * Returns the command of user.
     *
     * @param text User input
     * @return First word of user input
     */
    public String getFirstWord(String text) {
        int index = text.indexOf('/');

        if (index > -1) {

            return text.substring(0, index).trim();

        } else {

            return text;
        }
    }

    public String[] getDetails(String text) throws WrongInputsException {
        String[] details = new String[3];

        String[] parts = text.split("/", 0);
        String command = smartCommandRecognition(COMMANDS, getFirstWord(text));

        assert parts.length > 0;

        if (parts.length <= 1) {
            throw new WrongInputsException();
        } else if (command.equals("add")) {
            if (isValidDate(parts[3])) {
                details[0] = parts[1];
                details[1] = parts[2];
                details[2] = parts[3];
            }
        } else if (command.equals("delete")) {
            if (isValidDate(parts[2])) {
                details[0] = parts[1];
                details[1] = parts[2];
            }
        } else if (command.equals("list")) {
            details[0] = parts[1];
        }
        return details;
    }

    public static String formatDate(String datetime) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("ddMMyyyy");
        Date date = parser.parse(datetime);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        return formatter.format(date);
    }

    public static boolean isValidDate(String datetime) {
        /* Check if date is 'null' */
        if (!datetime.trim().equals("")) {
            /*
             * Set preferred date format,
             * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
            SimpleDateFormat sdfrmt = new SimpleDateFormat("ddMMyyyy");
            sdfrmt.setLenient(false);
            /* Create Date object
             * parse the string into date
             */
            try {
                Date javaDate = sdfrmt.parse(datetime);
                //System.out.println(datetime + " is valid date format");
            }
            /* Date format is invalid */
            catch (ParseException e) {
                System.out.println(datetime + " is Invalid Date format");
                return false;
            }
        }
        /* Return true if date format is valid */
        return true;
    }

    public Command nurseParse(String line, NurseScheduleUI ui) {
        assert line != null : "user input should not be null";
        assert !(line.isEmpty()) : "user input should not be empty";

        NurseSchedulesParser parser = new NurseSchedulesParser();
        String command = parser.getFirstWord(line);
        Command c = null;

        switch (smartCommandRecognition(COMMANDS, command)) {
        case "add":
            try {
                String[] details = parser.getDetails(line);
                c = new NurseScheduleAdd(details);
            } catch (ArrayIndexOutOfBoundsException | WrongInputsException e) {
                ui.formatHelpMessage();
                ui.addHelpMessage();
            }
            break;
        case "list":
            try {
                String[] details = parser.getDetails(line);
                c = new NurseScheduleList(details);
            } catch (ArrayIndexOutOfBoundsException | WrongInputsException e) {
                ui.formatHelpMessage();
                ui.listHelpMessage();
            }
            break;
        case "delete":
            try {
                String[] details = parser.getDetails(line);
                c = new NurseScheduleDelete(details);
            } catch(ArrayIndexOutOfBoundsException | WrongInputsException e) {
                ui.formatHelpMessage();
                ui.deleteHelpMessage();
            }
            break;
        case "help":
            c = new NurseScheduleHelp();
            break;
        case "return":
            c = new NurseScheduleReturn();
            break;
        default:
            ui.invalidInputsMessage();
            break;
        }
        return c;
    }

//    public boolean commandHandler(List<NurseSchedule> nurseSchedules, String command, String line) {
//        NurseScheduleActions actions = new NurseScheduleActions();
//        NurseScheduleStorage storage = new NurseScheduleStorage();
//        NurseSchedulesParser parser = new NurseSchedulesParser();
//
//        switch (command) {
//        case "add":
//            try {
//                actions.addSchedule(nurseSchedules, parser.getDetails(line));
//                storage.writeToFile(nurseSchedules);
//            } catch (WrongInputsException | ParseException e) {
//                System.out.println(e.getMessage());
//                NurseScheduleUI.addHelpMessage();
//            }
//            break;
//        case "list":
//            try {
//                actions.listSchedules(nurseSchedules, parser.getDetails(line));
//            } catch (WrongInputsException e) {
//                NurseScheduleUI.invalidInputsMessage();
//                NurseScheduleUI.listHelpMessage();
//            } catch (EmptyListException e) {
//                System.out.println(e.getMessage());
//            } catch (NurseIdNotFound e) {
//                System.out.println(e.getMessage());
//            }
//            break;
//        case "delete":
//            try {
//                actions.deleteSchedule(nurseSchedules, parser.getDetails(line));
//                storage.writeToFile(nurseSchedules);
//            } catch (WrongInputsException e) {
//                System.out.println(e.getMessage());
//                NurseScheduleUI.deleteHelpMessage();
//            } catch (NurseIdNotFound e) {
//                System.out.println(e.getMessage());
//            }
//            break;
//        case "help":
//            NurseScheduleUI.printNurseScheduleHelpList();
//            break;
//        case "return":
//            storage.writeToFile(nurseSchedules);
//            NurseScheduleUI.returningToStartMenuMessage();
//            return false;
//        default:
//            NurseScheduleUI.invalidCommandMessage();
//            break;
//        }
//        return true;
//    }
}