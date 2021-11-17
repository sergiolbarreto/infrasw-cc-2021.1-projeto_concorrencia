import ui.AddSongWindow;
import ui.PlayerWindow;
import java.awt.event.*;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ui.PlayerWindow;

public class Player {
    String[][] queueArray = {};
    PlayerWindow window;
    AddSongWindow addSongWindow;
    String musicaTocandoAtualmenteID;
    public Player() {

        // ActionListener que vai ativar funçao de adicionar uma musica
        ActionListener AdicionarMusica = e -> adicionarMusica();
        // ActionListener que vai ativar funçao de remover uma musica
        ActionListener RemoverMusica = e -> removerMusica();
        // ActionListener que vai ativar funçao de tocar uma musica
        ActionListener TocarMusica = e -> tocarMusica();
        // ActionListener que vai ativar funçao de começar e parar uma musica
        ActionListener PausarTocarMusica = e -> pausarTocarMusica();
        // ActionListener que vai parar uma musica
        ActionListener PararMusica = e -> pararMusica();

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
                TocarMusica,
                RemoverMusica,
                AdicionarMusica,
                PausarTocarMusica,
                PararMusica,
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

    void removerMusica() {
        int id = this.window.getSelectedSongID();
        String[][] listaDeFilas = new String[queueArray.length - 1][7];
        int j = 0;
        for (int i = 0; i < queueArray.length; i++) {
            String[] musica = queueArray[i];
            if (!Objects.equals(musica[6], Integer.toString(id))) {listaDeFilas[j] = musica; j++;}
        }
        this.queueArray = listaDeFilas;
        this.window.updateQueueList(queueArray);
        if (Objects.equals(musicaTocandoAtualmenteID, Integer.toString(id))) {
            ThreadDoScroller.interrupt();
            this.window.resetMiniPlayer();
        }

    }

    // variaveis para controlar o scroller
    int posicaoScroller = 0;
    ThreadDoScroller ThreadDoScroller;
    // condicao se está ou não tocando alguma coisa
    Boolean condicaoMusica = false;

    private void pararMusica() {
        this.window.resetMiniPlayer();
        condicaoMusica = false;
    }

    void tocarMusica() {
        int id = this.window.getSelectedSongID();
        for (String[] musica: queueArray) {
            if (Objects.equals(musica[6], Integer.toString(id))) {
                musicaTocandoAtualmenteID = musica[6];
                this.window.updatePlayingSongInfo(musica[0], musica[1], musica[2]);
                if (ThreadDoScroller != null){
                    ThreadDoScroller.interrupt();
                }
            }

        }
        this.window.enableScrubberArea();
        int tamMus = Integer.parseInt(queueArray[id][5]);
        ThreadDoScroller = new ThreadDoScroller(window, window.getScrubberValue(), tamMus);
    }

    void pausarTocarMusica() {
        if (!condicaoMusica) {
            ThreadDoScroller.start();
            window.updatePlayPauseButton(true);
            condicaoMusica = true;
        } else {
            window.updatePlayPauseButton(false);
            condicaoMusica = false;
        }
    }
    
}

