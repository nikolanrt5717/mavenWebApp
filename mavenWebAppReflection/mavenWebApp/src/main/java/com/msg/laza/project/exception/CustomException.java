package com.msg.laza.project.exception;

public abstract class CustomException extends Exception {
    int code;
    String message;

    public CustomException(String message, int code) {
        super("Code: "+code+". "+message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public static class InvalidIdException extends CustomException {

        public InvalidIdException(String id) {
            super("The entered ID:"+id +" is invalid or null",101);
        }

    }

    public static class InvalidNumberOfArgumentsException extends CustomException {

        public InvalidNumberOfArgumentsException() {
            super("Argument or arguments are missing",102);
        }

    }
}
