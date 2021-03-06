package com.example.tarunkukreja.moviesdb;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   private static final String LOG_TAG = MainActivity.class.getSimpleName() ;
//    Uri popularUri = null ;


//    GridView listView;
//
//    List<MoviePage> moviePageArrayList ;
    private static final int SPLASH_TIME = 2000 ;
//    RecyclerView recyclerView ;
//    RecyclerView.LayoutManager grigLayoutManager ;
//    RecyclerView.Adapter adapter ;
    ProgressBar progressBar ;
    Handler handler ;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "OnCreate called");
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashTheme);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar_splash) ;
        handler = new Handler() ;
      //  progressBar.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "thread in run") ;
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE) ;
                NetworkInfo networkInfo =  connectivityManager.getActiveNetworkInfo() ;
                //  final android.net.NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) ;

                if(networkInfo!=null && networkInfo.isConnected()){

                    Log.d(LOG_TAG, "Network Available") ;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                          //  progressBar.setVisibility(View.GONE);
                            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class) ;
                            startActivity(homeIntent);
                            finish();
                        }
                    }, SPLASH_TIME) ;
                    //progressBar.setVisibility(View.GONE);

                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
               // handler.sendEmptyMessage(0);
            }
        }.start();

//        listView = (GridView)findViewById(listView) ;
//        recyclerView = (RecyclerView)findViewById(R.id.listView) ;
//        grigLayoutManager = new GridLayoutManager(MainActivity.this, 2) ;
//        recyclerView.setLayoutManager(grigLayoutManager);
    }
//
//    public class InternetReciever extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//        }
//    }



//    private class MoviesDB extends AsyncTask<String, String, List<MoviePage>>{
//
//
//
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
//                String basePopularUrl = "http://api.themoviedb.org/3/movie/popular?" ;
//
//                final String MOVIES_API_KEY = "api_key" ;
//
//                popularUri = Uri.parse(basePopularUrl).buildUpon()
//                        .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
//                        .build();
//                URL popularUrl = new URL(popularUri.toString()) ;
//
//                urlConnection = (HttpURLConnection)popularUrl.openConnection() ;
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
//                    MoviePage pos = res.get(position) ;
//                    String storyline = pos.getOverview() ;
//                    Bundle args = new Bundle() ;
//                    args.putString("Overview", storyline);
//
//                    Intent intent = new Intent(MainActivity.this, DetailActivity.class) ;
//                    intent.putExtras(args) ;
//                    startActivity(intent);
//
//                }
//            });
//
//
//
//        }
//    }
//
//
//
////    private List<MoviePage> getMoviesDataJson(String  moviesJsonStr)throws JSONException{
////
////        final String RESULTS = "results" ;
////        final String OVERVIEW = "overview" ;
////        final String TITLE = "original_title" ;
////        final String LANGUAGE = "original_language" ;
////        List<MoviePage> moviePageArrayList  = null;
////
////        try{
////
////            JSONObject mainObj = new JSONObject(moviesJsonStr) ;
////            JSONArray moviesArray = mainObj.getJSONArray(RESULTS) ;
////
////            MoviePage moviePage = new MoviePage();
////
////            moviePageArrayList = new ArrayList<MoviePage>() ;
////
////            for(int i=0; i<moviesArray.length(); i++){
////
////                JSONObject subObj = moviesArray.getJSONObject(i) ;
////                String overview = subObj.getString(OVERVIEW) ;
////                String lang = subObj.getString(LANGUAGE) ;
////                String title = subObj.getString(TITLE) ;
////
////               moviePage.setTitle(title);
////               moviePage.setLanguage(lang);
////               moviePage.setOverview(overview);
////
////              moviePageArrayList.add(moviePage);
////            }
////
////        }catch (JSONException e){
////            Log.e(LOG_TAG, "Error in JSON") ;
////               e.printStackTrace();
////        }
////        return moviePageArrayList ;
////
////    }
//
//
////
////    public class MovieAdapter extends ArrayAdapter<MoviePage> {
////
////        int resource ;
////        List<MoviePage> moviePageList ;
////        LayoutInflater inflater ;
////
////        public MovieAdapter(Context context, int resource, List<MoviePage> objects) {
////            super(context, resource, objects);
////            this.resource = resource ;
////            moviePageList = objects ;
////
////            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE) ;
////        }
////
////        @NonNull
////        @Override
////        public View getView(int position, View convertView, ViewGroup parent) {
////
////            ViewHolder holder = null ;
////
////            if(convertView == null) {
////
////                holder = new ViewHolder();
////                convertView = inflater.inflate(resource, null);
////
////                holder.title = (TextView) convertView.findViewById(R.id.movie_title);
////                holder.language = (TextView) convertView.findViewById(R.id.lang);
////                holder.overview = (TextView) convertView.findViewById(R.id.overview);
////                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
////                holder.adult = (TextView)convertView.findViewById(R.id.adult) ;
////                holder.ratingBar = (RatingBar)convertView.findViewById(R.id.rating);
////                holder.releaseDate = (TextView)convertView.findViewById(R.id.release) ;
////
////                convertView.setTag(holder);
////            }
////            else {
////                holder = (ViewHolder)convertView.getTag() ;
////            }
////
////            holder.title.setText("Title: " + moviePageList.get(position).getTitle());
////            holder.language.setText("Language: " + moviePageList.get(position).getLanguage());
////            holder.overview.setText("Overview: " + moviePageList.get(position).getOverview());
////            holder.releaseDate.setText("Release Date" + moviePageList.get(position).getRelease());
////             if(moviePageList.get(position).isAdult()){
////                 holder.adult.setText("Adult: " + "A");
////             }
////            else{
////                 holder.adult.setText("Adult: " + "U/A");
////             }
////
////         // holder.imageView.setText("Image url: " + moviePageList.get(position).getImage());
////            String url = moviePageList.get(position).getImage();
////
////            Picasso.with(MainActivity.this).load(url)
////                   .into(holder.imageView);
////
////            float rating = moviePageList.get(position).getRating() ;
////            holder.ratingBar.setRating(rating/2);
////
////            return convertView;
////        }
////
////        class ViewHolder{
////
////            private TextView title ;
////            private TextView overview ;
////            private TextView language ;
////            private ImageView imageView ;
////            private RatingBar ratingBar ;
////            private TextView releaseDate ;
////            private TextView adult ;
////        }
////    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true ;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.action_refresh){
//            Log.d(LOG_TAG, "onRefresh") ;

//            return true ;
//
//
//        }
//
//        else if(item.getItemId() == R.id.action_settings){
//
//            Intent intent = new Intent(MainActivity.this, SettingsActivity.class) ;
//            startActivity(intent);
//            return true ;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
