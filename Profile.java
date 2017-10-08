import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Grant on 10/7/2017.
 */
public class Profile {
    private String username; //unique username
    private String password; //password to log in
    private String location; //optional address/general location
    private HashMap<Long, Book> haveList; //list of books being offered
    private ArrayList<Long> wantList; //list of books user wants

    Profile(String username, String password, String location) {
        this.username = username;
        this.password = password;
        this.location = location;
        this.haveList = new HashMap<>();
        this.wantList = new ArrayList<>();
    }   

    String getUsername() {
        return this.username;
    }

    String getLocation() {
        return this.location;
    }

    Set<Long> getHaveList() {
        return haveList.keySet();
    }

    ArrayList<Long> getWantList() {
        return wantList;
    }

    void addHaveBook(Book book) {
        this.haveList.put(book.getISBN(), book);
    }

    void addWantBook(long ISBN) {
        this.wantList.add(ISBN);
    }

    void deleteHaveBook(Book book) {
        this.haveList.remove(book.getISBN());
    }

    void deleteWantBook(long ISBN) {
        this.wantList.remove(ISBN);
    }
}
