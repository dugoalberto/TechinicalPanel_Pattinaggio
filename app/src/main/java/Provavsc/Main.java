package Provavsc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.checkerframework.checker.units.qual.degrees;

/** Main class */
public class Main {

  public static void main(String[] args) throws InterruptedException {

    AtomicInteger eidx = new AtomicInteger(0);
    List<Element> program =
        Stream.of(
                "4S",
                "4T",
                "3F",
                "FCCoSp4",
                "StSq3",
                "4S+3T",
                "4T+REP",
                "3A+1Lo+3S",
                "3Lo",
                "3Lz",
                "FCSSp4",
                "ChSq1",
                "CCoSp4")
            .map(s -> new Element(eidx.incrementAndGet(), s)).toList();
    // set up the rink
    var rink= new LinkedBlockingQueue<Element>();// TODO LinkedBlockingQueue
    
    // set up the athlete
    Athlete hanyuYusuru = new Athlete(rink, program);
    

    // set up the rink video system and vote collection
    VideoSystem video = new VideoSystem(rink);
    video.screen();
    var votes = new LinkedBlockingQueue<Vote>();// TODO deve essere una LinkedBlockingQueue

    // set up the judges and technical panel
    AtomicInteger jidx = new AtomicInteger(0);
    var judges =
        List.of(Nation.values()).stream()
            .map(n -> new Judge(n, jidx.incrementAndGet(), video.screen(), votes))
            .toList();
    var techPanel = new TechnicalPanel(video.screen(), votes);

    // set up the score board
    var score = new ScoreBoard(votes);

    // set up the 1s printer
    var printer = new Printer(score);

    // start the athlete, the judges, the technical panel and the scoring board
    // TODO: start what needs to be started
    for (Judge judge : judges) {
      judge.start();
    }
    System.out.println("startati i judidi");
    video.start();
    score.start();
    techPanel.start();
    hanyuYusuru.start();
    printer.start();
    
    // check if exercise and scoring is over
    do {
      Thread.sleep(5000);
    } while (!hanyuYusuru.done() && !video.done() && !techPanel.done()); // check if relevant components have stopped);
    
    // TODO: stop components that don't stop themselves
    printer.stop();
    score.stop();
    //Runtime.getRuntime().halt(1);
  }
}
