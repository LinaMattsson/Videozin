package starz.videozin.handlers;

import starz.videozin.entities.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PagingHandler {

    public static List<String> getPageList(List<Movie> movielist, int pageSize) {
        List<String> pages = new ArrayList<>();
        int totalpages = (int) Math.ceil((movielist.size() - 1) / pageSize);

        for (int i = 0; i < totalpages + 1; i++) {
            pages.add(Integer.toString(i));
        }
        return pages;
    }

    public static List<Movie> getPagedMovieList(List<Movie> movielist, int currentpage, int pageSize) {
        movielist = movielist.stream()
                .skip(currentpage * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        return movielist;
    }
}
