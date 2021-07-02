package org.wikiclean.languages;

import java.util.List;
import java.util.regex.Pattern;

public class Kannada extends Language {

  public Kannada() {
    super("kn");
  }

  @Override
  protected List<Pattern> footerPatterns() {
    return footerPatterns("ಉಲ್ಲೇಖ(ಗಳು)?", "(ಹೊರ(ಗಿನ)?|ಬಾಹ್ಯ)\\s+(ಕೊಂಡಿ|ಸಂಪರ್ಕ)(ಗಳು)?", "ಆಕರಗಳು", "(ಇದನ್ನೂ|ಇವನ್ನೂ|ಕೆಳಗಿನ\\s+ಲೇಖನಗಳನ್ನೂ)?\\s*ನೋಡಿ", "ಹೆಚ್ಚಿಗೆ ಓದಲು");
  }

  private static final String CATEGORY_LINKS1 = "ವರ್ಗ";

  @Override
  protected List<Pattern> categoryLinkPatterns() {
    return categoryLinkPatterns(English.CATEGORY_LINKS1, CATEGORY_LINKS1); // Some pages of KN use same category tag as
  }

}
