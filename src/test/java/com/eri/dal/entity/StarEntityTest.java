package com.eri.dal.entity;

import com.eri.constants.enums.TestData;
import com.eri.model.Star;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class StarEntityTest {
    //region getSetId
    @Test
    public void getSetIdTest(){
        Long id = 1L;
        StarEntity starEntity = new StarEntity();
        // actual method call
        starEntity.setId(id);
        // assertions
        Assert.assertEquals(id, starEntity.getId());
    }
    //endregion getSetId

    //region getSetName
    @Test
    public void getSetNameTest(){
        StarEntity starEntity = new StarEntity();
        // actual method call
        starEntity.setName(TestData.STAR_NAME.getValue());
        // assertions
        Assert.assertEquals(TestData.STAR_NAME.getValue(), starEntity.getName());
    }
    //endregion getSetName

    //region getSetSurname
    @Test
    public void getSetSurnameTest(){
        StarEntity starEntity = new StarEntity();
        // actual method call
        starEntity.setSurname(TestData.STAR_SURNAME.getValue());
        // assertions
        Assert.assertEquals(TestData.STAR_SURNAME.getValue(), starEntity.getSurname());
    }
    //endregion getSetSurname

    //region getSetCreated
    @Test
    public void getSetCreatedTest(){
        Date date = new Date();
        StarEntity starEntity = new StarEntity();
        // actual method call
        starEntity.setCreated(date);
        // assertions
        Assert.assertEquals(date, starEntity.getCreated());
    }
    //endregion getSetCreated

    //region getSetUpdated
    @Test
    public void getSetUpdatedTest(){
        Date date = new Date();
        StarEntity starEntity = new StarEntity();
        // actual method call
        starEntity.setUpdated(date);
        // assertions
        Assert.assertEquals(date, starEntity.getUpdated());
    }
    //endregion getSetUpdated

    //region isSetDeleted
    @Test
    public void isSetDeletedTest(){
        StarEntity starEntity = new StarEntity();
        // actual method call
        starEntity.setDeleted(true);
        // assertions
        Assert.assertTrue(starEntity.isDeleted());
    }
    //endregion isSetDeleted

    //region onCreate
    @Test
    public void onCreateTest(){
        StarEntity starEntity = new StarEntity();
        // actual method call
        starEntity.onCreate();
        // assertions
        Assert.assertEquals(starEntity.getCreated(), starEntity.getUpdated());
    }
    //endregion onCreate

    //region onUpdate
    @Test
    public void onUpdateTest() throws InterruptedException {
        StarEntity starEntity = new StarEntity();
        starEntity.onCreate();
        // assertions
        Assert.assertEquals(starEntity.getCreated(), starEntity.getUpdated());
        Thread.sleep(1000);
        // actual method call
        starEntity.onUpdate();
        // assertions
        Assert.assertNotEquals(starEntity.getCreated(), starEntity.getUpdated());
    }
    //endregion onUpdate

    //region equals
    @Test
    public void equalsTrueTest(){
        StarEntity starEntity1 = new StarEntity();
        StarEntity starEntity2 = starEntity1;
        // assertions
        Assert.assertTrue(starEntity1.equals(starEntity2));
    }

    @Test
    public void equalsFalseTest(){
        StarEntity starEntity = new StarEntity();
        Star star = new Star();
        // assertions
        Assert.assertFalse(starEntity.equals(star));
    }
    //endregion equals
}
