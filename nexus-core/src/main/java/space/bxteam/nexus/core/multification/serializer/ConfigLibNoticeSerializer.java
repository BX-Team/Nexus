package space.bxteam.nexus.core.multification.serializer;

import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.notice.Notice.Builder;
import com.eternalcode.multification.notice.NoticeKey;
import com.eternalcode.multification.notice.NoticePart;
import com.eternalcode.multification.notice.resolver.NoticeContent;
import com.eternalcode.multification.notice.resolver.NoticeDeserializeResult;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import com.eternalcode.multification.notice.resolver.NoticeSerdesResult;
import com.eternalcode.multification.notice.resolver.NoticeSerdesResult.Multiple;
import com.eternalcode.multification.notice.resolver.NoticeSerdesResult.Single;
import com.eternalcode.multification.notice.resolver.chat.ChatContent;
import de.exlll.configlib.Serializer;

import java.util.*;

public class ConfigLibNoticeSerializer implements Serializer<Notice, Object> {
    private final NoticeResolverRegistry noticeRegistry;

    public ConfigLibNoticeSerializer(NoticeResolverRegistry noticeRegistry) {
        this.noticeRegistry = noticeRegistry;
    }

    @Override
    public Object serialize(Notice notice) {
        if (trySerializeChatDirectly(notice)) {
            return getSingleChatMessage(notice);
        }

        Map<String, Object> data = new HashMap<>();
        for (NoticePart<?> part : notice.parts()) {
            NoticeSerdesResult result = this.noticeRegistry.serialize(part);

            if (result instanceof Single single) {
                data.put(part.noticeKey().key(), single.element());
            } else if (result instanceof Multiple multiple) {
                data.put(part.noticeKey().key(), multiple.elements());
            } else if (result instanceof NoticeSerdesResult.Section section) {
                data.put(part.noticeKey().key(), section.elements());
            }
        }

        return data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Notice deserialize(Object data) {
        Builder builder = Notice.builder();

        if (data instanceof String stringValue) {
            builder.withPart(NoticeKey.CHAT, new ChatContent(Collections.singletonList(stringValue)));
            return builder.build();
        } else if (data instanceof Map<?, ?> mapData) {
            for (Map.Entry<?, ?> entry : mapData.entrySet()) {
                String key = entry.getKey().toString();
                Object value = entry.getValue();

                if (value instanceof String stringValue) {
                    NoticeDeserializeResult<?> noticeResult = this.noticeRegistry.deserialize(key, new Single(stringValue))
                            .orElseThrow(() -> new UnsupportedOperationException(
                                    "Unsupported notice key: " + key + " with value: " + stringValue));
                    withPart(builder, noticeResult);
                } else if (value instanceof List<?> listValue) {
                    List<String> messages = new ArrayList<>();
                    for (Object item : listValue) {
                        if (item instanceof String str) {
                            messages.add(str);
                        }
                    }
                    NoticeDeserializeResult<?> noticeResult = this.noticeRegistry.deserialize(key, new Multiple(messages))
                            .orElseThrow(() -> new UnsupportedOperationException(
                                    "Unsupported notice key: " + key + " with values: " + messages));
                    withPart(builder, noticeResult);
                } else if (value instanceof Map<?, ?> mapValue) {
                    NoticeDeserializeResult<?> noticeResult = this.noticeRegistry.deserialize(key, new NoticeSerdesResult.Section((Map<String, String>) mapValue))
                            .orElseThrow(() -> new UnsupportedOperationException(
                                    "Unsupported notice key: " + key + " with values: " + mapValue));
                    withPart(builder, noticeResult);
                } else {
                    throw new UnsupportedOperationException(
                            "Unsupported notice type: " + value.getClass() + " for key: " + key);
                }
            }
        } else {
            throw new UnsupportedOperationException("Unsupported data type: " + data.getClass());
        }

        return builder.build();
    }

    private <T extends NoticeContent> void withPart(Builder builder, NoticeDeserializeResult<T> noticeResult) {
        builder.withPart(noticeResult.noticeKey(), noticeResult.content());
    }

    private boolean trySerializeChatDirectly(Notice notice) {
        List<NoticePart<?>> parts = notice.parts();
        return parts.size() == 1 && parts.get(0).noticeKey() == NoticeKey.CHAT;
    }

    private Object getSingleChatMessage(Notice notice) {
        NoticePart<?> part = notice.parts().get(0);
        ChatContent chat = (ChatContent) part.content();
        List<String> messages = chat.contents();
        return messages.size() == 1 ? messages.get(0) : messages;
    }
}
