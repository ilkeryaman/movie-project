package com.eri.service.messaging;

import com.eri.model.messaging.EventMessage;

public interface IMessageService {
    void sendMessage(EventMessage eventMessage);
}
