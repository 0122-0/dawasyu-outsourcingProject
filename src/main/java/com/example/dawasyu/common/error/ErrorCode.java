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
    STORE_NOT_FOUND(404,"Not Found","S001","Can Not Found Store"),

    // Menu
    MENU_NOT_FOUND(404,"Not Found","M001","Can Not Found Menu"),

    // Order
    ORDER_NOT_FOUND(404,"Not Found","O001","Can Not Found Order"),

    // Review
    REVIEW_NOT_FOUND(404,"Not Found","R001","Can Not Found Review"),
    NOT_ORDER_OWNER(400, "Bad Request", "R002", "You are not the owner of this order."),
    ALREADY_REVIEWED(400, "Bad Request", "R003", "A review has already been written for this order."),
    USER_NOT_MATCHED(400, "Bad Request", "R004", "The user is not authorized to modify this review"),
    ORDER_NOT_COMPLETED(400, "Bad Request", "R005", "The order has not been completed yet and cannot be reviewed.");


    // Category

    private final int status;
    private final String error;
    private final String code;
    private final String message;

}
