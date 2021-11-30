package org.wikiclean.languages;

import java.util.List;
import java.util.regex.Pattern;

public class Hindi extends Language {

  public Hindi() {
    super("mr");
  }

  @Override
  protected List<Pattern> footerPatterns() {
    return footerPatterns("सन्दर्भ", "सन्दर्भ", "टिप्पणी", "बाहरी कड़ी", "स्रोत.*");
  }

  private static final String CATEGORY_LINKS1 = "श्रेणियाँ";
  private static final String CATEGORY_LINKS2 = "श्रेणि";
  private static final String CATEGORY_LINKS3 = "वर्ग";

  @Override
  protected List<Pattern> categoryLinkPatterns() {
    return categoryLinkPatterns(English.CATEGORY_LINKS1, CATEGORY_LINKS1, CATEGORY_LINKS2, CATEGORY_LINKS3);
  }

}
