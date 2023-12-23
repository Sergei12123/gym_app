package com.example.learn_spring_core;

import com.example.learn_spring_core.repository.entity.BaseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestsParent {
    @Mock
    private ObjectMapper objectMapper;

    public TestsParent() {
        MockitoAnnotations.openMocks(this);
        try {
            doReturn("Entity as json").when(objectMapper).writeValueAsString(any(BaseEntity.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
