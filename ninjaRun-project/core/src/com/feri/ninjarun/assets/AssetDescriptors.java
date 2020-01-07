package com.feri.ninjarun.assets;


import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {
    /*
    public static final AssetDescriptor<BitmapFont> FONT32 =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT32, BitmapFont.class);

     */

    public static final AssetDescriptor<BitmapFont> FONT32 =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT32, BitmapFont.class);

    public static AssetDescriptor<Skin> GAME_UI =
            new AssetDescriptor<Skin>(AssetPaths.GAME_UISKIN, Skin.class);

    public static final AssetDescriptor<Sound> PICK_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.PICK_SOUND, Sound.class);

    public static final AssetDescriptor<TextureAtlas> UI_ATLAS =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_UISKIN_ATLAS, TextureAtlas.class);


    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    /*

    public static AssetDescriptor<Skin> GAME_UI =
            new AssetDescriptor<Skin>(AssetPaths.GAME_UISKIN, Skin.class);
    public static final AssetDescriptor<Sound> OBSTACLE_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.OBSTACLE_SOUND, Sound.class);
    public static final AssetDescriptor<Sound> PICK_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.PICK_SOUND, Sound.class);
    public static final AssetDescriptor<Music> CAR_START =
            new AssetDescriptor<Music>(AssetPaths.CAR_START_MUSIC, Music.class);
    public static final AssetDescriptor<Music> CAR_STOP =
            new AssetDescriptor<Music>(AssetPaths.CAR_END_MUSIC, Music.class);
    public static final AssetDescriptor<Music> CAR_LOOP =
            new AssetDescriptor<Music>(AssetPaths.CAR_LOOP_MUSIC, Music.class);
     */

    //public static final AssetDescriptor<TiledMap> TILES =
    //        new AssetDescriptor<TiledMap>(AssetPaths.TILES, TiledMap.class);

    /*
    public static final AssetDescriptor<TextureAtlas> UI_ATLAS =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_UISKIN_ATLAS, TextureAtlas.class);
     */

    private AssetDescriptors() {
    }
}
