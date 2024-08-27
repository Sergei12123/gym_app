package com.example.gym_app;

import com.example.gym_app.entity.BaseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

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
