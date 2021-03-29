package seedu.logic.command.staff;

import seedu.logic.command.Command;
import seedu.logic.command.StaffAggregation;
import seedu.storage.StaffStorage;
import seedu.ui.StaffUI;

import java.io.IOException;

public class StaffReturn extends Command {

    public void execute(StaffAggregation staffAggregation, StaffUI staffUI, StaffStorage staffStorage) throws IOException {
       staffAggregation.resetList();
    }


    @Override
    public boolean isExit() {
        return true;
    }
}
