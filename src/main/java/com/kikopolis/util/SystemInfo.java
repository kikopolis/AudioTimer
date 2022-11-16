package com.kikopolis.util;

public class SystemInfo {
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private SystemInfo() {
    }
    
    public static boolean isMacOs() {
        String osName = System.getProperty(OS_NAME);
        return osName != null && osName.toLowerCase().contains("mac");
    }
    
    public static boolean isWindows() {
        String osName = System.getProperty(OS_NAME);
        return osName != null && osName.toLowerCase().contains("windows");
    }
    
    public static boolean isLinux() {
        String osName = System.getProperty(OS_NAME);
        return osName != null && osName.toLowerCase().contains("linux");
    }
}
