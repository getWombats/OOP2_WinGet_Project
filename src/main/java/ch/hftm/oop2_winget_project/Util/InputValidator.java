package ch.hftm.oop2_winget_project.Util;

import javafx.scene.control.TextFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator
{
    private static final Pattern INVALID_INPUT_REGEX = Pattern.compile("^[a-zA-Z0-9]+$");

    public static TextFormatter<String> createValidator()
    {
        return new TextFormatter<>(change -> {
            Matcher matcher = INVALID_INPUT_REGEX.matcher(change.getText());
            if (!matcher.find())
            {
                change.setText("");
            }
            return change;
        });
    }
}
