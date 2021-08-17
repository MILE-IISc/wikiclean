/**
 * WikiClean: A Java Wikipedia markup to plain text converter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wikiclean;

import java.io.File;
import java.io.PrintWriter;
import java.text.BreakIterator;
import java.util.Locale;

import org.iisc.mile.tts.engine.G2P_Tel;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;
import org.wikiclean.languages.Telugu;

/**
 * Simple program for dumping English Wikipedia articles to plain text, one
 * sentence per line.
 */
public class DumpTeWikiToParsedSentences {
    private DumpTeWikiToParsedSentences() {
    }

    private static final class Args {
        @Option(name = "-input", metaVar = "[path]", required = true, usage = "input path")
        File input;

        @Option(name = "-output", metaVar = "[path]", required = true, usage = "output path")
        String output;
    }

    public static void main(String[] argv) throws Exception {
        final Args args = new Args();
        CmdLineParser parser = new CmdLineParser(args, ParserProperties.defaults().withUsageWidth(100));

        try {
            parser.parseArgument(argv);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            System.exit(-1);
        }

        final WikiClean cleaner = new WikiClean.Builder().withLanguage(new Telugu()).withTitle(false).withFooter(false)
                .build();

        PrintWriter writer = new PrintWriter(args.output, "UTF-8");
        WikipediaArticlesDump wikipedia = new WikipediaArticlesDump(args.input);
        BreakIterator iterator = BreakIterator.getSentenceInstance(new Locale("te"));

        wikipedia.stream().filter(page -> !page.contains("<ns>") || page.contains("<ns>0</ns>")).forEach(page -> {
            String s = cleaner.clean(page);
            if (s.startsWith("#REDIRECT"))
                return;

            String title = cleaner.getTitle(page).replaceAll("\\n+", " ");
            int cnt = 0;
            for (String line : s.split("[\r\n]+")) {
                line = G2P_Tel.getAbreviationExpandedText(line);
                iterator.setText(line);
                int start = iterator.first();
                String prevSentence = "";
                for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
                    String sentence = line.substring(start, end).trim();
                    if (!sentence.isEmpty()) {
                        if (!prevSentence.isEmpty()) {
                            sentence = prevSentence + " " + sentence;
                            prevSentence = "";
                        }
                        if (sentence.split("\\s+").length <= 5) {
                            prevSentence = sentence;
                        } else {
                            writer.print(String.format("%s.%04d\t%s\n", title, cnt, sentence));
                            System.out.print(String.format("%s.%04d\t%s\n", title, cnt, sentence));
                            cnt++;
                        }
                    }
                }
            }
        });

        writer.close();
    }
}
