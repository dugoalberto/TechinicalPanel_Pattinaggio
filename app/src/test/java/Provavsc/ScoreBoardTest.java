package Provavsc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("ex1")
public class ScoreBoardTest {

  @SuppressWarnings("unchecked")
  private Optional<Float>[] init() {
    var res = new Optional[Nation.values().length];
    Arrays.fill(res, Optional.empty());
    return res;
  }

  Judge judge(int idx) {
    return new Judge(null, idx, null, null);
  }

  @Test
  public void components() {
    ScoreRow cp = new ComponentScoreRow(init());
    cp = cp.add(new SkatingSkills(judge(1), 1.0f));
    cp = cp.add(new SkatingSkills(judge(2), 1.0f));
    cp = cp.add(new SkatingSkills(judge(3), 1.0f));
    cp = cp.add(new SkatingSkills(judge(4), 1.0f));
    cp = cp.add(new SkatingSkills(judge(5), 1.0f));
    cp = cp.add(new SkatingSkills(judge(6), 1.0f));
    cp = cp.add(new SkatingSkills(judge(7), 1.0f));
    cp = cp.add(new SkatingSkills(judge(8), 1.0f));
    cp = cp.add(new SkatingSkills(judge(9), 1.0f));
    assertEquals(2, cp.partial());
  }

  @Test
  public void trimmedAverage() {
    assertEquals(2.71f, ScoreBoard.trimmedAverage(of(3f, 1f, 3f, 3f, 2f, 2f, 3f, 3f, 3f)), 0.01f);
    assertEquals(
        9.71f,
        ScoreBoard.trimmedAverage(
            of(9.75f, 9.50f, 9.75f, 10.00f, 9.50f, 9.75f, 9.50f, 10.00f, 9.75f)),
        0.01f);
    assertEquals(
        -1.71f, ScoreBoard.trimmedAverage(of(-1f, -2f, -2f, -2f, -2f, -1f, -2f, -2f, -1f)), 0.01f);
  }

  @SuppressWarnings("unchecked")
  private Optional<Float>[] of(float... values) {
    Optional<Float> res[] = new Optional[values.length];
    for (int i = 0; i < values.length; i++) res[i] = Optional.of(values[i]);
    return res;
  }
}
