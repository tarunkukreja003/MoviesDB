package com.example.tarunkukreja.moviesdb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class HomeActivity extends AppCompatActivity implements ActionBar.TabListener{

    ViewPager viewPager;
    TabAdapter tabAdapter ;
    android.support.v7.app.ActionBar actionBar ;
    private static final int NO_OF_TABS = 3 ;

    private static final String LOG_TAG = HomeActivity.class.getSimpleName() ;

    private String[] tabs = {
      "Popular",
      "Now Playing",
      "Upcoming"
    };
//    Uri popularUri = null ;
//    Uri topRatedUri = null ;


//    GridView listView;

    // List<MoviePage> moviePageArrayList ;
//    RecyclerView recyclerView ;
//    RecyclerView.LayoutManager grigLayoutManager ;
//    RecyclerView.Adapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "OnCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

     //   listView = (GridView)findViewById(listView) ;
//        recyclerView = (RecyclerView)findViewById(R.id.listView) ;
//        grigLayoutManager = new GridLayoutManager(MainActivity.this, 2) ;
//        recyclerView.setLayoutManager(grigLayoutManager);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));


        viewPager = (ViewPager)findViewById(R.id.viewPager) ;
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        actionBar = getSupportActionBar() ;
        actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.themoviesdblogo));
        viewPager.setAdapter(tabAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        viewPager.setOffscreenPageLimit(3);

        for(String tab_name : tabs){
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }


    private class TabAdapter extends FragmentStatePagerAdapter{

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null ;
            if(position == 0){
                fragment = new FragmentPopular() ;
            }

            else if(position == 1){
                fragment = new FragmentTopRated() ;
            }

            else if(position == 2){
                fragment = new FragmentUpcoming() ;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return NO_OF_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null ;
            if(position == 0){
                title = tabs[0] ;
            }
            else if(position == 1){
                title = tabs[1] ;
            }

            else if(position == 2){
                title = tabs[2] ;
            }
            return title;
        }
    }





  //  private class MoviesDB extends AsyncTask<String, String, List<MoviePage>> {



//        HttpURLConnection urlConnection = null;
//        BufferedReader reader = null;
//        String moviesJsonStr = null ;
//
//
//
//        @Override
//        protected List<MoviePage> doInBackground(String... params) {
//
//
//
//            try {
//                String baseUrl = "http://api.themoviedb.org/3/movie" ;
//                final String popular_sort = "popular" ;
//                final String top_rated_sort = "top_rated" ;
//
//                final String MOVIES_API_KEY = "api_key" ;
//
//                popularUri = Uri.parse(baseUrl).buildUpon()
//                        .appendPath(popular_sort)
//                        .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
//                        .build();
//                topRatedUri = Uri.parse(baseUrl).buildUpon()
//                        .appendPath(top_rated_sort)
//                        .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
//                        .build();
//                URL popularUrl = new URL(popularUri.toString()) ;
//                URL topUrl = new URL(topRatedUri.toString()) ;
//
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                if(sharedPreferences.getString(getString(R.string.movies_pref_key), getString(R.string.popular_sort))
//                        .equals(getString(R.string.popular_label))) {
//                    urlConnection = (HttpURLConnection) popularUrl.openConnection();
//                }
//
//                else if(sharedPreferences.getString(getString(R.string.movies_pref_key), getString(R.string.top_rated_sort))
//                        .equals(getString(R.string.top_rated_label))){
//                    urlConnection = (HttpURLConnection)topUrl.openConnection() ;
//                }
//
//
//                urlConnection.connect();
//                Log.d(LOG_TAG, "URL connected") ;
//
//                InputStream inputStream = urlConnection.getInputStream() ;
//
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                StringBuffer stringBuffer = new StringBuffer() ;
//                String line = " ";
//
//                if((line = reader.readLine())!= null){
//
//                    stringBuffer.append(line) ;
//                }
//
//                if(stringBuffer.length() == 0){
//                    return null;
//                }
//
//                moviesJsonStr = stringBuffer.toString() ;
//
//                final String RESULTS = "results" ;
//                final String OVERVIEW = "overview" ;
//                final String TITLE = "original_title" ;
//                final String LANGUAGE = "original_language" ;
//                final String IMAGE = "poster_path" ;
//                final String ADULT = "adult" ;
//                final String RELEASE = "release_date" ;
//                final String VOTE = "vote_average" ;
//
//                try{
//
//                    JSONObject mainObj = new JSONObject(moviesJsonStr) ;
//                    JSONArray moviesArray = mainObj.getJSONArray(RESULTS) ;
//
//                    MoviePage moviePage ;
//
//                    moviePageArrayList = new ArrayList<MoviePage>() ;
//
//                    for(int i=0; i<moviesArray.length(); i++) {
//
//                        StringBuffer moviePosterUrl = null;
//                        moviePosterUrl = new StringBuffer() ;
//                        moviePosterUrl.append("https://image.tmdb.org/t/p/w342/");
//                        moviePage = new MoviePage();
//
//                        JSONObject subObj = moviesArray.getJSONObject(i);
//                        String overview = subObj.getString(OVERVIEW);
//                        String lang = subObj.getString(LANGUAGE);
//                        String  title = subObj.getString(TITLE);
//                        String image = subObj.getString(IMAGE) ;
//                        String release_date = subObj.getString(RELEASE) ;
//                        boolean adult_or_not = subObj.getBoolean(ADULT) ;
//                        float vote = subObj.getInt(VOTE) ;
//
//
//                        moviePosterUrl.append(image);
//                        String posterUrl = moviePosterUrl.toString();
//
//
//                        moviePage.setTitle(title);
//                        moviePage.setLanguage(lang);
//                        moviePage.setOverview(overview);
//                        moviePage.setImage(posterUrl);
//                        moviePage.setAdult(adult_or_not);
//                        moviePage.setRelease(release_date);
//                        moviePage.setRating(vote);
//
//
//                        moviePageArrayList.add(i, moviePage);
//                        Log.d(LOG_TAG, "Insertion" + i + "done");
//                    }
//
//                    return moviePageArrayList;
//
//                }catch (JSONException e){
//                    Log.e(LOG_TAG, "Error in JSON") ;
//                    e.printStackTrace();
//                }
//
//
//            } catch (MalformedURLException e) {
//                Log.e(LOG_TAG, "Error in Malformed") ;
//                e.printStackTrace();
//            } catch (IOException e) {
//                Log.e(LOG_TAG, "Error");
//                e.printStackTrace();
//            } finally {
//                if(urlConnection != null){
//                    urlConnection.disconnect();
//                    Log.d(LOG_TAG, "url disconnected");
//
//                    if(reader != null){
//                        try {
//                            reader.close();
//                            Log.d(LOG_TAG, "reader closed");
//                        } catch (IOException e) {
//                            Log.e(LOG_TAG, "Some issues with Reader") ;
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//
//
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(final List<MoviePage> res) {
//            Log.d(LOG_TAG, "onPostExecute called") ;
//            super.onPostExecute(res);
//
//            MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), R.layout.row, res);
//            listView.setAdapter(movieAdapter);
//
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//
//                    Log.d(LOG_TAG, "Item Clicked") ;
//
//                  //  MoviePage pos = res.get(position) ;
//                    MoviePage pos1 = (MoviePage)parent.getItemAtPosition(position) ;
//                    String storyline = pos1.getOverview() ;
//                    Bundle args = new Bundle() ;
//                    args.putString("Overview", storyline);
//
//                    Intent intent = new Intent(HomeActivity.this, DetailActivity.class) ;
//                    intent.putExtras(args) ;
//                    startActivity(intent);
//
//                }
//            });
//
//
//
//        }




//    private List<MoviePage> getMoviesDataJson(String  moviesJsonStr)throws JSONException{
//
//        final String RESULTS = "results" ;
//        final String OVERVIEW = "overview" ;
//        final String TITLE = "original_title" ;
//        final String LANGUAGE = "original_language" ;
//        List<MoviePage> moviePageArrayList  = null;
//
//        try{
//
//            JSONObject mainObj = new JSONObject(moviesJsonStr) ;
//            JSONArray moviesArray = mainObj.getJSONArray(RESULTS) ;
//
//            MoviePage moviePage = new MoviePage();
//
//            moviePageArrayList = new ArrayList<MoviePage>() ;
//
//            for(int i=0; i<moviesArray.length(); i++){
//
//                JSONObject subObj = moviesArray.getJSONObject(i) ;
//                String overview = subObj.getString(OVERVIEW) ;
//                String lang = subObj.getString(LANGUAGE) ;
//                String title = subObj.getString(TITLE) ;
//
//               moviePage.setTitle(title);
//               moviePage.setLanguage(lang);
//               moviePage.setOverview(overview);
//
//              moviePageArrayList.add(moviePage);
//            }
//
//        }catch (JSONException e){
//            Log.e(LOG_TAG, "Error in JSON") ;
//               e.printStackTrace();
//        }
//        return moviePageArrayList ;
//
//    }


//
//    public class MovieAdapter extends ArrayAdapter<MoviePage> {
//
//        int resource ;
//        List<MoviePage> moviePageList ;
//        LayoutInflater inflater ;
//
//        public MovieAdapter(Context context, int resource, List<MoviePage> objects) {
//            super(context, resource, objects);
//            this.resource = resource ;
//            moviePageList = objects ;
//
//            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE) ;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            ViewHolder holder = null ;
//
//            if(convertView == null) {
//
//                holder = new ViewHolder();
//                convertView = inflater.inflate(resource, null);
//
//                holder.title = (TextView) convertView.findViewById(R.id.movie_title);
//                holder.language = (TextView) convertView.findViewById(R.id.lang);
//                holder.overview = (TextView) convertView.findViewById(R.id.overview);
//                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
//                holder.adult = (TextView)convertView.findViewById(R.id.adult) ;
//                holder.ratingBar = (RatingBar)convertView.findViewById(R.id.rating);
//                holder.releaseDate = (TextView)convertView.findViewById(R.id.release) ;
//
//                convertView.setTag(holder);
//            }
//            else {
//                holder = (ViewHolder)convertView.getTag() ;
//            }
//
//            holder.title.setText("Title: " + moviePageList.get(position).getTitle());
//            holder.language.setText("Language: " + moviePageList.get(position).getLanguage());
//            holder.overview.setText("Overview: " + moviePageList.get(position).getOverview());
//            holder.releaseDate.setText("Release Date" + moviePageList.get(position).getRelease());
//             if(moviePageList.get(position).isAdult()){
//                 holder.adult.setText("Adult: " + "A");
//             }
//            else{
//                 holder.adult.setText("Adult: " + "U/A");
//             }
//
//         // holder.imageView.setText("Image url: " + moviePageList.get(position).getImage());
//            String url = moviePageList.get(position).getImage();
//
//            Picasso.with(MainActivity.this).load(url)
//                   .into(holder.imageView);
//
//            float rating = moviePageList.get(position).getRating() ;
//            holder.ratingBar.setRating(rating/2);
//
//            return convertView;
//        }
//
//        class ViewHolder{
//
//            private TextView title ;
//            private TextView overview ;
//            private TextView language ;
//            private ImageView imageView ;
//            private RatingBar ratingBar ;
//            private TextView releaseDate ;
//            private TextView adult ;
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true ;
//   }
////
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.action_refresh){
//            Log.d(LOG_TAG, "onRefresh") ;
//
//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            Log.d(LOG_TAG, String.valueOf(sharedPreferences.getString(getString(R.string.movies_pref_key), getString(R.string.popular_label)))) ;
//            Log.d(LOG_TAG, String.valueOf(sharedPreferences.getString(getString(R.string.movies_pref_key), getString(R.string.top_rated_label)))) ;
//            if(sharedPreferences.getString(getString(R.string.movies_pref_key), getString(R.string.popular_sort))
//                    .equals(getString(R.string.popular_label))) {
//
//
//            }
//
//            else{
//                Log.d(LOG_TAG, "Else called") ;
//            }
//            return true ;
//
//
//        }
//
//        else if(item.getItemId() == R.id.action_settings){
//
//            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class) ;
//            startActivity(intent);
//            return true ;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
