import ui.AddSongWindow;
import ui.PlayerWindow;
import java.awt.event.*;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Objects;


public class Player {
    String[][] queueArray = {};
    PlayerWindow window;
    AddSongWindow addSongWindow;
    String musicaTocandoAtualmenteID;
    int duracaoDaMusica;
    int queueSize;
    int posicaoMusicaQueueArray;
    boolean chamouNextPrevious = false; /* boolean que será utilizado para determinar o funcionamento do next/previous, que
                                terão auxílio do tocarMusica; quando falso, o tocarMusica funcionará normalmente,
                                como se estivesse querendo tocar a música que selecionei, quando verdadeiro, o tocarMusica
                                funcionará igual a menos que o id da música que irá tocar não será o de alguma selecionada,
                                e sim o da música anterior/próxima à atual*/
    int idNextPreviousMusic;
    boolean reprAleatoria = false;   // booleano que indica se a reprodução aleatória está ativada ou não
    String[][] listaOriginal; // matriz que vai guardar o QueueArray original para trocar entre reprodução aleatoria
                              // e sequencia
    boolean isRepeat = false;

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
        // ActionListener para dar um next na musica
        ActionListener ProximaMusica = e -> proximaMusica();
        // ActionListener para dar um previous na musica
        ActionListener AnteriorMusica = e -> anteriorMusica();
        // ActionListener para ativar reproducao aleatoria
        ActionListener Aleatorizar = e -> aleatorizar();
        // ActionListener para ativar reproducao repetitiva
        ActionListener Repetir = e -> repetir();

