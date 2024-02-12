package me.geuxy;

import me.geuxy.gui.Window;
import me.geuxy.library.LibraryManager;
import me.geuxy.util.OSUtil;

import lombok.Getter;

import java.io.File;

@Getter
public class Main {

    public static final Main INSTANCE = new Main();

    private final LibraryManager libraryManager = new LibraryManager(new File(OSUtil.getOS().getDirectory()));

    public void init() {
        this.libraryManager.getRequiredLibraries();

        new Window();
    }

    public static void main(String[] args) {
        INSTANCE.init();
    }

}
