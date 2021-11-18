import ui.PlayerWindow;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDoScroller extends Thread{

    int partida;
    int tamanho;
    PlayerWindow window;
    Condition condition;
    Boolean tocandoMusica;
    public ThreadDoScroller(PlayerWindow window, int partida, int tamanho, Condition condition, Boolean tocandoMusica) {
        this.partida = partida;
        this.condition = condition;
        this.tamanho = tamanho;
        this.window = window;
        this.tocandoMusica = tocandoMusica;
    }
    @Override
    public void run() {
        int i = 0;
        while (i<tamanho) {
            if(!tocandoMusica){
                try {
                    condition.await();
                } catch (InterruptedException e) {
                }
            }
            else {
                window.updateMiniplayer(true, true, false, i, tamanho, 0, 0);
                try {
                    Thread.sleep(1000);
                    i++;
                } catch (InterruptedException e) {
                    return;
                }

            }
        }

    }
}
