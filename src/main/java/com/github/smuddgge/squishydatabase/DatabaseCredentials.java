package com.github.smuddgge.squishydatabase;

import java.io.File;

/**
 * Represents a databases credentials.
 * Contains values to connected to a database.
 */
public class DatabaseCredentials {

    private String path;
    private File file;

    /**
     * Used to set the path.
     *
     * @param path The path to set.
     * @return This instance of the credentials.
     */
    public DatabaseCredentials setPath(String path) {
        this.path = path;

        return this;
    }

    /**
     * Used to set the file.
     *
     * @param file The file to set.
     * @return This instance of the credentials.
     */
    public DatabaseCredentials setFile(File file) {
        this.file = file;

        return this;
    }

    /**
     * Used to get the path.
     *
     * @return The requested path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Used to get the file.
     *
     * @return The requested file.
     */
    public File getFile() {
        return this.file;
    }
}
