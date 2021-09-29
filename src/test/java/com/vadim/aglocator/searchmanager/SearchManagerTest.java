package com.vadim.aglocator.searchmanager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchManagerTest {

    SearchManager searchManager;

    @BeforeEach
    public void init() {
        searchManager = new SearchManagerImpl();
    }

    @Test
    void getUnsortedUnscoredResults() {
    }

    @Test
    void scoreUnsortedUnscoredResults() {
    }

    @Test
    void sortUnsortedScoredResults() {
    }
}
