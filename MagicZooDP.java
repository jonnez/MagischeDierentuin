import java.util.*;

public class MagicZooDP
{
//    private static final State FINAL_STATE = new State(55, 17, 6);
    private static final State FINAL_STATE = new State(2055, 2017, 2006);
    private Map<State, Integer> score = new HashMap<>();
    private Map<State, Integer> scorePrevious;

    public MagicZooDP()
    {
        for (int sum = 1; !score.containsKey(FINAL_STATE); sum++)
        {
            System.out.println(String.format("%s Round: %d Map size: %d", new Date(), sum, score.size()));
            scorePrevious = score;
            score = new HashMap<>();
            process(sum);
        }
        System.out.println("Score for " + FINAL_STATE + " is " + score.get(FINAL_STATE));
    }

    private void compute(int goats, int wolfs, int lions)
    {
        assert goats >= wolfs;
        assert wolfs >= lions;
        assert lions >= 0;

        State state = new State(goats, wolfs, lions);
        int value = Integer.MIN_VALUE, v;

        // basis case
        if (wolfs == lions)
        {
            score.put(state, goats + wolfs);
            return;
        }

        if (wolfs > 0 && lions > 0) // lion eats wolf becomes goat
        {
            v = scorePrevious.get(new State(goats + 1, wolfs - 1, lions - 1));
            value = Math.max(value, v);
        }
        if (wolfs > 0 && goats > 0) // wolf eats goat becomes lion
        {
            // re-order if we get more lions than wolfs and/or goats, because problem is symmetric
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
            // re-order if we get more wolfs than goats, because problem is symmetric
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
    }

    public static void main(String[] args)
    {
        new MagicZooDP();
    }
}
