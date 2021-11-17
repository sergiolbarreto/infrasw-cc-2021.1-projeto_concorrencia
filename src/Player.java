import ui.AddSongWindow;
import ui.PlayerWindow;
import java.awt.event.*;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import ui.PlayerWindow;

public class Player {
    String[][] queueArray = {};
    PlayerWindow window;
    AddSongWindow addSongWindow;

    public Player() {

        // ActionListener que vai ativar funçao de adicionar uma musica
        ActionListener adicionarMusica = e -> adicionarMusica();

        ActionListener buttonListenerPause = e -> {

        };
        ActionListener buttonListenerAddSongOk = e -> {

        };
        ActionListener buttonListenerAddSong = e -> {

        };
        MouseListener scrubberListenerClick = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };

        MouseMotionListener scrubberListenerMotion = new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        };
        this.window = new PlayerWindow(
                null,
                null,
                adicionarMusica,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "Player",
                queueArray
        );
    }


    void adicionarMusicaNaLista(String[] novaMusica) {
        // pega a musica e adiciona na lista de reprodução, recebe
        // a novaMusica da função adicionarMusica

        String[][] listaDeFilas = new String[queueArray.length + 1][7];
        System.arraycopy(queueArray, 0, listaDeFilas, 0, queueArray.length);
        listaDeFilas[queueArray.length] = novaMusica;
        window.updateQueueList(listaDeFilas);
        this.queueArray = listaDeFilas;
    }

    int musicaAtualId = 0;

    void adicionarMusica() {
        ActionListener addSongOkAction = e -> {
            String[] musica = addSongWindow.getSong();
            adicionarMusicaNaLista(musica);
        };
        WindowListener listener = this.window.getAddSongWindowListener();
        AddSongWindow newWindow = new AddSongWindow(Integer.toString(this.musicaAtualId), addSongOkAction, listener);
        this.addSongWindow = newWindow;
        newWindow.start();
        this.musicaAtualId++;
        newWindow.start();
    }
}

