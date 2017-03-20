package com.example.tarunkukreja.moviesdb;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarunkukreja on 19/03/17.
 */

public class FragmentTopRated extends Fragment {

    private static final String LOG_TAG = FragmentTopRated.class.getSimpleName() ;
   // Uri popularUri = null ;
    Uri topRatedUri = null ;


    GridView gridViewTop;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false) ;
        gridViewTop = (GridView)view.findViewById(R.id.gridView_top) ;

        return view ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // gridViewTop = (GridView)getActivity().findViewById(R.id.gridView_top) ;

        setHasOptionsMenu(true);
    }

    public class MoviesTopRated extends AsyncTask<String, String, List<MoviePage>>{

        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        private String moviesJsonStr = null ;



        @Override
        protected List<MoviePage> doInBackground(String... params) {



            try {
                String baseUrl = "http://api.themoviedb.org/3/movie/top_rated?" ;
              //  final String popular_sort = "popular" ;
               // final String top_rated_sort = "top_rated" ;

                final String MOVIES_API_KEY = "api_key" ;

                //popularUri = Uri.parse(baseUrl).buildUpon()
//                        .appendPath(popular_sort)
//                        .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
//                        .build();
                topRatedUri = Uri.parse(baseUrl).buildUpon()
                        .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
                        .build();

                URL topUrl = new URL(topRatedUri.toString()) ;
                urlConnection = (HttpURLConnection)topUrl.openConnection() ;
                urlConnection.connect();
                Log.d(LOG_TAG, "URL connected") ;

                // URL popularUrl = new URL(popularUri.toString()) ;

//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                if(sharedPreferences.getString(getString(R.string.movies_pref_key), getString(R.string.popular_sort))
//                        .equals(getString(R.string.popular_label))) {
                  //  urlConnection = (HttpURLConnection) popularUrl.openConnection();
                //}

//                else if(sharedPreferences.getString(getString(R.string.movies_pref_key), getString(R.string.top_rated_sort))
//                        .equals(getString(R.string.top_rated_label))){

              //  }


                InputStream inputStream = urlConnection.getInputStream() ;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer() ;
                String line = " ";

                if((line = reader.readLine())!= null){

                    stringBuffer.append(line) ;
                }

                if(stringBuffer.length() == 0){
                    return null;
                }

                moviesJsonStr = stringBuffer.toString() ;

                final String RESULTS = "results" ;
                final String OVERVIEW = "overview" ;
                final String TITLE = "original_title" ;
                final String LANGUAGE = "original_language" ;
                final String IMAGE = "poster_path" ;
                final String ADULT = "adult" ;
                final String RELEASE = "release_date" ;
                final String VOTE = "vote_average" ;

                List<MoviePage> moviePageArrayListTop ;

                try{

                    JSONObject mainObj = new JSONObject(moviesJsonStr) ;
                    JSONArray moviesArray = mainObj.getJSONArray(RESULTS) ;

                    MoviePage moviePage ;

                    moviePageArrayListTop = new ArrayList<MoviePage>() ;

                    for(int i=0; i<moviesArray.length(); i++) {

                        StringBuffer moviePosterUrl = null;
                        moviePosterUrl = new StringBuffer() ;
                        moviePosterUrl.append("https://image.tmdb.org/t/p/w342/");
                        moviePage = new MoviePage();

                        JSONObject subObj = moviesArray.getJSONObject(i);
                        String overview = subObj.getString(OVERVIEW);
                        String lang = subObj.getString(LANGUAGE);
                        String  title = subObj.getString(TITLE);
                        String image = subObj.getString(IMAGE) ;
                        String release_date = subObj.getString(RELEASE) ;
                        boolean adult_or_not = subObj.getBoolean(ADULT) ;
                        float vote = subObj.getInt(VOTE) ;


                        moviePosterUrl.append(image);
                        String posterUrl = moviePosterUrl.toString();


                        moviePage.setTitle(title);
                        moviePage.setLanguage(lang);
                        moviePage.setOverview(overview);
                        moviePage.setImage(posterUrl);
                        moviePage.setAdult(adult_or_not);
                        moviePage.setRelease(release_date);
                        moviePage.setRating(vote);


                        moviePageArrayListTop.add(i, moviePage);
                        Log.d(LOG_TAG, "Insertion" + i + "done");
                    }

                    return moviePageArrayListTop;

                }catch (JSONException e){
                    Log.e(LOG_TAG, "Error in JSON") ;
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error in Malformed") ;
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error");
                e.printStackTrace();
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                    Log.d(LOG_TAG, "url disconnected");

                    if(reader != null){
                        try {
                            reader.close();
                            Log.d(LOG_TAG, "reader closed");
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Some issues with Reader") ;
                            e.printStackTrace();
                        }
                    }
                }



            }


            return null;
        }

        @Override
        protected void onPostExecute(final List<MoviePage> res) {
            Log.d(LOG_TAG, "onPostExecute called") ;
            super.onPostExecute(res);

            MovieAdapter movieAdapter = new MovieAdapter(getActivity(), R.layout.row, res);
            gridViewTop.setAdapter(movieAdapter);

            gridViewTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                    Log.d(LOG_TAG, "Item Clicked") ;

                    //  MoviePage pos = res.get(position) ;
                    MoviePage pos1 = (MoviePage)parent.getItemAtPosition(position) ;
                    String storyline = pos1.getOverview() ;
                    Bundle args = new Bundle() ;
                    args.putString("Overview", storyline);

                    Intent intent = new Intent(getActivity(), DetailActivity.class) ;
                    intent.putExtras(args) ;
                    startActivity(intent);

                }
            });



        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_refresh) {
            Log.d(LOG_TAG, "onRefresh");

            new MoviesTopRated().execute("http://api.themoviedb.org/3/movie/top_rated?api_key=aaffe5cad18de82872dc777a55d9ac51");
            return true;
        }

        else if(item.getItemId() == R.id.action_settings){

            Intent intent = new Intent(getActivity(), SettingsActivity.class) ;
            startActivity(intent);
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }
}
