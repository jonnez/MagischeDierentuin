public class State
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

    @Override
    public String toString()
    {
        return String.format("(%d, %d, %d)", goats, wolfs, lions);
    }
}
