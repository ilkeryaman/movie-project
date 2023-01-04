package com.eri.dal.service.impl;

import com.eri.constants.enums.TestData;
import com.eri.dal.entity.StarEntity;
import com.eri.dal.repository.StarRepository;
import com.eri.exception.NullNameException;
import com.eri.exception.NullSurnameException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class StarServiceImplTest {
    @InjectMocks
    StarServiceImpl starService;

    //region mocks
    @Mock
    StarRepository starRepositoryMock;
    //endregion mocks

    //region saveStar
    @Test
    public void saveStarTest(){
        StarEntity starEntity = new StarEntity();
        starEntity.setId(1L);
        starEntity.setName(TestData.STAR_NAME.getValue());
        starEntity.setSurname(TestData.STAR_SURNAME.getValue());
        // mocking
        Mockito.when(starRepositoryMock.save(Mockito.any(StarEntity.class))).thenReturn(starEntity);
        // actual method call
        StarEntity starEntityActual = starService.saveStar(starEntity);
        // assertions
        Assert.assertEquals(starEntity, starEntityActual);
    }
    //endregion saveStar


    //region getStarList
    @Test
    public void getStarListTest(){
        StarEntity starEntity1 = new StarEntity();
        starEntity1.setId(1L);
        starEntity1.setName(TestData.STAR_NAME.getValue());
        starEntity1.setSurname(TestData.STAR_SURNAME.getValue());
        StarEntity starEntity2 = new StarEntity();
        starEntity2.setId(2L);
        starEntity2.setName(TestData.STAR_NAME.getValue() + " 2");
        starEntity2.setSurname(TestData.STAR_SURNAME.getValue() + " 2");
        // mocking
        Mockito.when(starRepositoryMock.findAll()).thenReturn(Arrays.asList(starEntity1, starEntity2));
        // actual method call
        List<StarEntity> starEntityList =  starService.getStarList();
        // assertions
        Assert.assertEquals(1L, starEntityList.get(0).getId().longValue());
        Assert.assertEquals(TestData.STAR_NAME.getValue(), starEntityList.get(0).getName());
        Assert.assertEquals(TestData.STAR_SURNAME.getValue(), starEntityList.get(0).getSurname());
        Assert.assertEquals(2L, starEntityList.get(1).getId().longValue());
        Assert.assertEquals(TestData.STAR_NAME.getValue() + " 2", starEntityList.get(1).getName());
        Assert.assertEquals(TestData.STAR_SURNAME.getValue() + " 2", starEntityList.get(1).getSurname());
    }
    //endregion getStarList

    //region getStarById
    @Test
    public void getStarByIdTest(){
        long id = 1L;
        StarEntity starEntity = new StarEntity();
        starEntity.setId(id);
        starEntity.setName(TestData.STAR_NAME.getValue());
        starEntity.setSurname(TestData.STAR_SURNAME.getValue());
        // mocking
        Mockito.when(starRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(starEntity));
        // actual method call
        StarEntity starEntityActual = starService.getStarById(id);
        // assertions
        Assert.assertEquals(starEntity, starEntityActual);
    }

    @Test
    public void getStarByIdStarNotFoundTest(){
        // mocking
        Mockito.when(starRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        // actual method call
        StarEntity starEntity = starService.getStarById(1200L);
        // assertions
        Assert.assertNull(starEntity);
    }
    //endregion getStarById

    //region updateStar
    @Test
    public void updateStarTest(){
        long id = 1L;
        StarEntity starEntity = new StarEntity();
        starEntity.setId(id);
        starEntity.setName(TestData.STAR_NAME.getValue());
        starEntity.setSurname(TestData.STAR_SURNAME.getValue());
        // mocking
        Mockito.when(starRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(starEntity));
        Mockito.when(starRepositoryMock.save(Mockito.any(StarEntity.class))).thenReturn(starEntity);
        // actual method call
        starService.updateStar(starEntity, id);
        // assertions
        Mockito.verify(starRepositoryMock, Mockito.times(1)).save(Mockito.any(StarEntity.class));
    }

    @Test(expected = NullNameException.class)
    public void updateStarNullNameTest(){
        long id = 1L;
        StarEntity starEntity = new StarEntity();
        starEntity.setId(id);
        starEntity.setSurname(TestData.STAR_SURNAME.getValue());
        // mocking
        Mockito.when(starRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(starEntity));
        // actual method call
        starService.updateStar(starEntity, id);
    }

    @Test(expected = NullSurnameException.class)
    public void updateStarNullSurnameTest(){
        long id = 1L;
        StarEntity starEntity = new StarEntity();
        starEntity.setId(id);
        starEntity.setName(TestData.STAR_NAME.getValue());
        // mocking
        Mockito.when(starRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(starEntity));
        // actual method call
        starService.updateStar(starEntity, id);
    }
    //endregion updateStar
}
