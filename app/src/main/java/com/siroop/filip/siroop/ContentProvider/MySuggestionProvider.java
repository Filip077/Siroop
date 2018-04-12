package com.siroop.filip.siroop.ContentProvider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by filip on 6.3.18.
 */

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.siroop.filip.siroop";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY,MODE);
    }
}
