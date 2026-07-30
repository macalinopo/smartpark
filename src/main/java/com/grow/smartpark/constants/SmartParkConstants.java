package com.grow.smartpark.constants;

public class SmartParkConstants {
    // Status
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_ERROR = "ERROR";

    // Generic Codes
    public static final String CODE_SUCCESS = "AC04";
    public static final String CODE_SYSTEM_ERROR = "99";

    // Parking Lot Codes
    public static final String CODE_LOT_DUPLICATE = "PL01";
    public static final String LOT_ALREADY_EXISTS_MSG = "Parking lot already registered.";
    public static final String LOT_NOT_FOUND_MSG = "Parking lot not found.";
    public static final String SUCCESS_LOT_REGISTERED = "Parking lot registered successfully.";

    // Vehicle Codes
    public static final String CODE_VEHICLE_DUPLICATE = "VE01";
    public static final String VEHICLE_ALREADY_EXISTS_MSG = "Vehicle already registered.";
    public static final String VEHICLE_NOT_FOUND_MSG = "Vehicle not found.";
    public static final String SUCCESS_VEHICLE_REGISTERED = "Vehicle registered successfully.";

    public static final String CODE_VEHICLE_ALREADY_CHECKED_IN = "VE02";
    public static final String VEHICLE_ALREADY_CHECKED_IN_MSG = "Vehicle is already checked in.";

    public static final String CODE_VEHICLE_NO_CHECKIN = "VE04";
    public static final String VEHICLE_NO_CHECKIN_MSG = "Cannot check out without check in.";

    public static final String CODE_VEHICLE_ALREADY_CHECKED_OUT = "VE05";
    public static final String VEHICLE_ALREADY_CHECKED_OUT_MSG = "Vehicle already checked out.";

    public static final String SUCCESS_CHECKIN = "Vehicle checked in successfully.";
    public static final String SUCCESS_CHECKOUT = "Vehicle checked out successfully.";
}
