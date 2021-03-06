package ru.stoliarenko.gb.lesson7.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class MessageUserDisconnected extends Message {
    private String username;
    
    {
        setType(MessageType.USER_DISCONNECTED);
    }
}
