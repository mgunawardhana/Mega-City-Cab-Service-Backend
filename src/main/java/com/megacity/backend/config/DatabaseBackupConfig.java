package com.megacity.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Configuration class for scheduling PostgreSQL database backups.
 * Creates backup dump files at specified intervals using pg_dump utility.
 * Note: For development, backups are scheduled every 15 seconds; for production,
 * consider using a daily schedule (e.g., cron = "0 0 0 * * *").
 */
@Slf4j
@Configuration
@EnableScheduling
public class DatabaseBackupConfig {

    @Value("${spring.datasource.write.url}")
    private String dbUrl;

    @Value("${spring.datasource.write.username}")
    private String dbUsername;

    @Value("${spring.datasource.write.password}")
    private String dbPassword;

    @Value("${backup.directory:./resources}")
    private String backupDirectory;

    @Value("${backup.retention-days:7}")
    private int retentionDays;

    /**
     * Scheduled task to create database backup every 15 seconds (for development testing).
     * Creates a dump file with timestamp in the specified backup directory.
     * For production, change to a daily schedule (e.g., cron = "0 0 0 * * *").
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduleDatabaseBackup() {
        log.info("Starting database backup scheduling...");
        try {

            File directory = new File(backupDirectory);
            if (!directory.exists() && !directory.mkdirs()) {
                throw new IOException("Failed to create backup directory: " + backupDirectory);
            }

            String dbName = dbUrl.substring(dbUrl.lastIndexOf("/") + 1);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String backupFile = String.format("%s/megacity_backup_%s.sql", backupDirectory, timestamp);

            log.debug("Attempting to create backup file: {}", backupFile);

            ProcessBuilder pb = new ProcessBuilder("pg_dump", "--host", getHostFromUrl(), "--port", getPortFromUrl(), "--username", dbUsername, "--format", "plain", "--verbose", "--file", backupFile, dbName);

            pb.environment().put("PGPASSWORD", dbPassword);
            pb.redirectErrorStream(true);

            Process process = pb.start();
            int exitCode = process.waitFor();

            String output = new String(process.getInputStream().readAllBytes());
            if (exitCode == 0) {
                log.info("Database backup completed successfully: {}", backupFile);
                cleanupOldBackups();
            } else {
                log.error("Database backup failed with exit code: {}. Output: {}", exitCode, output);
            }

        } catch (IOException | InterruptedException e) {
            log.error("Error during database backup: {}", e.getMessage(), e);
        }
    }

    /**
     * Removes backup files older than the retention period.
     */
    private void cleanupOldBackups() {
        try {
            File directory = new File(backupDirectory);
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".sql"));

            if (files != null) {
                long cutoff = System.currentTimeMillis() - (retentionDays * 24 * 60 * 60 * 1000L);

                for (File file : files) {
                    if (file.lastModified() < cutoff) {
                        if (file.delete()) {
                            log.info("Deleted old backup: {}", file.getName());
                        } else {
                            log.warn("Failed to delete old backup: {}", file.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error during backup cleanup: {}", e.getMessage(), e);
        }
    }

    /**
     * Extracts host from database URL.
     *
     * @return host name from the JDBC URL
     */
    private String getHostFromUrl() {
        String withoutPrefix = dbUrl.substring("jdbc:postgresql://".length());
        return withoutPrefix.substring(0, withoutPrefix.indexOf(":"));
    }

    /**
     * Extracts port from database URL.
     *
     * @return port number from the JDBC URL
     */
    private String getPortFromUrl() {
        String withoutPrefix = dbUrl.substring("jdbc:postgresql://".length());
        String afterHost = withoutPrefix.substring(withoutPrefix.indexOf(":") + 1);
        return afterHost.substring(0, afterHost.indexOf("/"));
    }
}