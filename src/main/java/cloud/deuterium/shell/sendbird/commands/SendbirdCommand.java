package cloud.deuterium.shell.sendbird.commands;

import cloud.deuterium.shell.sendbird.service.SendbirdService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

/**
 * Created by Milan Stojkovic 12-Mar-2022
 * https://reflectoring.io/spring-shell/
 */

@ShellComponent
public class SendbirdCommand {

    private final SendbirdService service;

    public SendbirdCommand(SendbirdService service) {
        this.service = service;
    }

    @ShellMethod(key = "base-url", value = "Add base Sendbird url")
    public void addBaseUrl(@ShellOption(value = "-b") String baseUrl){
        service.setBaseUrl(baseUrl);
    }

    @ShellMethod(key = "api-token", value = "Add Sendbird Api token")
    public void addToken(@ShellOption(value = "-t") String token){
        service.setApiToken(token);
    }

    @ShellMethod(key = "users", value = "Get all registered Sendbird users")
    public void getUsers(){
        service.getUsers();
    }

    @ShellMethodAvailability("getUserCheck")
    @ShellMethod(key = "user", value = "Get registered Sendbird user by user id")
    public void getUser(@ShellOption(value = "-u") String userId){
        service.getUser(userId);
    }

    // Method name + Availability => getUsersAvailability
    public Availability getUsersAvailability() {
        return service.isReady() ? Availability.available() : Availability.unavailable("Url and Token Must be set first");
    }

    // Called by -> @ShellMethodAvailability("getUserCheck")
    public Availability getUserCheck() {
        return service.isReady() ? Availability.available() : Availability.unavailable("Url and Token Must be set first");
    }
}
