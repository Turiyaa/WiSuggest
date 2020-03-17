package wiki.wisuggest.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import wiki.wisuggest.CosineSimilarity;
import wiki.wisuggest.HT;

public class WiService {
	
	private CosineSimilarity similarity;
	private List<HT> hashWikiList;
	private Path path;
	private List<String> wikiPageList;

	public String processArticles(String userWiki) throws IOException {
		similarity = new CosineSimilarity();
		hashWikiList = new ArrayList<HT>();
		String wiSuggestUrl = "";
		path = Paths.get("links/articles.txt");
		wikiPageList = Files.readAllLines(path);
		wikiPageList.add(0, userWiki);

		for (String page : wikiPageList) {
			Document wikiPage = Jsoup.parseBodyFragment(Jsoup.connect(page).get().toString());
			String allText = wikiPage.text().toLowerCase();
			allText = allText.replaceAll("\\p{Punct}", "");
			String[] wordList = allText.split(" ");
			HT hashWikiPage = new HT();
			for (String word : wordList) {
				hashWikiPage.addOne(word);
			}
			hashWikiPage.setLink(page);
			hashWikiList.add(hashWikiPage);
		}
		similarity.calculateTFIDF(hashWikiList.get(0), hashWikiList);
		wiSuggestUrl = similarity.getWiSuggestLink();
		
		return wiSuggestUrl;
	}
}
