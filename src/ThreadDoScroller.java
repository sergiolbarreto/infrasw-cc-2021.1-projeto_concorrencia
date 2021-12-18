import ui.PlayerWindow;

public class ThreadDoScroller extends Thread{

    int partida;
    int tamanho;
    PlayerWindow window;
    int songIndex;
    int queueSize;
    String [][] QueueArray;

    public ThreadDoScroller(PlayerWindow window, int partida, int tamanho, int songIndex, int queueSize, String[][] QueueArray) {
        this.partida = partida;
        this.tamanho = tamanho;
        this.window = window;
        this.songIndex = songIndex;
        this.queueSize = queueSize;
        this.QueueArray = QueueArray;
    }
    @Override
    public void run() {
        boolean pausa = false;
        for (int i = partida; i < tamanho; i++) {
            window.updateMiniplayer(true, true, false, i, tamanho, songIndex, queueSize);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                i = tamanho;
                pausa = true;
            }
        }
        if (!pausa) {
            window.updateMiniplayer(true, false, false, tamanho, tamanho, songIndex, queueSize
            );
        }
    }
}