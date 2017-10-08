import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Grant on 10/7/2017.
 */
public class Matcher {
    /* PQ of Profile objects that have at least 1 mutual match to Profile conducting search */
    private MaxPQ<Profile> matches;

    Matcher(long[] ISBNList) {
        this.matches = new MaxPQ<>();
    }

    /**
     * Returns a PQ of profiles with at least 1 mutual match to the Profile conducting a search,
     * ordered by the number of books a Profile is offering on the user Profile's want list, and
     * sub-ordered by the absolute value of the difference of the number of books each Profile has
     * for the other.
     * @param profiles - mapping of ISBNs to Profiles offering that ISBN
     * @param user - profile of user looking for a match
     * @return
     */
    MaxPQ<Profile> findMatches(HashMap<Long, ArrayList<Profile>> profiles, Profile user) {
        for (long ISBN : user.getWantList()) {
            ArrayList<Profile> potentialMatches = profiles.get(ISBN);
            for (Profile profile : potentialMatches) {
                if (!this.matches.changePriority(profile, 0)) {
                    ArrayList<Long> partnerWantList = profile.getWantList();
                    int counter = 0;
                    for (Long ISBN2 : partnerWantList) {
                        if (user.getHaveList().contains(ISBN2)) {
                            counter++;
                        }
                    }
                    if (counter > 0) {
                        this.matches.insert(profile, new int[]{1, counter});
                    }
                }
            }
        }

        return this.matches;
    }


}
