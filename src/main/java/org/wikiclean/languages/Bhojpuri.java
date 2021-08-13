package org.wikiclean.languages;

import java.util.List;
import java.util.regex.Pattern;

public class Bhojpuri extends Language {

  public Bhojpuri() {
    super("bh");
  }

  @Override
  protected List<Pattern> footerPatterns() {
    return footerPatterns("संदर्भ", "सन्दर्भ", "इहो देखल जाय", "बाहरी कड़ी", "स्रोत.*");
  }

  private static final String CATEGORY_LINKS1 = "श्रेणीसभ";
  private static final String CATEGORY_LINKS2 = "श्रेणी";

  @Override
  protected List<Pattern> categoryLinkPatterns() {
    return categoryLinkPatterns(English.CATEGORY_LINKS1, CATEGORY_LINKS1, CATEGORY_LINKS2);
  }

}
