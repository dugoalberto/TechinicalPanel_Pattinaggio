package Provavsc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("ex1")
public class TechnicalPanelTest {

  @Test
  public void valuing() throws InterruptedException {
    BlockingQueue<Element> in = new LinkedBlockingQueue<Element>();
    BlockingQueue<Vote> out = new LinkedBlockingQueue<Vote>();
    TechnicalPanel panel = new TechnicalPanel(in, out);
    in.put(new Element(1, "3LOP"));
    in.put(new Element(2, "END"));
    panel.run();
    Set<Vote> result = new HashSet<>();
    out.drainTo(result);

    assertTrue(1 <= result.size());
    assertTrue(
        result.stream()
            .anyMatch(r -> r instanceof BaseValue c && c.element().element().equals("3LOP")));
    assertFalse(
        result.stream()
            .anyMatch(r -> r instanceof BaseValue c && c.element().element().equals("END")));
  }
}
