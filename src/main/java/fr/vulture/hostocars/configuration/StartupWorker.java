package fr.vulture.hostocars.configuration;

import static org.springframework.boot.SpringApplication.exit;

import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SplashScreen;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

/**
 * Worker running at startup to initialize the DB folder, add a tray icon and close the splash screen.
 */
@Slf4j
@Component
public class StartupWorker implements InitializingBean {

    static {
        // Sets the java.awt.headless system property to enable the tray icon
        System.setProperty("java.awt.headless", "false");
    }

    /**
     * Valued autowired constructor.
     *
     * @param applicationContext
     *     The autowired {@link ApplicationContext} component
     */
    @Autowired
    public StartupWorker(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @NonNull
    private final ApplicationContext applicationContext;

    @NonNull
    @Value("${server.address}")
    private String serverAddress;

    @NonNull
    @Value("${spring.application.name}")
    private String applicationName;

    @NonNull
    @Value("${server.port}")
    private String serverPort;

    /**
     * This method is intended to be called before the application context's initialization.
     *
     * @param args
     *     The execution arguments
     */
    public static void initialize(final String[] args) {
        // Initializes the DB folder
        new File("data").mkdir();
    }

    /**
     * {@inheritDoc}
     */
    @Loggable(debug = true)
    @Override
    public void afterPropertiesSet() {
        // Checks if the current system supports the system tray
        if (SystemTray.isSupported()) {
            try {
                // Gets the system tray factory
                final SystemTray tray = SystemTray.getSystemTray();

                // Extracts the URL of the tray icon resource file
                final URL resource = new PathMatchingResourcePatternResolver().getResources("classpath:*icon.png")[0].getURL();

                // Creates the tray icon with the icon
                final TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(resource), this.applicationName);

                // Generates the application URI
                final URI applicationUri = new URI("http://" + this.serverAddress + ':' + this.serverPort);

                // Adds an event listener to open a tab on the default browser with the application URI
                trayIcon.addActionListener(e -> {
                    try {
                        log.debug("Opening a new tab in the default browser with the URL : {}", applicationUri);

                        Desktop.getDesktop().browse(applicationUri);
                    } catch (final IOException exception) {
                        log.error("Failed to open a new tab in the default browser", exception);
                    }
                });

                // Creates a menu item for the tray icon in order to exit the application
                final MenuItem exitItem = new MenuItem("Quitter");

                // Adds an event listener to the exit menu item to remove the tray icon and exit the application
                exitItem.addActionListener(e -> {
                    log.debug("Removing the application tray icon from the system tray");

                    // Removes the tray icon
                    SystemTray.getSystemTray().remove(trayIcon);

                    log.info("Exiting the application");

                    // Exits the application
                    exit(this.applicationContext, () -> 0);
                });

                // Creates a popup menu for the tray icon
                final PopupMenu popupMenu = new PopupMenu();

                // Adds the exit menu item to the popup menu
                popupMenu.add(exitItem);

                // Adds the popup menu to the tray icon
                trayIcon.setPopupMenu(popupMenu);

                // Adds the tray icon to the system tray
                tray.add(trayIcon);
            } catch (final Exception exception) {
                log.error("Tray icon could not to be added", exception);
            }
        } else {
            log.warn("System tray not supported on this platform");
        }

        // Closes the splash screen (if there is one)
        final SplashScreen splashScreen = SplashScreen.getSplashScreen();
        if (Objects.nonNull(splashScreen)) {
            splashScreen.close();
        }
    }

}
