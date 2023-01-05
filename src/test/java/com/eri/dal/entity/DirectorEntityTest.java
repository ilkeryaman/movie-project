package com.eri.dal.entity;

import com.eri.constants.enums.TestData;
import com.eri.model.Director;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class DirectorEntityTest {
    //region getSetId
    @Test
    public void getSetIdTest(){
        Long id = 1L;
        DirectorEntity directorEntity = new DirectorEntity();
        // actual method call
        directorEntity.setId(id);
        // assertions
        Assert.assertEquals(id, directorEntity.getId());
    }
    //endregion getSetId

    //region getSetName
    @Test
    public void getSetNameTest(){
        DirectorEntity directorEntity = new DirectorEntity();
        // actual method call
        directorEntity.setName(TestData.DIRECTOR_NAME.getValue());
        // assertions
        Assert.assertEquals(TestData.DIRECTOR_NAME.getValue(), directorEntity.getName());
    }
    //endregion getSetName

    //region getSetSurname
    @Test
    public void getSetSurnameTest(){
        DirectorEntity directorEntity = new DirectorEntity();
        // actual method call
        directorEntity.setSurname(TestData.DIRECTOR_SURNAME.getValue());
        // assertions
        Assert.assertEquals(TestData.DIRECTOR_SURNAME.getValue(), directorEntity.getSurname());
    }
    //endregion getSetSurname

    //region getSetCreated
    @Test
    public void getSetCreatedTest(){
        Date date = new Date();
        DirectorEntity directorEntity = new DirectorEntity();
        // actual method call
        directorEntity.setCreated(date);
        // assertions
        Assert.assertEquals(date, directorEntity.getCreated());
    }
    //endregion getSetCreated

    //region getSetUpdated
    @Test
    public void getSetUpdatedTest(){
        Date date = new Date();
        DirectorEntity directorEntity = new DirectorEntity();
        // actual method call
        directorEntity.setUpdated(date);
        // assertions
        Assert.assertEquals(date, directorEntity.getUpdated());
    }
    //endregion getSetUpdated

    //region isSetDeleted
    @Test
    public void isSetDeletedTest(){
        DirectorEntity directorEntity = new DirectorEntity();
        // actual method call
        directorEntity.setDeleted(true);
        // assertions
        Assert.assertTrue(directorEntity.isDeleted());
    }
    //endregion isSetDeleted

    //region onCreate
    @Test
    public void onCreateTest(){
        DirectorEntity directorEntity = new DirectorEntity();
        // actual method call
        directorEntity.onCreate();
        // assertions
        Assert.assertEquals(directorEntity.getCreated(), directorEntity.getUpdated());
    }
    //endregion onCreate

    //region onUpdate
    @Test
    public void onUpdateTest() throws InterruptedException {
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.onCreate();
        // assertions
        Assert.assertEquals(directorEntity.getCreated(), directorEntity.getUpdated());
        Thread.sleep(1000);
        // actual method call
        directorEntity.onUpdate();
        // assertions
        Assert.assertNotEquals(directorEntity.getCreated(), directorEntity.getUpdated());
    }
    //endregion onUpdate

    //region equals
    @Test
    public void equalsTrueTest(){
        DirectorEntity directorEntity1 = new DirectorEntity();
        DirectorEntity directorEntity2 = directorEntity1;
        // assertions
        Assert.assertTrue(directorEntity1.equals(directorEntity2));
    }

    @Test
    public void equalsFalseTest(){
        DirectorEntity directorEntity = new DirectorEntity();
        Director director = new Director();
        // assertions
        Assert.assertFalse(directorEntity.equals(director));
    }
    //endregion equals
}
