package seedu.duke.storage;

import seedu.nurseschedules.NurseSchedule;
import seedu.staff.Staff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class NurseScheduleStorage {

    private static final String FILE_PATH = "NurseSchedules.txt";

    /**
     * Creates new file.
     */
    private static void createFile() {
        try {
            File file = new File(FILE_PATH);
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFile(List<NurseSchedule> nurseSchedules) {
        try {
            FileInputStream file = new FileInputStream(FILE_PATH);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String[] details = sc.nextLine().split("\\|", 0);
                nurseSchedules.add(new NurseSchedule(details[0], details[1], details[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(List<NurseSchedule> nurseSchedules) {
        try {
            File file = new File(FILE_PATH);
            FileWriter writer = new FileWriter(file);
            for (NurseSchedule n : nurseSchedules) {
                writer.write(n.toSave());
                writer.write("\r\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(List<NurseSchedule> nurseSchedules) {
        createFile();
        readFile(nurseSchedules);
    }
}