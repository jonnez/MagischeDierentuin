public class State implements Comparable<State>
{
    public final int goats;
    public final int wolfs;
    public final int lions;

    public State(int goats, int wolfs, int lions)
    {
        this.goats = goats;
        this.wolfs = wolfs;
        this.lions = lions;
    }

    @Override
    public int hashCode()
    {
        return Integer.hashCode(goats) ^
            Integer.hashCode(wolfs) ^
            Integer.hashCode(lions);
    }

    @Override
    public boolean equals(Object obj)
    {
        State other = (State) obj;
        return goats == other.goats &&
            wolfs == other.wolfs &&
            lions == other.lions;
    }

    public int upperbound()
    {
        return goats + wolfs;
    }

    public boolean isStable()
    {
        return wolfs == 0 && lions == 0;
    }

    @Override
    public int compareTo(State other)
    {
        return goats - other.goats;
    }

    @Override
    public String toString()
    {
        return String.format("(%d, %d, %d)", goats, wolfs, lions);
    }
}
