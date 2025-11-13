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

    private DeathNote diario;

    @BeforeEach
    void setUp() {
        this.diario = new DeathNoteImpl();
    }

    @Test
    void testOfGetRule() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                diario.getRule(0);
            }
        });
    }

    @Test
    void testOfGetRule2() {
        for (int i = 1; i < NUMBER_OF_RULE; i++) {
            assertTrue(diario.getRule(i) != null, "the rule " + i + "is null");
        }
    }

    @Test
    void testWriteName() {
        if (!diario.isNameWritten(PERSON)) {
            diario.writeName(PERSON);
            assertTrue(diario.isNameWritten(PERSON));
            assertFalse(diario.isNameWritten("Nicolas Bianchi"));
            assertFalse(diario.isNameWritten(""));
        }
    }

    @Test
    void testWriteCauseOfDeath() throws InterruptedException {
        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                diario.writeDeathCause("heart attack");
            }
        });
        diario.writeName(PERSON);
        assertEquals(diario.getDeathCause(PERSON), "heart attack");
        diario.writeName(PERSON2);
        assertTrue(diario.writeDeathCause("karting accident"));
        Thread.sleep(100);
        assertFalse(diario.writeDeathCause("hit by a track"));
        assertEquals(diario.getDeathCause(PERSON2), "karting accident");
    }

    @Test
    void testWriteDetailsOfDeath() throws InterruptedException {
        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                diario.writeDetails("ran");
            }
        });
        diario.writeName(PERSON);
        assertEquals("", diario.getDeathDetails(PERSON));
        assertTrue(diario.writeDetails("ran for too long"));
        assertEquals(diario.getDeathDetails(PERSON), "ran for too long");
        diario.writeName(PERSON2);
        Thread.sleep(TIME);
        diario.writeDetails("saw a ghost");
        assertEquals(diario.getDeathDetails(PERSON2), "");
    }
}
