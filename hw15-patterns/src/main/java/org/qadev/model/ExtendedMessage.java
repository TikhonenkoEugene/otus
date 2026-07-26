package org.qadev.model;

public class ExtendedMessage implements MessageInterface {
    private final MessageInterface message;

    private final String field11;
    private final String field12;
    private final ObjectForMessage field13;

    private ExtendedMessage(Builder builder) {
        this.message = builder.message;
        this.field11 = builder.field11;
        this.field12 = builder.field12;
        this.field13 = builder.field13;
    }

    @Override
    public long getId() {
        return message.getId();
    }

    @Override
    public String getField1() {
        return message.getField1();
    }

    @Override
    public String getField2() {
        return message.getField2();
    }

    @Override
    public String getField3() {
        return message.getField3();
    }

    @Override
    public String getField4() {
        return message.getField4();
    }

    @Override
    public String getField5() {
        return message.getField5();
    }

    @Override
    public String getField6() {
        return message.getField6();
    }

    @Override
    public String getField7() {
        return message.getField7();
    }

    @Override
    public String getField8() {
        return message.getField8();
    }

    @Override
    public String getField9() {
        return message.getField9();
    }

    @Override
    public String getField10() {
        return message.getField10();
    }

    public String getField11() {
        return field11;
    }

    public String getField12() {
        return field12;
    }

    public ObjectForMessage getField13() {
        return field13;
    }

    @Override
    public String toString() {
        return "ExtendedMessage{" +
                "Message=" + message +
                ", field11='" + field11 + '\'' +
                ", field12='" + field12 + '\'' +
                ", field13=" + field13 +
                '}';
    }

    public static Builder decorationOf(MessageInterface original) {
        return new Builder(original);
    }

    public static class Builder {
        private final MessageInterface message;
        private String field11;
        private String field12;
        private ObjectForMessage field13;

        public Builder(MessageInterface message) {
            this.message = message;
        }

        public Builder field11(String field11) {
            this.field11 = field11;
            return this;
        }

        public Builder field12(String field12) {
            this.field12 = field12;
            return this;
        }

        public Builder field13(ObjectForMessage field13) {
            this.field13 = field13;
            return this;
        }

        public ExtendedMessage build() {
            return new ExtendedMessage(this);
        }
    }
}
