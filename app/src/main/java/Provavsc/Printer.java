package Provavsc;

/** Prints the partial score at regular intervals. */
public class Printer implements Runnable {

  private final ScoreBoard score;
  private boolean stop;
  private Thread printing;
  private float count;

  Printer(ScoreBoard score) {
    this.score = score;
    printing = new Thread(this);
  }

  @Override
  public void run() {
    try {
      while (!stop) {
        // TODO: Print the result. Wait 1 second.
        if (score.result != null){
          System.out.println(ScoreBoard.format(score.result.get().elements(),score.result.get().total()));
        }
        Thread.sleep(1000);
      }
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  /** Starts the printer */
  void start() {
    this.printing.start();
  }

  /** Asks the printer to stop */
  void stop() {
    this.stop = true;
  }
}
