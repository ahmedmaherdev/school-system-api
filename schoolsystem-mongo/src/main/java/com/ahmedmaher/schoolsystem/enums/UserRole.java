package com.ahmedmaher.schoolsystem.enums;

public enum UserRole {
    STUDENT(Names.STUDENT),
    ADMIN(Names.ADMIN),
    SUPERADMIN(Names.SUPERADMIN);

    public class Names {
        public static final String STUDENT = "STUDENT";
        public static final String ADMIN = "ADMIN";
        public static final String SUPERADMIN = "SUPERADMIN";

    }
    private String label;
    private UserRole(String label){
        this.label = label;
    }
    @Override
    public String toString() {
        return this.label;
    }
}