package starz.videozin.handlers;

import starz.videozin.entities.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PagingHandler {

    private static int pageSize = 5;

    public static List<Integer> getPageList(List<Movie> movielist) {
        List<Integer> pages = new ArrayList<>();
        int totalpages = (int) Math.ceil((movielist.size() - 1) / pageSize);

        for (int i = 0; i <= totalpages; i++) {
            pages.add(i);
        }
        return pages;
    }

    public static List<Movie> getPagedMovieList(List<Movie> movielist, int currentpage) {
        movielist = movielist.stream()
                .skip((currentpage) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        return movielist;
    }
}
