import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Library {
    private final Map<String, LibraryItem> catalog = new LinkedHashMap<>();
    private final Map<String, Member> members = new LinkedHashMap<>();
    private final Set<String> borrowedIds = new LinkedHashSet<>();

    public void addItem(LibraryItem item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null.");
        catalog.put(item.getId(), item);
    }

    public void addMember(Member m) throws LibraryException {
        if (m == null) throw new IllegalArgumentException("Member cannot be null.");
        if (members.containsKey(m.getMemberId())) {
            throw new LibraryException("Member " + m.getMemberId() + " already exists.");
        }
        members.put(m.getMemberId(), m);
    }

    private Member findMember(String memberId) throws LibraryException {
        Member m = members.get(memberId);
        if (m == null) throw new LibraryException("member " + memberId + " does not exist.");
        return m;
    }

    private LibraryItem findItem(String itemId) throws LibraryException {
        LibraryItem item = catalog.get(itemId);
        if (item == null) throw new LibraryException("item " + itemId + " does not exist.");
        return item;
    }

    public void borrowItem(String memberId, String itemId) throws LibraryException {
        Member member = findMember(memberId);
        LibraryItem item = findItem(itemId);

        if (item.isBorrowed()) {
            throw new LibraryException("item " + itemId + " is already out.");
        }
        if (!member.canBorrowMore()) {
            throw new LibraryException("member " + memberId + " has reached the limit of "
                    + member.getMaxAllowed() + " items.");
        }
        item.markBorrowed();
        member.addBorrowedItem(item);
        borrowedIds.add(itemId);
    }

    public void returnItem(String memberId, String itemId) throws LibraryException {
        Member member = findMember(memberId);
        LibraryItem item = findItem(itemId);

        if (!member.holds(item)) {
            throw new LibraryException("member " + memberId + " did not borrow item " + itemId + ".");
        }
        item.markReturned();
        member.removeBorrowedItem(item);
        borrowedIds.remove(itemId);
    }

    public void listCatalog() {
        if (catalog.isEmpty()) {
            System.out.println("The catalog is empty.");
            return;
        }
        for (LibraryItem item : catalog.values()) {
            item.displayInfo();          // polymorphism: no instanceof needed
        }
    }

    // ----- bonus: search by title -----
    public void searchByTitle(String keyword) {
        String k = keyword.trim().toLowerCase();
        boolean found = false;
        for (LibraryItem item : catalog.values()) {
            if (item.getTitle().toLowerCase().contains(k)) {
                item.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("No items match \"" + keyword + "\".");
    }

    // ----- bonus: available items only -----
    public void listAvailable() {
        boolean found = false;
        for (LibraryItem item : catalog.values()) {
            if (!item.isBorrowed()) {
                item.displayInfo();
                found = true;
            }
        }
        if (!found) System.out.println("No items are currently available.");
    }

    // ----- bonus: statistics by type -----
    private Map<String, Integer> countByType() {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (LibraryItem item : catalog.values()) {
            counts.merge(item.getType(), 1, Integer::sum);
        }
        return counts;
    }

    public void printReport() {
        System.out.println("---------- REPORT ----------");
        System.out.println("Total items   : " + catalog.size());
        System.out.println("Currently out : " + borrowedIds.size());
        System.out.println("Borrowed ids  : " + borrowedIds);
        System.out.println("Items by type : " + countByType());
        System.out.println("Total created : " + LibraryItem.getTotalItemsCreated());
        System.out.println("----------------------------");
    }
}