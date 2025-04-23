package com.example.dawasyu.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // common
    INVALID_INPUT_VALUE(400, "Bad Request", "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed", "C002", "Method Not Allowed"),
    ENTITY_NOT_FOUND(400, "Bad Request", "C003", "Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "Server Error", "C004", "Internal Server Error"),
    INVALID_TYPE_VALUE(400, "Bad Request", "C005", "Invalid Type Value"),

    // User
    EMAIL_DUPLICATION(400, "Bad Request", "U001", "Email is Duplicated"),
    NICKNAME_DUPLICATION(400, "Bad Request", "U002", "NickName is Duplicated"),
    USER_NOT_FOUND(404, "Not Found", "U003", "User Not Found"),
    EMPTY_EMAIL_OR_NICKNAME(400, "Bad Request", "U004", "Email or Nickname is Empty"),
    PASSWORD_NOT_MATCHED(400, "Bad Request", "U005", "Password do not match"),
    SAME_NICKNAME(400, "Bad Request", "U006", "Cannot update to the same nickname."),
    UNAUTHORIZED_USER(401, "Unauthorized", "U007", "Login is required."),

    // Store

    // Menu

    // Order

    // Review
    REVIEW_NOT_FOUND(404,"Not Found","R001","Can Not Found Review");

    // Category

    private final int status;
    private final String error;
    private final String code;
    private final String message;

}
