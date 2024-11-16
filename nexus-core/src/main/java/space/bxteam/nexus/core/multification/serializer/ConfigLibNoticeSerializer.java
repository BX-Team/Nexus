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

public class ConfigLibNoticeSerializer implements Serializer<Notice, Map<String, Object>> {
    private final NoticeResolverRegistry noticeRegistry;

    public ConfigLibNoticeSerializer(NoticeResolverRegistry noticeRegistry) {
        this.noticeRegistry = noticeRegistry;
    }

    @Override
    public Map<String, Object> serialize(Notice notice) {
        Map<String, Object> data = new HashMap<>();

        if (trySerializeChatBeautiful(data, notice)) {
            return data;
        }

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
    public Notice deserialize(Map<String, Object> data) {
        Builder builder = Notice.builder();

        if (data.size() == 1 && data.containsKey("value")) {
            Object value = data.get("value");

            if (value instanceof String stringValue) {
                List<String> messages = Collections.singletonList(stringValue);
                builder.withPart(NoticeKey.CHAT, new ChatContent(messages));
            } else if (value instanceof List<?> listValue) {
                List<String> messages = new ArrayList<>();
                for (Object item : listValue) {
                    if (item instanceof String str) {
                        messages.add(str);
                    }
                }
                builder.withPart(NoticeKey.CHAT, new ChatContent(messages));
            }

            return builder.build();
        }

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
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

        return builder.build();
    }

    private <T extends NoticeContent> void withPart(Builder builder, NoticeDeserializeResult<T> noticeResult) {
        builder.withPart(noticeResult.noticeKey(), noticeResult.content());
    }

    private static boolean trySerializeChatBeautiful(Map<String, Object> data, Notice notice) {
        List<NoticePart<?>> parts = notice.parts();

        if (parts.size() != 1) {
            return false;
        }

        NoticePart<?> part = parts.get(0);

        if (part.noticeKey() != NoticeKey.CHAT) {
            return false;
        }

        ChatContent chat = (ChatContent) part.content();
        List<String> messages = chat.contents();

        if (messages.size() == 1) {
            data.put("value", messages.get(0));
            return true;
        }

        data.put("value", messages);
        return true;
    }
}
