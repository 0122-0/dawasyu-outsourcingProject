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
    USER_NOT_FOUND(404, "Not Found", "U002", "User Not Found"),
    PASSWORD_NOT_MATCHED(400, "Bad Request", "U003", "Password Do Not Match"),
    UNAUTHORIZED_USER(401, "Unauthorized", "U004", "Unauthorized User"),

    // Store
    STORE_NOT_FOUND(404,"Not Found","S001","Can Not Found Store"),
    ALREADY_CLOSED_STORE(400, "Bad Request", "S002", "Already Closed Store"),
    NO_AUTHORIZATION(403, "Forbidden", "S003", "No Authorization for This Store"),
    STORE_LIMIT_MAX(404,"Not Found","S004","Only 3 stores are allowed per account."),
    DUPLICATE_STORE_NAME(409, "Conflict", "S005", "Store name already exists."),
    DUPLICATE_STORE_NUMBER(409, "Conflict", "S006", "Store phone number already exists."),
    DUPLICATE_STORE_BIZNO(409, "Conflict", "S007", "Business number already exists."),
    STORE_CREATE_CATEGORY_NOT_FOUND(400, "Bad Request", "S008", "At least one category is required to create a store."),

    // Menu
    MENU_NOT_FOUND(404,"Not Found","M001","Can Not Found Menu"),
    MENU_ALREADY_DELETED(409, "CONFLICT","M002","Menu Already Deleted"),

    // Order
    ORDER_NOT_FOUND(404,"Not Found","O001","Can Not Found Order"),
    DIFFERENT_STATUS(400, "Bad Request", "O002", "Order status does not match"),
    // Review
    REVIEW_NOT_FOUND(404,"Not Found","R001","Can Not Found Review"),
    NOT_ORDER_OWNER(400, "Bad Request", "R002", "You are not the owner of this order."),
    ALREADY_REVIEWED(400, "Bad Request", "R003", "A review has already been written for this order."),
    USER_NOT_MATCHED(400, "Bad Request", "R004", "The user is not authorized to modify this review"),
    ORDER_NOT_COMPLETED(400, "Bad Request", "R005", "The order has not been completed yet and cannot be reviewed."),

    // Category
    CATEGORY_NOT_FOUND(404, "Not Found", "C001", "Category not found."),
    DUPLICATE_CATEGORY_NAME(409, "Conflict", "C002", "Category name already exists.");

    private final int status;
    private final String error;
    private final String code;
    private final String message;


}
