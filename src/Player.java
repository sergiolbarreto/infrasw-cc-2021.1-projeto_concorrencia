import ui.AddSongWindow;
import ui.PlayerWindow;

import java.awt.event.ActionListener;

public class Player {
    public Player() {
        ActionListener buttonListenerPlayNow = e -> {

        };
        ActionListener buttonListenerRemove = e -> {

        };
        ActionListener buttonListernerAddSongOk = e -> {

        };
        ActionListener buttonListenerPause = e -> {

        };
        ActionListener buttonListenerAddSong = e -> {
            AddSongWindow addSongWindow = new AddSongWindow(null, buttonListernerAddSongOk, null);
        };

        PlayerWindow window = new PlayerWindow(buttonListenerPlayNow, buttonListenerRemove, buttonListenerAddSong, buttonListenerPause, null, null, null, null, null, null, null, "Player", null);

    }
}

