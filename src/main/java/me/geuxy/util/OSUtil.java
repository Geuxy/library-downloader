package me.geuxy.util;

import lombok.RequiredArgsConstructor;
import me.geuxy.Main;

import java.io.File;

@RequiredArgsConstructor
public enum OSUtil implements ProjectInfo {

    WINDOWS("AppData" + File.separator + "Roaming" + File.separator + "." + name + File.separator),
    MAC("Library" + File.separator + "Application Support" + File.separator +  name + File.separator),
    LINUX("." + name + File.separator);

    private final String directory;

    public static OSUtil getOS() {
        String osName = System.getProperty("os.name").toLowerCase();

        return osName.contains("windows") ? WINDOWS : osName.contains("mac") ? MAC : LINUX;
    }

    public String getDirectory() {
        return System.getProperty("user.home") + File.separator + directory.replace("minecraft", "funnylibs");
    }

}
