package io.renatofreire.chat_room.records;

import io.renatofreire.chat_room.enums.MessageType;

public record ChatMessage(
        String content,
        String sender,
        MessageType type
) {
}
