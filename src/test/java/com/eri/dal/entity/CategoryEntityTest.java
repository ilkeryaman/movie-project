package com.eri.dal.entity;

import com.eri.constant.enums.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class CategoryEntityTest {
    //region getSetId
    @Test
    public void getSetIdTest(){
        Long id = 1L;
        CategoryEntity categoryEntity = new CategoryEntity();
        // actual method call
        categoryEntity.setId(id);
        // assertions
        Assert.assertEquals(id, categoryEntity.getId());
    }
    //endregion getSetId

    //region getSetName
    @Test
    public void getSetNameTest(){
        CategoryEntity categoryEntity = new CategoryEntity();
        // actual method call
        categoryEntity.setName(Category.ACTION.getName());
        // assertions
        Assert.assertEquals(Category.ACTION.getName(), categoryEntity.getName());
    }
    //endregion getSetName

    //region getSetUpdated
    @Test
    public void getSetUpdatedTest(){
        Date date = new Date();
        CategoryEntity categoryEntity = new CategoryEntity();
        // actual method call
        categoryEntity.setUpdated(date);
        // assertions
        Assert.assertEquals(date, categoryEntity.getUpdated());
    }
    //endregion getSetUpdated

    //region isSetDeleted
    @Test
    public void isSetDeletedTest(){
        CategoryEntity categoryEntity = new CategoryEntity();
        // actual method call
        categoryEntity.setDeleted(true);
        // assertions
        Assert.assertTrue(categoryEntity.isDeleted());
    }
    //endregion isSetDeleted

    //region onCreate
    @Test
    public void onCreateTest(){
        CategoryEntity categoryEntity = new CategoryEntity();
        // actual method call
        categoryEntity.onCreate();
        // assertions
        Assert.assertEquals(categoryEntity.getCreated(), categoryEntity.getUpdated());
    }
    //endregion onCreate

    //region onUpdate
    @Test
    public void onUpdateTest() throws InterruptedException {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.onCreate();
        // assertions
        Assert.assertEquals(categoryEntity.getCreated(), categoryEntity.getUpdated());
        Thread.sleep(1000);
        // actual method call
        categoryEntity.onUpdate();
        // assertions
        Assert.assertNotEquals(categoryEntity.getCreated(), categoryEntity.getUpdated());
    }
    //endregion onUpdate

    //region equals
    @Test
    public void equalsTrueTest(){
        CategoryEntity categoryEntity1 = new CategoryEntity();
        CategoryEntity categoryEntity2 = categoryEntity1;
        // assertions
        Assert.assertTrue(categoryEntity1.equals(categoryEntity2));
    }

    @Test
    public void equalsFalseTest(){
        CategoryEntity categoryEntity = new CategoryEntity();
        String category = Category.ACTION.getName();
        // assertions
        Assert.assertFalse(categoryEntity.equals(category));
    }
    //endregion equals
}
