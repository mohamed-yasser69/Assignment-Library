/** Abstract base class holding everything all library items share. */
public abstract class LibraryItem {
    private String id;
    private String title;
    private boolean borrowed;

    private static int totalItemsCreated = 0;
    private static int nextNumber = 1;

    protected LibraryItem(String title) {
        validateTitle(title);              // validate first so a bad title never consumes an id
        this.title = title.trim();
        this.id = "ITEM-" + nextNumber++;
        this.borrowed = false;
        totalItemsCreated++;
    }

    private static void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
    }

    // ----- getters / setters -----
    public String getId() { return id; }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Id cannot be null or empty.");
        }
        this.id = id.trim();
    }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title.trim();
    }

    public boolean isBorrowed() { return borrowed; }   // no public setter on purpose

    public static int getTotalItemsCreated() { return totalItemsCreated; }

    // ----- shared behaviour -----
    public void markBorrowed() { this.borrowed = true; }

    public void markReturned() { this.borrowed = false; }

    public void displayInfo() {
        System.out.println(getId() + " | " + getTitle()
                + " | " + getType()
                + " | loan: " + getLoanPeriodDays() + " days"
                + " | " + (isBorrowed() ? "OUT" : "available"));
    }

    // ----- each subclass provides its own -----
    public abstract int getLoanPeriodDays();

    public abstract String getType();
}
