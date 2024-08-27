package com.example.gym_app.component;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/*
We need to create a new KeyHolder for each request to avoid problems with multithreading,
but we also need to test the save method and in order to make the KeyHolder return
the expected result in the save method, we need to make it a Mock object,
so it was decided to take out the KeyHolder generator as a separate component.
 */
@Component
public class KeyHolderGenerator {

    public KeyHolder getNewGeneratedKeyHoler() {
        return new GeneratedKeyHolder();
    }
}
