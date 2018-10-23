package ru.stoliarenko.gb.lesson7.server.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.stoliarenko.gb.lesson7.model.Connection;

@Getter
@AllArgsConstructor
public final class ResponceMessageDamagedEvent {
    private final Connection connection;
}
