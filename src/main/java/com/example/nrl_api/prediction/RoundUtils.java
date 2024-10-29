package com.example.nrl_api.prediction;

import java.util.List;

/**
 * Utility class for handling the custom ordering of NRL rounds.
 * <p>
 * The `RoundUtils` class defines a custom order for rounds used in the NRL,
 * enabling sorting and comparison of rounds that are stored as strings.
 */

public class RoundUtils {
    /**
     * Static list defining the custom order of rounds.
     * This list represents all possible round values in the correct sequence
     * from the first round to the final. The order is maintained by the index
     * of each round in the list, where a lower index represents an earlier
     * round and a higher index represents a later round.
     */
    private static final List<String> ROUND_ORDER = List.of(
            "Round 1", "Round 2", "Round 3", "Round 4", "Round 5", "Round 6", "Round 7",
            "Round 8", "Round 9", "Round 10", "Round 11", "Round 12", "Round 13", "Round 14",
            "Round 15", "Round 16", "Round 17", "Round 18", "Round 19", "Round 20", "Round 21",
            "Round 22", "Round 23", "Round 24", "Round 25", "Round 26", "Round 27",
            "Finals Week 1", "Finals Week 2", "Finals Week 3", "Grand Final"
    );

    /**
     * Retrieves the index of a given round within the custom round order.
     * This method provides a way to compare rounds based on their true order
     * in the season, by returning an integer index for any valid round string.
     * The index can be used in comparisons to determine which round is later or earlier.
     *
     * @param round The round name (e.g., "Round 1", "Finals Week 2") to look up.
     * @return The index of the round in `ROUND_ORDER`, or -1 if the round is not found.
     */
    public static int getRoundOrder(String round) {
        return ROUND_ORDER.indexOf(round);
    }
}
