package org.example.web;


import lombok.Data;

@Data
public class SimpleResponseOld {
    private String code = "20000";
    private String message = "成功";
    private Object data;

    public static class SimpleResponseBuilder {
        private String code = "20000";
        private String message = "成功";
        private Object data;


        public SimpleResponseBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public SimpleResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }


        public SimpleResponseBuilder withData(Object data) {
            this.data = data;
            return this;
        }

        public SimpleResponseOld build() {
            SimpleResponseOld simpleResponse = new SimpleResponseOld();
            simpleResponse.setCode(code);
            simpleResponse.setMessage(message);
            simpleResponse.setData(data);
            return simpleResponse;
        }
    }

}
