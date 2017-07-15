package com.example.dattamber.sdhs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.util.Log.d;

/**
 * Created by Dattamber on 17/06/2017.
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHoder>{

    Boolean procesd = false;
    List<Card> list;
    Context contex;
    DatabaseReference ref;
    private int expandedPosition = -1;
    final MediaPlayer mediaPlayer = new MediaPlayer();
    public RecyclerAdapter(List<Card> list, Context context) {
        this.list = list;
        this.contex = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = LayoutInflater.from(contex).inflate(R.layout.card,parent,false);
        MyHoder myHoder = new MyHoder(view);
        return myHoder;
    }

    @Override
    public void onBindViewHolder(final MyHoder holder, final int position) {
        final Context context = holder.imgdown.getContext();
        final Card mylist = list.get(position);
        final int[] mExpandedPosition = {-1};
        final Bitmap[] bittu = new Bitmap[1];
        holder.flag = 0;
        holder.id = mylist.getpid();
        holder.category.setText(mylist.getcategory());
        holder.content.setText(mylist.getcontent());
        holder.title.setText(mylist.gettitle());
        holder.team.setText(mylist.getteam());
        holder.date.setText(mylist.getdate());
        holder.url = mylist.geturl();
        holder.path = mylist.getpath();
        if(mylist.getlikeCount() == null)
        {
            holder.lik.setText("0");
        }
        else
        {
            holder.lik.setText(String.valueOf(mylist.getlikeCount()));
        }
        holder.likes = mylist.getlikeslist();
        holder.thumbnail = mylist.getthumbnail();
        if(mylist.gettitle().equals("Appaji's audio message") || mylist.gettitle().equals("Appaji's video message"))
        {
            holder.content.setText("Jaya guru datta");
            mylist.setcontent("Jaya guru datta");
            Glide.with(context).load(R.drawable.appaji)
                    .thumbnail(0.5f)
                    .fitCenter()
                    .crossFade()
                    .bitmapTransform(new RoundedCornersTransformation(context,16,2))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img);
        }
        else {
            Glide.with(context).load(holder.thumbnail)
                    .thumbnail(0.5f)
                    .fitCenter()
                    .crossFade()
                    .bitmapTransform(new RoundedCornersTransformation(context, 16, 2))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img);
        }
        holder.img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
               // final Bitmap[] bitmap = new Bitmap[1];
                if(mylist.gettitle().equals("Appaji's audio message"))
                {
                    holder.switcher.setDisplayedChild(0);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    Log.d("enra idhi","hehe");
                    StorageReference storage = FirebaseStorage.getInstance().getReference(mylist.geturl());
                    Toast.makeText(view.getContext(),"Loading audio ...",Toast.LENGTH_LONG).show();
                    File local = null;
                    try
                    {
                        local = File.createTempFile("Audio",".mp3");
                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    final File finalLocal = local;
                    storage.getFile(local).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            final MediaPlayer mediaplayer = new MediaPlayer();
                            try
                            {
                                mediaplayer.setDataSource(finalLocal.getAbsolutePath());
                                mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                Log.d("jhjh",finalLocal.getAbsolutePath());
                                mediaplayer.prepare();
                                mediaplayer.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else if (!mylist.gettitle().equals("Appaji's video message")){
                    holder.switcher.setDisplayedChild(0);
                    Intent intent = new Intent(view.getContext(), FullScreenImage.class);
                /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bittu[0].compress(Bitmap.CompressFormat.JPEG,70,stream);
                byte[] byteArray = stream.toByteArray();
                Bitmap compi = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

                intent.putExtra("BitmapImage", bittu[0]);*/
                    intent.putExtra("name", mylist.gettitle());
                    intent.putExtra("url", mylist.getpath());
                    context.startActivity(intent);
                }
                else {
                    holder.switcher.setDisplayedChild(1);
                    holder.content.setText("");
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference(mylist.getfilePath());
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String str = uri.toString();
                            MediaController mediacontroller = new MediaController(view.getContext());
                            mediacontroller.setAnchorView(holder.cvideo);
                            mediacontroller.requestFocus();
                            holder.cvideo.setMediaController(mediacontroller);
                            holder.cvideo.setVideoURI(uri);
                            holder.cvideo.requestFocus();
                            holder.cvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.setLooping(true);
                                    holder.cvideo.start();
                                }
                            });
                        }
                    });
                }
            }
        });
        if(holder.likes != null && holder.likes.containsKey(getUid()))
        {
                holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                d("Loc1 ",holder.lik.getText().toString());
                holder.flag = 1;
        }
        else {
            holder.fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            d("Loc2 ",holder.lik.getText().toString());
            holder.flag = 0;
        }
        holder.fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    if(holder.flag == 0) {
                        final int[] fl = {0};
                        ref = FirebaseDatabase.getInstance().getReference("/postlikes");
                        ref.keepSynced(true);
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild("post" + String.valueOf(mylist.getpid()))) {
                                    ref.runTransaction(new Transaction.Handler() {
                                        @Override
                                        public Transaction.Result doTransaction(MutableData mutableData) {
                                            d("Lol", mutableData.getValue().toString());

                                            if (mutableData.getValue() == null) {
                                                d("LOlll", "lololol");
                                                return Transaction.success(mutableData);
                                            }
                                            Long l = mutableData.child("post" + String.valueOf(mylist.getpid())).child("likeCount").getValue(Long.class);
                                            if (!(mutableData.child("post" + String.valueOf(mylist.getpid())).child("likes").hasChild(getUid()))) {
                                                l = mutableData.child("post" + String.valueOf(mylist.getpid())).child("likeCount").getValue(Long.class) + 1L;
                                                ref.child("post" + String.valueOf(mylist.getpid())).child("likeCount").setValue(l);
                                                ref.child("post" + String.valueOf(mylist.getpid())).child("likes").child(getUid()).setValue(1L);
                                                d("yavva", l.toString());
                                                d("Loc3 ", holder.lik.getText().toString());
                                                mylist.setlikeCount(l);
                                                fl[0] = 1;
                                            } else {
                                                Log.d("Adhenti", "uhhuh");
                                            }
                                            //holder.lik.setText(String.valueOf(mutableData.child("post"+String.valueOf(mylist.getpid())).child("likeCount").getValue(Long.class)));
                                            //
                                            // holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                                            return Transaction.success(mutableData);
                                        }

                                        @Override
                                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                                            if (databaseError != null) {
                                                d("Error :", databaseError.getDetails());
                                            }
                                            if (dataSnapshot != null) {
                                                d("Data  ", dataSnapshot.toString());
                                            }
                                        }
                                    });
                                    holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                                    holder.lik.setText(String.valueOf(mylist.getlikeCount() + 1));
                                } else {
                                    holder.lik.setText(String.valueOf(1));
                                    holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                                    d("Loc5 ", holder.lik.getText().toString());
                                    ref.child("post" + String.valueOf(mylist.getpid())).child("likeCount").setValue(Long.valueOf(1));
                                    ref.child("post" + String.valueOf(mylist.getpid())).child("likes").child(getUid()).setValue(Long.valueOf(1));
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        procesd = true;
                        holder.fav.setClickable(false);
                        holder.flag = 1;
                    }
                }
            });

                /*

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(procesd)
                        {
                            if(dataSnapshot.child("likes").hasChild(getUid()))
                            {
                                ref.child("likes").child(getUid()).removeValue();
                                procesd = false;
                            }
                            else
                            {
                                Long likk = dataSnapshot.child("likeCount").getValue(Long.class);
                                likk = likk + 1 ;
                                ref.child("likeCount").setValue(likk);
                                ref.child("likes").child(getUid()).setValue(Long.valueOf(1));
                                procesd = false;
                            }
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT,mylist.gettitle()+"\n"+ mylist.getteam() + "\n" + mylist.getdate()+"\n" +mylist.getcontent() + "\n\n" + "www.sdhs.in/posts.html");
                    shareIntent.setType("text/*");
                    v.getContext().startActivity(Intent.createChooser(shareIntent, "Share post via:"));
                // Get access to the URI for the bitmap
                /*
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Hey view/download this image");
                final Bitmap[] bits = {null};
                Glide.with(v.getContext())
                        .load(mylist.geturl())
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                bits[0] = resource;
                            }
                        });
                String path = MediaStore.Images.Media.insertImage(v.getContext().getContentResolver(), bits[0], "temp", null);
                Uri screenshotUri = Uri.parse(path);

                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                intent.setType("image/*");
                v.getContext().startActivity(Intent.createChooser(intent, "Share image via..."));
                */
            }
        });
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try{
            if(list.size()==0){
                arr = 0;
            }
            else{
                arr=list.size();
            }
        }catch (Exception e){

        }

        return arr;

    }
    public String getEncodedURL(String urlStr) throws Exception
    {
        URL url = new URL(urlStr);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        urlStr = uri.toASCIIString();
        return urlStr;
    }
    public String getUid()
    {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        return  refreshedToken;
    }

    public void updateData(List<Card> listData) {
        list.clear();
        list.addAll(listData);
        notifyDataSetChanged();
    }

    class MyHoder extends RecyclerView.ViewHolder {
        TextView title,category,lik,content,team,date;
        String url,thumbnail,path;
        ImageView img;
        int flag = 0;
        Long id;
        VideoView cvideo;
        ViewSwitcher switcher;
        int likeCount;
        Map<String, Long> likes = new HashMap<>();
        Button share;
        ImageButton imgdown,fav;
        public MyHoder(View itemView) {
            super(itemView);
            ref = FirebaseDatabase.getInstance().getReference().child("postlikes");
            ref.keepSynced(true);
            switcher = (ViewSwitcher) itemView.findViewById(R.id.switchhh);
            cvideo = (VideoView) itemView.findViewById(R.id.cvideo);
            share = (Button) itemView.findViewById(R.id.cbutton);
            imgdown = (ImageButton) itemView.findViewById(R.id.overflow);
            img = (ImageView) itemView.findViewById(R.id.thumbnail);
            team = (TextView) itemView.findViewById(R.id.cteam);
            title = (TextView) itemView.findViewById(R.id.ctitle);
            date = (TextView) itemView.findViewById(R.id.cdate);
            category= (TextView) itemView.findViewById(R.id.ccategory);
            content= (TextView) itemView.findViewById(R.id.ccontent);
            fav = (ImageButton) itemView.findViewById(R.id.fav);
            lik = (TextView) itemView.findViewById(R.id.liks);
        }
    }

}