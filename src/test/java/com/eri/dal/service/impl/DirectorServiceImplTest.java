package com.eri.dal.service.impl;

import com.eri.constants.enums.TestData;
import com.eri.dal.entity.DirectorEntity;
import com.eri.dal.repository.DirectorRepository;
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
public class DirectorServiceImplTest {
    @InjectMocks
    DirectorServiceImpl directorService;

    //region mocks
    @Mock
    DirectorRepository directorRepositoryMock;
    //endregion mocks

    //region saveDirector
    @Test
    public void saveDirectorTest(){
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setId(1L);
        directorEntity.setName(TestData.DIRECTOR_NAME.getValue());
        directorEntity.setSurname(TestData.DIRECTOR_SURNAME.getValue());
        // mocking
        Mockito.when(directorRepositoryMock.save(Mockito.any(DirectorEntity.class))).thenReturn(directorEntity);
        // actual method call
        DirectorEntity directorEntityActual = directorService.saveDirector(directorEntity);
        // assertions
        Assert.assertEquals(directorEntity, directorEntityActual);
    }
    //endregion saveDirector


    //region getDirectorList
    @Test
    public void getDirectorListTest(){
        DirectorEntity directorEntity1 = new DirectorEntity();
        directorEntity1.setId(1L);
        directorEntity1.setName(TestData.DIRECTOR_NAME.getValue());
        directorEntity1.setSurname(TestData.DIRECTOR_SURNAME.getValue());
        DirectorEntity directorEntity2 = new DirectorEntity();
        directorEntity2.setId(2L);
        directorEntity2.setName(TestData.DIRECTOR_NAME.getValue() + " 2");
        directorEntity2.setSurname(TestData.DIRECTOR_SURNAME.getValue() + " 2");
        // mocking
        Mockito.when(directorRepositoryMock.findAll()).thenReturn(Arrays.asList(directorEntity1, directorEntity2));
        // actual method call
        List<DirectorEntity> directorEntityList =  directorService.getDirectorList();
        // assertions
        Assert.assertEquals(1L, directorEntityList.get(0).getId().longValue());
        Assert.assertEquals(TestData.DIRECTOR_NAME.getValue(), directorEntityList.get(0).getName());
        Assert.assertEquals(TestData.DIRECTOR_SURNAME.getValue(), directorEntityList.get(0).getSurname());
        Assert.assertEquals(2L, directorEntityList.get(1).getId().longValue());
        Assert.assertEquals(TestData.DIRECTOR_NAME.getValue() + " 2", directorEntityList.get(1).getName());
        Assert.assertEquals(TestData.DIRECTOR_SURNAME.getValue() + " 2", directorEntityList.get(1).getSurname());
    }
    //endregion getDirectorList

    //region getDirectorById
    @Test
    public void getDirectorByIdTest(){
        long id = 1L;
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setId(id);
        directorEntity.setName(TestData.DIRECTOR_NAME.getValue());
        directorEntity.setSurname(TestData.DIRECTOR_SURNAME.getValue());
        // mocking
        Mockito.when(directorRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(directorEntity));
        // actual method call
        DirectorEntity directorEntityActual = directorService.getDirectorById(id);
        // assertions
        Assert.assertEquals(directorEntity, directorEntityActual);
    }

    @Test
    public void getDirectorByIdDirectorNotFoundTest(){
        // mocking
        Mockito.when(directorRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        // actual method call
        DirectorEntity directorEntity = directorService.getDirectorById(1200L);
        // assertions
        Assert.assertNull(directorEntity);
    }
    //endregion getDirectorById

    //region updateDirector
    @Test
    public void updateDirectorTest(){
        long id = 1L;
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setId(id);
        directorEntity.setName(TestData.DIRECTOR_NAME.getValue());
        directorEntity.setSurname(TestData.DIRECTOR_SURNAME.getValue());
        // mocking
        Mockito.when(directorRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(directorEntity));
        Mockito.when(directorRepositoryMock.save(Mockito.any(DirectorEntity.class))).thenReturn(directorEntity);
        // actual method call
        directorService.updateDirector(directorEntity, id);
        // assertions
        Mockito.verify(directorRepositoryMock, Mockito.times(1)).save(Mockito.any(DirectorEntity.class));
    }

    @Test(expected = NullNameException.class)
    public void updateDirectorNullNameTest(){
        long id = 1L;
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setId(id);
        directorEntity.setSurname(TestData.DIRECTOR_SURNAME.getValue());
        // mocking
        Mockito.when(directorRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(directorEntity));
        // actual method call
        directorService.updateDirector(directorEntity, id);
    }

    @Test(expected = NullSurnameException.class)
    public void updateDirectorNullSurnameTest(){
        long id = 1L;
        DirectorEntity directorEntity = new DirectorEntity();
        directorEntity.setId(id);
        directorEntity.setName(TestData.DIRECTOR_NAME.getValue());
        // mocking
        Mockito.when(directorRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(directorEntity));
        // actual method call
        directorService.updateDirector(directorEntity, id);
    }
    //endregion updateDirector
}
