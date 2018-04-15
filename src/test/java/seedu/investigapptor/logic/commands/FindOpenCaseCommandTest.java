package seedu.investigapptor.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.investigapptor.commons.core.Messages.MESSAGE_CASES_LISTED_OVERVIEW;
import static seedu.investigapptor.testutil.TypicalCrimeCases.BRAVO;
import static seedu.investigapptor.testutil.TypicalCrimeCases.DELTA;
import static seedu.investigapptor.testutil.TypicalCrimeCases.ECHO;
import static seedu.investigapptor.testutil.TypicalCrimeCases.FIVE;
import static seedu.investigapptor.testutil.TypicalCrimeCases.GOLF;
import static seedu.investigapptor.testutil.TypicalCrimeCases.getTypicalInvestigapptor;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.investigapptor.logic.CommandHistory;
import seedu.investigapptor.logic.UndoRedoStack;
import seedu.investigapptor.model.Investigapptor;
import seedu.investigapptor.model.Model;
import seedu.investigapptor.model.ModelManager;
import seedu.investigapptor.model.UserPrefs;
import seedu.investigapptor.model.crimecase.CrimeCase;

//@@author pkaijun
/**
 * Contains integration tests (interaction with the Model) for {@code FindOpenCaseCommand}.
 */
public class FindOpenCaseCommandTest {
    private Model model = new ModelManager(getTypicalInvestigapptor(), new UserPrefs());

    @Test
    public void equals() {
        FindOpenCaseCommand findOpenCaseFirstCommand = new FindOpenCaseCommand();
        FindOpenCaseCommand findOpenCaseSecondCommand = new FindOpenCaseCommand();

        // same object -> returns true
        assertTrue(findOpenCaseFirstCommand.equals(findOpenCaseFirstCommand));

        // same values -> returns true
        FindOpenCaseCommand findFirstCommandCopy = new FindOpenCaseCommand();
        assertTrue(findOpenCaseFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findOpenCaseFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findOpenCaseFirstCommand.equals(null));

        // same object -> returns true
        assertTrue(findOpenCaseFirstCommand.equals(findOpenCaseSecondCommand));
    }

    @Test
    public void execute_command_multipleCrimeCaseFound() {
        String expectedMessage = String.format(MESSAGE_CASES_LISTED_OVERVIEW, 5);
        FindOpenCaseCommand command = prepareCommand();
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BRAVO, DELTA, ECHO, GOLF, FIVE));
    }

    /**
     * Prepare the FindOpenCaseCommand {@code FindOpenCaseCommand}.
     */
    private FindOpenCaseCommand prepareCommand() {
        FindOpenCaseCommand command = new FindOpenCaseCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<CrimeCase>} is equal to {@code expectedList}<br>
     *     - the {@code Investigapptor} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindOpenCaseCommand command, String expectedMessage,
                                      List<CrimeCase> expectedList) {
        Investigapptor expectedInvestigapptor = new Investigapptor(model.getInvestigapptor());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredCrimeCaseList());
        assertEquals(expectedInvestigapptor, model.getInvestigapptor());
    }
}
