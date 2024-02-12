package me.geuxy.library;

import com.google.gson.*;

import me.geuxy.util.FileUtil;
import me.geuxy.util.Logger;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class LibraryManager {

    private final Library[] libraries = getRequiredLibraries();

    @Getter
    private final File directory;

    public LibraryManager(File directory) {
        this.directory = directory;
    }

    public Library[] getRequiredLibraries() {
        try {
            String exampleUrl = "https://raw.githubusercontent.com/Project-Pulsar/Cloud/main/LauncherScriptGen/libraries.json";

            Scanner scanner = new Scanner(new URL(exampleUrl).openStream());

            String json = "";

            while(scanner.hasNextLine()) {
                json += scanner.nextLine();
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            return gson.fromJson(json, Library[].class);

        } catch (IOException ignored) {
        }
        return null;

    }

    public void updateLibraries() {
        this.createDirectory(directory);

        for(int i = 0; i < libraries.length; i++) {
            Library library = libraries[i];

            File jar = new File(directory, library.getName() + ".jar");

            boolean wrongSize = jar.length() != library.getBytes();

            if(!jar.exists()) {
                this.downloadLibrary(library, jar);

            } else {
                if(wrongSize) {
                    if(jar.delete()) {
                        this.downloadLibrary(library, jar);

                    } else {
                        Logger.error("Failed to delete " + library.getName());
                    }

                } else {
                    Logger.info("Found " + library.getName());
                }
            }
        }
    }

    private void createDirectory(File file) {
        if(!file.exists()) {
            if(file.mkdir()) {
                Logger.info("Created new directory: " + file.getPath());

            } else {
                Logger.error("Failed to make directory: " + file.getPath());
            }
        }
    }

    private void downloadLibrary(Library library, File file) {
        if(FileUtil.download(library.getUrl(), file)) {
            System.out.println("Downloaded " + library.getName());

        } else {
            System.err.println("Failed to download " + library.getName());
        }
    }

}
