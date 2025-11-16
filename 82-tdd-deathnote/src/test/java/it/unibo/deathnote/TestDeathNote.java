package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.DeathNoteImpl;

class TestDeathNote {
    private static final int TIME = 6100;
    private static final int NUMBER_OF_RULE = 14;
    private static final String PERSON = "Nicolas Colinucci";
    private static final String PERSON2 = "Mario Rossi";

    private DeathNote note;

    @BeforeEach
    void setUp() {
        this.note = new DeathNoteImpl();
    }

    @Test
    void testOfGetRule() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                note.getRule(0);
            }
        });
    }

    @Test
    void testOfGetRule2() {
        for (int i = 1; i < NUMBER_OF_RULE; i++) {
            assertTrue(note.getRule(i) != null, "the rule " + i + "is null");
        }
    }

    @Test
    void testWriteName() {
        if (!note.isNameWritten(PERSON)) {
            note.writeName(PERSON);
            assertTrue(note.isNameWritten(PERSON));
            assertFalse(note.isNameWritten("Nicolas Bianchi"));
            assertFalse(note.isNameWritten(""));
        }
    }

    @Test
    void testWriteCauseOfDeath() throws InterruptedException {
        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                note.writeDeathCause("heart attack");
            }
        });
        note.writeName(PERSON);
        assertEquals(note.getDeathCause(PERSON), "heart attack");
        note.writeName(PERSON2);
        assertTrue(note.writeDeathCause("karting accident"));
        Thread.sleep(100);
        assertFalse(note.writeDeathCause("hit by a track"));
        assertEquals(note.getDeathCause(PERSON2), "karting accident");
    }

    @Test
    void testWriteDetailsOfDeath() throws InterruptedException {
        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                note.writeDetails("ran");
            }
        });
        note.writeName(PERSON);
        assertEquals("", note.getDeathDetails(PERSON));
        assertTrue(note.writeDetails("ran for too long"));
        assertEquals(note.getDeathDetails(PERSON), "ran for too long");
        note.writeName(PERSON2);
        Thread.sleep(TIME);
        note.writeDetails("saw a ghost");
        assertEquals(note.getDeathDetails(PERSON2), "");
    }
}
