package gb.stoliarenkoas.ru.material03;

import java.util.Objects;

public class Message {

    private String name;
    private String text;
    private Type type;

    enum Type {
        SELF,
        FRIEND
    }

    public Message(String name, String text, Type type) {
        this.name = name;
        this.text = text;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return name.equals(message.name) &&
                type == message.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
