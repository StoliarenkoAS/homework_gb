package ru.stoliarenko.gb.lesson7.client.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ClientParseUserInputEvent {
    private String text;
}
