package it.unibo.deathnote.api;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * implementation of the death note interface.
 */
public final class DeathNoteImpl implements DeathNote {

    private static final double TIME_FOR_CAUSE_OF_DEATH = 0.0040;
    private static final double TIME_FOR_DETAILS = 6 + TIME_FOR_CAUSE_OF_DEATH;
    private final List<NameOnDeathNote> names = new LinkedList<>();
    private int size;
    private double time;

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1) {
            throw new IllegalArgumentException("there arent negative number of rule");
        } else {
            final Iterator<String> it = RULES.iterator();
            int count = 0;
            final String rule;
            while (it.hasNext()) {
                count++;
                if (count == ruleNumber) {
                    rule = it.next();
                    return rule;
                } else {
                    it.next();
                }
            }
            throw new IllegalArgumentException("number of rule is too large");
        }
    }

    @Override
    public void writeName(final String name) {
        names.add(new NameOnDeathNote(name));
        this.size++;
        time = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (cause == null) {
            throw new IllegalStateException("the cause is null");
        } 
        final Iterator<NameOnDeathNote> it = names.iterator();
        int i = 1;
        while (it.hasNext()) {
            if (i == size) {
                if (System.currentTimeMillis() - this.time <= TIME_FOR_CAUSE_OF_DEATH) {
                    it.next().setCause(cause);
                    this.time = System.currentTimeMillis();
                    return true;
                } else {
                    return false;
                }
            }
            it.next();
            i++;
        }
        throw new IllegalStateException("No name on the deathnote");
    }

    @Override
    public boolean writeDetails(final String details) {
        if (details == null) {
            throw new IllegalStateException("the detail is null");
        } 
        final Iterator<NameOnDeathNote> it = names.iterator();
        int i = 1;
        while (it.hasNext()) {
            if (i == size) {
                if (System.currentTimeMillis() - this.time <= TIME_FOR_DETAILS) {
                    it.next().setDetails(details);
                    return true;
                } else {
                    return false;
                }
            }
            it.next();
            i++;
        }
        throw new IllegalStateException("No name on the deathnote");
    }

    @Override
    public String getDeathCause(final String name) {
        final Iterator<NameOnDeathNote> it = names.iterator();
        NameOnDeathNote person;
        while (it.hasNext()) {
            person = it.next();
            if (person.getName().equals(name)) {
                return person.getCause();
            }
        }
        throw new IllegalArgumentException("no such name in the deathnote");
    }

    @Override
    public String getDeathDetails(final String name) {
        final Iterator<NameOnDeathNote> it = names.iterator();
        NameOnDeathNote person;
        while (it.hasNext()) {
            person = it.next();
            if (person.getName().equals(name)) {
                return person.getDetails();
            }
        }
        throw new IllegalArgumentException("no such name in the deathnote");
    }

    @Override
    public boolean isNameWritten(final String name) {
        final Iterator<NameOnDeathNote> it = names.iterator();
        while (it.hasNext()) {
            if (it.next().getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /*
     * I added a class for the exercise.
     */
    private static class NameOnDeathNote {

        private final String name;
        private String cause;
        private String details;

        NameOnDeathNote(final String name) {
            this.name = name;
            this.cause = "heart attack";
            this.details = "";
        }

        private void setCause(final String cause) {
            this.cause = cause;
        }

        private void setDetails(final String details) {
            this.details = details;
        }

        private String getCause() {
            return this.cause;
        }

        private String getDetails() {
            return this.details;
        }

        private String getName() {
            return this.name;
        }
    }

}
