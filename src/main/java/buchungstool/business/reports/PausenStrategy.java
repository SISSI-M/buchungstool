package buchungstool.business.reports;

/**
 * Created by Alexander Bischof on 25.09.15.
 */
public class PausenStrategy {

    //TODO property datei
    static final int PAUSEN_ABZUG = 30;
    static final int PAUSEN_GRENZE_WINTER = 300;
    static final int PAUSEN_GRENZE_SOMMER = 240;

    private boolean isSommer;

    public PausenStrategy() {
    }

    public PausenStrategy(boolean isSommer) {
        this.isSommer = isSommer;
    }

    public void sommer() {
        this.isSommer = true;
    }

    public int pausenAbzug(long durationInMinutes) {
        boolean abzugNecessary = durationInMinutes > (isSommer ? PAUSEN_GRENZE_SOMMER : PAUSEN_GRENZE_WINTER);
        return abzugNecessary ? PAUSEN_ABZUG : 0;
    }
}
