import java.util.ArrayList;
import java.util.List;

public class Member {
    private String memberId;
    private String name;
    private int maxAllowed;
    private final List<LibraryItem> borrowedItems = new ArrayList<>();

    public Member(String memberId, String name, int maxAllowed) {
        setMemberId(memberId);
        setName(name);
        setMaxAllowed(maxAllowed);
    }

    public String getMemberId() { return memberId; }

    public void setMemberId(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member id cannot be empty.");
        }
        this.memberId = memberId.trim();
    }

    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public int getMaxAllowed() { return maxAllowed; }

    public void setMaxAllowed(int maxAllowed) {
        if (maxAllowed <= 0) {
            throw new IllegalArgumentException("Max allowed must be a positive number.");
        }
        this.maxAllowed = maxAllowed;
    }

    public int getBorrowedCount() { return borrowedItems.size(); }

    public boolean canBorrowMore() { return borrowedItems.size() < maxAllowed; }

    public boolean holds(LibraryItem item) { return borrowedItems.contains(item); }

    // helpers used by Library
    void addBorrowedItem(LibraryItem item) { borrowedItems.add(item); }

    boolean removeBorrowedItem(LibraryItem item) { return borrowedItems.remove(item); }
}