package org.wikiclean.languages;

import java.util.List;
import java.util.regex.Pattern;

public class Telugu extends Language {

  public Telugu() {
    super("te");
  }

  @Override
  protected List<Pattern> footerPatterns() {
    return footerPatterns(".*మూలాలు.*", ".*వనరులు.*", "(బయటి|ఇతర|బాహ్య) (లింకులు|లంకెలు)", "ఇవి\\s*కూడా చూడండి", "గమనికలు", ".*గ్రంథసూచి.*");
  }

  private static final String CATEGORY_LINKS1 = "వర్గం";

  @Override
  protected List<Pattern> categoryLinkPatterns() {
    return categoryLinkPatterns(English.CATEGORY_LINKS1, CATEGORY_LINKS1);
  }

}
