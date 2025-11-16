package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * Non graphical {@link DrawNumberView} implementation.
 */
public final class DrawNumberStandardOutputView implements DrawNumberView {

    @Override
    public void setController(final DrawNumberController observer) {
    }

    @Override
    public void start() {
        System.out.println("il gioco é iniziato..."); //NOPMD
    }

    @Override
    public void result(final DrawResult res) {
        System.out.println(res.getDescription()); //NOPMD
    }

}
