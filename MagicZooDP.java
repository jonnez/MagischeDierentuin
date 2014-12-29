import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class MagicZooDP
{
//    private static final State FINAL_STATE = new State(55, 17, 6);
    private static final State FINAL_STATE = new State(2055, 2017, 2006);
    private Map<State, Integer> score = new ConcurrentHashMap<>();
    private Map<State, Integer> scorePrevious;

    public MagicZooDP()
    {
        // som verhogen totdat we de oplossing hebben
        for (int sum = 1; !score.containsKey(FINAL_STATE); sum++)
        {
            System.out.println(String.format("%s Round: %d Map size: %d", new Date(), sum, score.size()));
            scorePrevious = score;
            score = new ConcurrentHashMap<>();
            processParallel(sum);
        }
        System.out.println("Score for " + FINAL_STATE + " is " + score.get(FINAL_STATE));
    }

    // berekent de score van de gegeven aantal dieren
    private void compute(int goats, int wolfs, int lions)
    {
        State state = new State(goats, wolfs, lions);
        int value = Integer.MIN_VALUE, v;

        // basis geval
        if (wolfs == lions)
        {
            score.put(state, goats + wolfs);
            return;
        }

        // hier weten we dat de oplossing het maximum van ten hoogste 3 elementen is,
        // die allemaal in de map scorePrevious moeten zitten. We hoeven dus niet eens
        // te kijken of de waarde in de map zit, we gebruiken hem direct.
        // (de vorige iteratie was namelijk voor een som goats+wolfs+lions van 1 kleiner).

        if (wolfs > 0 && lions > 0) // lion eats wolf becomes goat
        {
            v = scorePrevious.get(new State(goats + 1, wolfs - 1, lions - 1));
            value = Math.max(value, v);
        }
        if (wolfs > 0 && goats > 0) // wolf eats goat becomes lion
        {
            // we houden de argumenten in dalende volgorde, omdat het probleem symmetrisch is.
            // De reden dat we dit hier doen, en niet in de State constructor, is omdat we hier beter
            // weten wat we moeten controleren. Voor de case hierboven hoeft er bijvoorbeeld niets
            // gecontroleerd te worden.
            if (lions + 1 > goats -1)
            {
                v = scorePrevious.get(new State(lions + 1, goats - 1, wolfs - 1));
            }
            else if (lions + 1 > wolfs - 1)
            {
                v = scorePrevious.get(new State(goats - 1, lions + 1, wolfs - 1));
            }
            else
            {
                v = scorePrevious.get(new State(goats - 1, wolfs - 1, lions + 1));
            }
            value = Math.max(value, v);
        }
        if (lions > 0 && goats > 0) // lion eats goat becomes wolf
        {
            // we houden de argumenten in dalende volgorde, omdat het probleem symmetrisch is.
            if (wolfs + 1 > goats - 1)
            {
                v = scorePrevious.get(new State(wolfs + 1, goats - 1, lions - 1));
            }
            else
            {
                v = scorePrevious.get(new State(goats - 1, wolfs + 1, lions - 1));
            }
            value = Math.max(value, v);
        }

        score.put(state, value);
    }

    /* Dit was de sequentiele versie die vervangen is door de parallelle versie hieronder.
       De enige andere wijziging die daarvoor nodig was is het overstappen van een
       HashMap naar een ConcurrentHashmap voor scores en scorePrevious.
    // De twee for-loops samen lopen alle mogelijke partitioneringen af van 3 groepen en de
    // gegeven som. Het enige wat we met een verdeling in drie groepen hoeven te doen is de
    // functie compute hierboven aan te roepen.
    private void process(int sum)
    {
        int min0 = sum / 3 + (sum % 3 == 0 ? 0 : 1);
        for (int goats = sum; goats >= min0; goats--)
        {
            for (int wolfs = Math.min(goats, sum - goats); 2 * wolfs >= sum - goats; wolfs--)
            {
                compute(goats, wolfs, sum - wolfs - goats);
            }
        }
    } */

    private void processParallel(int sum)
    {
        IntStream.rangeClosed(sum / 3 + (sum % 3 == 0 ? 0 : 1), sum)
            .parallel()
            .forEach(goats -> process(sum, goats));
    }

    private void process(int sum, int goats)
    {
        for (int wolfs = Math.min(goats, sum - goats); 2 * wolfs >= sum - goats; wolfs--)
        {
            compute(goats, wolfs, sum - wolfs - goats);
        }
    }

    public static void main(String[] args)
    {
        new MagicZooDP();
    }
}
