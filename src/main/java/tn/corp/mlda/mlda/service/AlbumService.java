package tn.corp.mlda.mlda.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import tn.corp.mlda.mlda.model.Album;
import tn.corp.mlda.mlda.model.Category;
import tn.corp.mlda.mlda.model.Singer;
import tn.corp.mlda.mlda.model.Song;

public class AlbumService {
	private List<Album> allAlbums = new ArrayList<>();

	public AlbumService() {

	}

	public void loadData() {
		// ," ",""
		Category pop = new Category("Pop",
				"Pop is a genre of popular music that originated in its modern form during the mid-1950s in the United States and the United Kingdom");
		Category funk = new Category("Funk",
				"Funk music is an amalgam of soul music, soul jazz, R&B, and Afro-Cuban rhythms absorbed and reconstituted in New Orleans");
		Category rnb = new Category("R&B",
				"Rhythm and blues, often abbreviated as R&B or R'n'B, is a genre of popular music that originated in African-American communities in the 1940s");
		Category classical = new Category("Classical",
				"Classical music is a term that most commonly refers to the formal musical tradition of the Western world, considered to be distinct from Western folk music");

		Singer aznavour = new Singer("Charles Aznavour", "image.png");
		Singer fouler = new Singer("Adam Fouler", "image.png");

		allAlbums.add(new Album("Fool", "Single Album", "image.png", 2003, fouler,
				new Song[] { new Song("Dot be Fool", pop, 360), new Song("Don't Fool Me", pop, 360),
						new Song("Fool And Tamia", rnb, 360), }));
		allAlbums.add(new Album("Non, je n'ai rien oublié", "Single Album", "image.png", 1971, aznavour,
				new Song[] { new Song("Non, je n'ai rien oublié", pop, 360), new Song("Un par un", pop, 360),
						new Song("Partir", pop, 360) }));
		allAlbums.add(new Album("Plus bleu...", "Single Album", "image.png", 1997, aznavour,
				new Song[] { new Song("Plus bleu que tes yeux", pop, 360), new Song("Les enfants", pop, 360),
						new Song("Ce métier", pop, 360) }));
	}

	public List<String> findCategoryNames() {
		return allAlbums.stream() // iterate all albums
				.flatMap(x -> x.getSongs().stream()) // for each album replace it by its songs
				.map(x -> x.getCategory().getName()) // for each song replace it by its name
				.distinct() // remove duplicates
				.sorted() // sort the result
				.collect(Collectors.toList()); // return the result as list
	}

	public List<String> findSingerNames() {
		return allAlbums.stream() // iterate all albums
				.map(x -> x.getSinger().getName()) // for each album replace it by its name
				.distinct() // remove duplicates
				.sorted() // sort the result
				.collect(Collectors.toList()); // return the result as list
	}

	public List<String> findYearsNames() {
		return allAlbums.stream() // iterate all albums
				.map(x -> x.getYear()) // for each album replace it by its name
				.distinct() // remove duplicates
				.sorted() // sort the result
				.map(x -> x.toString()).collect(Collectors.toList()); // return the result as list
	}

	public List<Album> findAlbumsBetter(Integer year, String category, String singer) {
		return allAlbums.stream()
				.filter(album -> (year == null || album.getYear() == year)
						&& (singer == null || singer.equals(album.getSinger().getName()))
						&& (category == null
								|| album.getSongs().stream().anyMatch(s -> category.equals(s.getCategory().getName()))))
				.collect(Collectors.toList());
	}

	public List<Album> findAlbums(Integer year, String category, String singer) {
		List<Album> all = new ArrayList<Album>();
		for (Album album : allAlbums) {
			if (albumMatches(album, year, category, singer)) {
				all.add(album);
			}
		}
		return all;
	}

	private boolean albumMatches(Album album, Integer year, String category, String singer) {
		if (year != null) {
			if (album.getYear() != year) {
				return false;
			}
		}
		if (singer != null) {
			if (album.getSinger() == null || !album.getSinger().getName().equals(singer)) {
				return false;
			}
		}
		if (category != null) {
			boolean catOk = false;
			for (Song s : album.getSongs()) {
				if (s.getCategory() != null && s.getCategory().getName().equals(category)) {
					catOk = true;
					break;
				}
			}
			if (!catOk) {
				return false;
			}
		}
		return true;
	}
}