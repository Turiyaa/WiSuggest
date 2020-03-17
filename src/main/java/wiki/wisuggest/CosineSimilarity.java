package wiki.wisuggest;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import wiki.wisuggest.HT.Node;

public class CosineSimilarity {
	
	private String wiSuggestLink;

	public void calculateTFIDF(HT userWiki, List<HT> hashWikiList) {
		String term;
		int numOfTermDocs = 0;
		double tf;
		double idf;
		double tf_idf;

		for (int i = 0; i < userWiki.getTable().length; i++) {
			for (Node p = userWiki.getTable()[i]; p != null; p = p.next) { 
				term = p.key;
				for (HT hashWiki : hashWikiList) {
					TFIDF tf_idfObj = new TFIDF();
					if (hashWiki.get(p.key) > 0) {
						numOfTermDocs = numOfTermDocs(hashWikiList, term);
						tf = (double) hashWiki.get(term) / hashWiki.getSize();
						idf = Math.log10((double) hashWikiList.size() / numOfTermDocs);
						tf_idf = tf * idf;
						tf_idfObj.setWord(term);
						tf_idfObj.setTf(tf);
						tf_idfObj.setIdf(idf);
						tf_idfObj.setTf_idf(tf_idf);
						hashWiki.getTfidfList().add(tf_idfObj);
					} else {
						tf_idfObj.setWord(term);
						tf_idfObj.setTf(0);
						tf_idfObj.setIdf(0);
						tf_idfObj.setTf_idf(0);
						hashWiki.getTfidfList().add(tf_idfObj);
					}
				}
			}
		}

		dotProduct(userWiki, hashWikiList);

		// remove userPage after similarity calculation
		hashWikiList.remove(0);

		HT similarPage = hashWikiList.stream().max(Comparator.comparing(HT::getSimilarity))
				.orElseThrow(NoSuchElementException::new);
		wiSuggestLink = similarPage.getLink();
	}

	private void dotProduct(HT userWiki, List<HT> hashWikiList) {
		for (HT hashWiki : hashWikiList) {
			double product = 0;
			double similarity = 0;
			double userWikiMag = 0;
			double wikiMag = 0;
			for (int i = 0; i < userWiki.getTfidfList().size(); i++) {
				double userWikitf_idf = userWiki.getTfidfList().get(i).getTf_idf();
				double wikitf_idf = hashWiki.getTfidfList().get(i).getTf_idf();

				product += (userWikitf_idf * wikitf_idf);
				userWikiMag += Math.pow(userWikitf_idf, 2);
				wikiMag += Math.pow(wikitf_idf, 2);
			}
			similarity = product / (Math.sqrt(userWikiMag) * Math.sqrt(wikiMag));
			hashWiki.setSimilarity(similarity);
		}
	}

	private int numOfTermDocs(List<HT> hashWikiList, String k) {
		int numOfTermsDocs = 0;
		for (HT hashWiki : hashWikiList) {
			if (hashWiki.get(k) != 0)
				numOfTermsDocs++;
		}
		return numOfTermsDocs;
	}
	
	public String getWiSuggestLink() {
		return wiSuggestLink;
	}
}
