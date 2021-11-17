import ui.AddSongWindow;
import ui.PlayerWindow;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.PriorityQueue;
import java.util.Queue;

public class Player {
    PlayerWindow window;
    AddSongWindow addSongWindow;
    public Player() {

        int qtdMaxMus = 100;
        Queue<String> queueSongID = new PriorityQueue<>();
        String songID = "5"; // modificar
        String[][] arrQueue = new String[qtdMaxMus][7];

        ActionListener buttonListenerPlayNow = e -> {
            window.updatePlayingSongInfo(arrQueue[0][0], arrQueue[0][1], arrQueue[0][2]); // mudar a primeira posição
            window.updatePlayPauseButton(true);
            window.updateMiniplayer(true, true, false, 000000,
                    Integer.parseInt(arrQueue[0][5]), Integer.parseInt(arrQueue[0][6]), 1);
        };
        ActionListener buttonListenerRemove = e -> {

        };
        ActionListener buttonListenerPause = e -> {

        };
        ActionListener buttonListenerAddSongOk = e -> {
            arrQueue[0] = addSongWindow.getSong();
            window.updateQueueList(arrQueue);
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
            public void mouseMoved(MouseEvent e) {
            }
        };
        window = new PlayerWindow(buttonListenerPlayNow, buttonListenerRemove, buttonListenerAddSong, buttonListenerPause, null, null, null, null, null, scrubberListenerClick, scrubberListenerMotion, "Player", arrQueue);
        window.enableScrubberArea();

    }
    private int getSongID(Queue<String> queueSongID){
        queueSongID.add("songID"); // O songID verdadeiramente será pego pelo idx desse item na queue e convertido para String
        return 2;
    }
}

