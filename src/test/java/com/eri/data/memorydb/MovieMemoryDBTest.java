package com.eri.data.memorydb;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MovieMemoryDBTest {
    @Test
    public void getMoviesTest(){
        MovieMemoryDB movieMemoryDB = new MovieMemoryDB();
        // assertions
        Assert.assertNotNull(movieMemoryDB.getMovies());
    }
}
