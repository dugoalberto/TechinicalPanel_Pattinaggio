package Provavsc;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/** An Athlete executes its program when start is called. */
class Athlete {

  private final Thread execution;
// an athlete is a thread who puts into a rink(pista di pattinaggio) an element(program)
  Athlete(final BlockingQueue<Element> rink, final List<Element> program) {
    final Random rnd = new Random();
    this.execution =
        new Thread(
            () -> {
              try {
                for (Element e : program) {
                  rink.put(e);
                  Thread.sleep(rnd.nextInt(3000));
                }
                //here add a new element, called end, in this way we "finished" the thread
                rink.put(new Element(program.size() + 1, Element.END));

              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
  }

  /** Call to start the exercise */
  void start() {
    execution.start();
  }

  /** Call to check if the exercise is over */
  boolean done() {
    return !execution.isAlive();
  }
}
