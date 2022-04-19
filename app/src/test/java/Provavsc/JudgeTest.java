package Provavsc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Test;

public class JudgeTest {
  @Test
  public void judging() throws InterruptedException {
    BlockingQueue<Element> in = new LinkedBlockingQueue<Element>();
    BlockingQueue<Vote> out = new LinkedBlockingQueue<Vote>();
    Judge judge = new Judge(Nation.GER, 1, in, out);
    in.put(new Element(1, "3LOP"));
    in.put(new Element(2, "END"));
    judge.run();
    Set<Vote> result = new HashSet<>();
    out.drainTo(result);

    assertTrue(6 <= result.size());
    assertTrue(
        result.stream()
            .anyMatch(
                r ->
                    r instanceof GradeOfExecution c
                        && c.element().element().equals("3LOP")
                        && c.judge().idx == 1));
    assertTrue(result.stream().anyMatch(r -> r instanceof SkatingSkills c && c.judge().idx == 1));
    assertTrue(result.stream().anyMatch(r -> r instanceof Transitions c && c.judge().idx == 1));
    assertTrue(result.stream().anyMatch(r -> r instanceof Performance c && c.judge().idx == 1));
    assertTrue(result.stream().anyMatch(r -> r instanceof Composition c && c.judge().idx == 1));
    assertTrue(result.stream().anyMatch(r -> r instanceof Interpretation c && c.judge().idx == 1));
    assertFalse(
        result.stream()
            .anyMatch(r -> r instanceof GradeOfExecution c && c.element().element().equals("END")));
  }
}