        MouseListener scrubberListenerClick = new MouseListener() {
            @Override

            public void mouseClicked(MouseEvent e) {
                // ao invés do clicked foram implementados os mousePressed e mouseReleased, pois dessa forma não há
                // conflito no caso de existir um clicked e outro released funcionando ao mesmo tempo e executando
                // funções similares em partes
            }

            @Override
            public void mousePressed(MouseEvent e) {
                ThreadDoScroller.interrupt();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ThreadDoScroller = new ThreadDoScroller(
                        window,
                        window.getScrubberValue(),
                        duracaoDaMusica,
                        posicaoMusicaQueueArray,
                        queueSize,
                        queueArray
                );
                ThreadDoScroller.start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        };

        MouseMotionListener scrubberListenerMotion = new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                // isso ocorrerá enquanto o usuário estiver arrastando o mouse no slider, será utilizado em conjunto
                // com o mousePressed e mouseReleased
                window.updateMiniplayer(true,true,false,window.getScrubberValue(),duracaoDaMusica, posicaoMusicaQueueArray, queueSize);
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
                ProximaMusica,
                AnteriorMusica,
                null,
                Repetir,
                scrubberListenerClick,
                scrubberListenerMotion,
                "Player",
                queueArray
        );

    }
    void aleatorizar() {
        // metodo para ativar reproducao aleatoria
        listaOriginal = queueArray;

    }

    void repetir() {
        // metodo para ativar reproducao repetitiva
        // ver método proximaMusica() abaixo
        // ATENÇAO!!! o código está implementado de forma incompleta, a função repetir só funciona efetivamente se o
        // usuário ativar o modo repetir e após isso pressionar o botão de next, e a próxima música seria então
        // a mesma que ele está escutando (o player está repetindo)
        isRepeat = !isRepeat;
    }

    void proximaMusica() {
        // coloca a próxima música para ficar setada para tocar
        if (!isRepeat) { // se nao estiver setado para repetir, funcionará normalmente
            chamouNextPrevious = true;
            idNextPreviousMusic = posicaoMusicaQueueArray + 1;
            tocarMusica();
            chamouNextPrevious = false; // para não dar problema se o usuário quiser tocar outra música através do
                                        // tocarMusica normalmente
        }
        else if (isRepeat) { // se estiver setado para repetir, funcionará igual, a menos que o id não será incrementado
            chamouNextPrevious = true;  // dessa forma, permanecerá na mesma música
            idNextPreviousMusic = posicaoMusicaQueueArray;
            tocarMusica();
            chamouNextPrevious = false; // para não dar problema se o usuário quiser tocar outra música através do
                                        // tocarMusica normalmente
        }
    }

    void anteriorMusica(){
        // coloca a música anterior para ficar setada para tocar
        if (posicaoMusicaQueueArray > 0) {
            chamouNextPrevious = true;
            idNextPreviousMusic = posicaoMusicaQueueArray - 1;
            tocarMusica();
            chamouNextPrevious =  false; // para não dar problema se o usuário quiser tocar outra música através do
                                         // tocarMusica normalmente
        }
    }

    void adicionarMusicaNaLista(String[] novaMusica) {
        // pega a musica e adiciona na lista de reprodução, recebe
        // a novaMusica da função adicionarMusica

        String[][] listaDeFilas = new String[queueArray.length + 1][7];
        System.arraycopy(queueArray, 0, listaDeFilas, 0, queueArray.length);
        listaDeFilas[queueArray.length] = novaMusica;
        window.updateQueueList(listaDeFilas);
        this.queueArray = listaDeFilas;
        queueSize++;
        if (ThreadDoScroller != null) { // para atualizar o miniplayer, mais especificamente se o botão de next
                                        // estava desativado e agora deve ficar ativado
            ThreadDoScroller.queueSize = queueSize;
            ThreadDoScroller.QueueArray = queueArray;
        }
        else {
            window.updateMiniplayer(true, false, false, window.getScrubberValue(), duracaoDaMusica, posicaoMusicaQueueArray, queueSize);
        }
    }

    int musicaAtualId = 0;

    void adicionarMusica() {
        // janela de adicionar musica
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
        if (Objects.equals(musicaTocandoAtualmenteID, Integer.toString(id))){
            if (ThreadDoScroller != null) {
                ThreadDoScroller.interrupt();
            }
            this.window.resetMiniPlayer();
        }
        for (int i = 0; i < queueArray.length; i++) {
            String[] musica = queueArray[i];
            if (!Objects.equals(musica[6], Integer.toString(id))) {listaDeFilas[j] = musica; j++;}
        }
        this.queueArray = listaDeFilas;
        this.window.updateQueueList(queueArray);
        queueSize--;
        if (ThreadDoScroller != null) {
            ThreadDoScroller.queueSize = queueSize;
            ThreadDoScroller.QueueArray = queueArray;
        }
    }

    // variaveis para controlar o scroller
    int posicaoScroller = 0;
    ThreadDoScroller ThreadDoScroller;
    // condicao se estar ou não tocando alguma coisa
    Boolean condicaoMusica = false;
    int segundoDaMusica = 0;

    private void pararMusica() {
        this.window.resetMiniPlayer();
        condicaoMusica = false;
    }

    void tocarMusica() {
        if (!chamouNextPrevious) {
            if (ThreadDoScroller != null) {
                ThreadDoScroller.interrupt();
            }
            int id = this.window.getSelectedSongID();
            posicaoMusicaQueueArray = 0; // inteiro que vai ver em qual índice do queueArray a música a ser tocada está
            boolean forExecuta = true; // booleano que decide se o for abaixo irá executar algo efetivamente ou não
            this.window.enableScrubberArea();
            for (String[] musica : queueArray) {
                if (forExecuta) {
                    if (Objects.equals(musica[6], Integer.toString(id))) {
                        this.window.updatePlayingSongInfo(musica[0], musica[1], musica[2]);
                        this.duracaoDaMusica = Integer.parseInt(musica[5]);
                        window.updateMiniplayer(true, false, false, 0, duracaoDaMusica, posicaoMusicaQueueArray, queueSize);
                        forExecuta = false;
                    } else {
                        posicaoMusicaQueueArray++;
                    }
                }
            }

            musicaTocandoAtualmenteID = Integer.toString(id);
        }
        else {
            if (ThreadDoScroller != null) {
                ThreadDoScroller.interrupt();
            }
            posicaoMusicaQueueArray = idNextPreviousMusic;
            String[] musica = queueArray[posicaoMusicaQueueArray];
            this.window.updatePlayingSongInfo(musica[0], musica[1], musica[2]);
            this.duracaoDaMusica = Integer.parseInt(musica[5]);
            this.window.enableScrubberArea();
            window.updateMiniplayer(true, false, false, 0, duracaoDaMusica, posicaoMusicaQueueArray, queueSize);
            musicaTocandoAtualmenteID = Integer.toString(posicaoMusicaQueueArray);
        }

    }

    private void pausarTocarMusica() {
        if (duracaoDaMusica != window.getScrubberValue()) {
            segundoDaMusica = window.getScrubberValue();
        } else {
            condicaoMusica = false;
        }

        if (!condicaoMusica) {
            ThreadDoScroller = new ThreadDoScroller(
                    window,
                    segundoDaMusica,
                    duracaoDaMusica,
                    posicaoMusicaQueueArray,
                    queueSize,
                    queueArray
            );
            ThreadDoScroller.start();
        } else {
            ThreadDoScroller.interrupt();
        }

        condicaoMusica = !condicaoMusica;
        window.updatePlayPauseButton(condicaoMusica);
    }
}
