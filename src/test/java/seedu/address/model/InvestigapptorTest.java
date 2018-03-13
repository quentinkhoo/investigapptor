package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalInvestigapptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.InvestigapptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class InvestigapptorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private final Investigapptor investigapptorWithAliceAndBob =
            new InvestigapptorBuilder().withPerson(ALICE).withPerson(BOB).build();


    private final Investigapptor investigapptor = new Investigapptor();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), investigapptor.getPersonList());
        assertEquals(Collections.emptyList(), investigapptor.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        investigapptor.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyInvestigapptor_replacesData() {
        Investigapptor newData = getTypicalInvestigapptor();
        investigapptor.resetData(newData);
        assertEquals(newData, investigapptor);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        InvestigapptorStub newData = new InvestigapptorStub(newPersons, newTags);

        thrown.expect(AssertionError.class);
        investigapptor.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        investigapptor.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        investigapptor.getTagList().remove(0);
    }
    @Test
    public void deleteTag_usedByMultiplePersons_tagDeleted() throws Exception {
        Person amyWithFriendTag = new PersonBuilder(AMY).withTags("Friend").build();
        Person bobWithFriendTag = new PersonBuilder(BOB).withTags("Friend").build();

        Investigapptor investigapptor = new InvestigapptorBuilder().withPerson(amyWithFriendTag)
                .withPerson(bobWithFriendTag).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(investigapptor, userPrefs);
        modelManager.deleteTag(new Tag("Friend"));

        Person amyNoFriendTag = new PersonBuilder(AMY).withTags().build();
        Person bobNoFriendTag = new PersonBuilder(BOB).withTags().build();

        Investigapptor expectedInvestigapptor = new InvestigapptorBuilder().withPerson(amyNoFriendTag)
                .withPerson(bobNoFriendTag).build();

        assertEquals(new ModelManager(expectedInvestigapptor, userPrefs), modelManager);
    }
    /**
     * A stub ReadOnlyInvestigapptor whose persons and tags lists can violate interface constraints.
     */
    private static class InvestigapptorStub implements ReadOnlyInvestigapptor {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        InvestigapptorStub(Collection<Person> persons, Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
