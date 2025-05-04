package com.batteryworkshop.repositories;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @brief Interface for exporting JSON data to a document.
 * @details Provides a method to export JSON-formatted strings to a file for persistence and sharing.
 * Implementations should define the storage mechanism for the document.
 * @since 1.0
 */
public interface Export {
    /**
     * Exports the provided JSON string to a document.
     *
     * @param json The JSON string to export.
     */
    default void exportToDocument(String json) {
        String filePath = "exported_report.json"; // File path to save the JSON document
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(json); // Write the JSON content to the file
            System.out.println("JSON has been successfully exported to " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while exporting the JSON document: " + e.getMessage());
        }
    }
}
