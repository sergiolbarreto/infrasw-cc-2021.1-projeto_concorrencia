import ui.PlayerWindow;

public class ThreadDoScroller extends Thread{

    int partida;
    int tamanho;
    PlayerWindow window;

    public ThreadDoScroller(PlayerWindow window, int partida, int tamanho) {
        this.partida = partida;
        this.tamanho = tamanho;
        this.window = window;
    }
    @Override
    public void run() {
            for (int i = partida; i < tamanho; i++) {
                window.updateMiniplayer(true, true, false, i, tamanho, 0, 0);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    window.updateMiniplayer(true, false, false, i, tamanho, 0, 0);
                    return;
                }
            }
        }
    }
