public class DVD extends LibraryItem {
    private int runtimeMinutes;

    public DVD(String title, int runtimeMinutes) {
        // validate runtime BEFORE super(), so an invalid DVD does not consume an id / bump the counter
        super(checkRuntime(title, runtimeMinutes));
        this.runtimeMinutes = runtimeMinutes;
    }

    private static String checkRuntime(String title, int runtimeMinutes) {
        if (runtimeMinutes <= 0) {
            throw new IllegalArgumentException("Runtime must be greater than zero.");
        }
        return title;
    }

    public int getRuntimeMinutes() { return runtimeMinutes; }

    public void setRuntimeMinutes(int runtimeMinutes) {
        if (runtimeMinutes <= 0) {
            throw new IllegalArgumentException("Runtime must be greater than zero.");
        }
        this.runtimeMinutes = runtimeMinutes;
    }

    @Override
    public int getLoanPeriodDays() { return 3; }

    @Override
    public String getType() { return "DVD"; }
}