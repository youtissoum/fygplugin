package me.youtissoum;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

/**
 *  A class for automatic-updating of your plugin
 *
 * @author youtissoum
 */
public class Updater {
    private static final int BYTE_SIZE = 1024;

    private final String USER;
    private final String REPO;

    private final Plugin plugin;
    private final File file;
    private final File updateFolder;

    /**
     * Initialize the updater function but with different get parameters
     *
     * @param plugin The plugin running this file
     * @param USER   The repo user who created the github repository
     * @param REPO   The name of the repository
     */
    public Updater(Plugin plugin, File file, String USER, String REPO) {
        this.plugin = plugin;
        this.file = file;
        this.updateFolder = this.plugin.getServer().getUpdateFolderFile();
        this.USER = USER;
        this.REPO = REPO;
    }

    public void update() {
        final File folder = this.updateFolder;

        deleteOldFiles();
        if(!folder.exists()) {
            this.fileIOOrError(folder, folder.mkdir(), true);
        }
        downloadFile();

        final File dFile = new File(folder.getAbsolutePath(), this.file.getName());
        Bukkit.broadcastMessage(this.plugin.getName() + " has been updated, please restart the server");
    }

    /**
     * parses the json that the github api returned
     *
     * @return returns the URL to the direct download of the jar
     */
    private static String parseJSON(String responseBody) {
        JSONObject root = new JSONObject(responseBody);

        String downloadURL = root.getJSONArray("assets").getJSONObject(0).getString("browser_download_url");

        return downloadURL;
    }

    private void downloadFile() {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        HttpURLConnection connection;

        try {
            String url = "https://api.github.com/repos/" + this.USER + "/" + this.REPO + "/releases/latest";
            URL apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();

            // Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            if(status > 299) {
                this.plugin.getLogger().log(Level.SEVERE, "Could not find latest release : " + status);
                this.plugin.getLogger().log(Level.SEVERE, "Check out " + url + " to see what could be the problem");
                connection.disconnect();
                return;
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
            connection.disconnect();
        } catch(IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Invalid URL");
            e.printStackTrace();
            return;
        }

        String downloadURL = parseJSON(responseContent.toString());

        if(!downloadURL.endsWith("fygplugin.jar")) {
            this.plugin.getLogger().log(Level.SEVERE, "wrongly named jar file detected, aborting update process");
            return;
        }

        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            URL fileUrl = new URL(downloadURL);
            System.out.println("Downloading update from : " + downloadURL);
            final int fileLength = fileUrl.openConnection().getContentLength();
            in = new BufferedInputStream(fileUrl.openStream());
            fout = new FileOutputStream(new File(this.updateFolder, file.getName()));

            final byte[] data = new byte[Updater.BYTE_SIZE];
            int count;
            this.plugin.getLogger().info("About to download a new update: " + this.plugin.getDescription().getVersion());

            long downloaded = 0;
            while ((count = in.read(data, 0, Updater.BYTE_SIZE)) != -1) {
                downloaded += count;
                fout.write(data, 0, count);
                final int percent = (int) ((downloaded * 100) / fileLength);
                if (((percent % 10) == 0)) {
                    this.plugin.getLogger().info("Downloading update: " + percent + "% of " + fileLength + " bytes.");
                }
            }
        } catch (Exception ex) {
            this.plugin.getLogger().log(Level.WARNING, "The auto-updater tried to download a new update, but was unsuccessful.", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException ex) {
                this.plugin.getLogger().log(Level.SEVERE, null, ex);
            }
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (final IOException ex) {
                this.plugin.getLogger().log(Level.SEVERE, null, ex);
            }
        }
    }

    private void deleteOldFiles() {
        //Just a quick check to make sure we didn't leave any files from last time...
        File[] list = listFilesOrError(this.updateFolder);
        for (final File xFile : list) {
            if (xFile.getName().endsWith(".zip")) {
                this.fileIOOrError(xFile, xFile.mkdir(), true);
            }
        }
    }

    private File[] listFilesOrError(File folder) {
        File[] contents = folder.listFiles();
        if (contents == null) {
            this.plugin.getLogger().severe("The updater could not access files at: " + this.updateFolder.getAbsolutePath());
            return new File[0];
        } else {
            return contents;
        }
    }

    private void fileIOOrError(File file, boolean result, boolean create) {
        if(!result) {
            this.plugin.getLogger().severe("The updater could not " + (create ? "create" : "delete") + " file at: " + file.getAbsolutePath());
        }
    }
}