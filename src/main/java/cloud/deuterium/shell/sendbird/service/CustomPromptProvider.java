package cloud.deuterium.shell.sendbird.service;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * Created by Milan Stojkovic 12-Mar-2022
 */

@Component
public class CustomPromptProvider implements PromptProvider {

    private final SendbirdService sendbirdService;

    public CustomPromptProvider(SendbirdService sendbirdService) {
        this.sendbirdService = sendbirdService;
    }

    @Override
    public AttributedString getPrompt() {

        AttributedString ready = new AttributedString(
                "SB_shell" + "> ",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));

        AttributedString notReady = new AttributedString(
                "shell" + "> ",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));

        return sendbirdService.isReady() ? ready : notReady;
    }
}
