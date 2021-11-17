import ui.AddSongWindow;
import ui.PlayerWindow;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import ui.PlayerWindow;

public class Player {
    PlayerWindow window;
    AddSongWindow addSongWindow;
    public Player() {

        int qtdMaxMus =  100;
        String[][] arrQueue = new String[qtdMaxMus][7];
        String songID = "1"; //testando só

        ActionListener buttonListenerPlayNow = e -> {

        };
        ActionListener buttonListenerRemove = e -> {

        };
        ActionListener buttonListenerPause = e -> {

        };
        ActionListener buttonListenerAddSongOk = e -> {
            arrQueue[0] = addSongWindow.getSong();
        };
        ActionListener buttonListenerAddSong = e -> {
            addSongWindow = new AddSongWindow(songID, buttonListenerAddSongOk, window.getAddSongWindowListener());
        };
        MouseListener scrubberListenerClick = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        };

        MouseMotionListener scrubberListenerMotion = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        };
        window = new PlayerWindow(buttonListenerPlayNow, buttonListenerRemove, buttonListenerAddSong, buttonListenerPause, null, null, null, null, null, scrubberListenerClick, scrubberListenerMotion, "Player", arrQueue);
        new PlayerWindow(null, null, null, null, null, null, null, null, null, null, null, "Player", null);
    }
}

