package com.brentvatne.exoplayer;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.IntDef;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class DolbyRendersFactory extends DefaultRenderersFactory {

    private static final String TAG = "DolbyRendersFactory";

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            DOLBY_EXTENSION_RENDERER_MODE_AUTO,
            DOLBY_EXTENSION_RENDERER_MODE_OFF,
            DOLBY_EXTENSION_RENDERER_MODE_ON,
            DOLBY_EXTENSION_RENDERER_MODE_PREFER})
    public @interface DolbyExtensionRendererMode {
    }

    public static final int DOLBY_EXTENSION_RENDERER_MODE_AUTO = -1;

    public static final int DOLBY_EXTENSION_RENDERER_MODE_OFF = 0;

    public static final int DOLBY_EXTENSION_RENDERER_MODE_ON = 1;

    public static final int DOLBY_EXTENSION_RENDERER_MODE_PREFER = 2;

    @ExtensionRendererMode
    private int audioExtensionRendererMode;
    @ExtensionRendererMode
    private int videoExtensionRendererMode;

    public DolbyRendersFactory(Context context) {
        super(context);
        audioExtensionRendererMode = EXTENSION_RENDERER_MODE_OFF;
        videoExtensionRendererMode = EXTENSION_RENDERER_MODE_OFF;
    }

    @Override
    public DolbyRendersFactory setExtensionRendererMode(int extensionRendererMode) {
        super.setExtensionRendererMode(extensionRendererMode);
        audioExtensionRendererMode = extensionRendererMode;
        videoExtensionRendererMode = extensionRendererMode;
        return this;
    }

    public DolbyRendersFactory setAudioExtensionRendererMode(@DolbyExtensionRendererMode int dolbyExtensionRendererMode) {
        if (dolbyExtensionRendererMode == DOLBY_EXTENSION_RENDERER_MODE_AUTO) {
            audioExtensionRendererMode = DolbyDefaultExtensionMode.getAudioDefaultMode();
        } else {
            audioExtensionRendererMode = dolbyExtensionRendererMode;
        }
        return this;
    }

    public DolbyRendersFactory setVideoExtensionRendererMode(@DolbyExtensionRendererMode int dolbyExtensionRendererMode) {
        if (dolbyExtensionRendererMode == DOLBY_EXTENSION_RENDERER_MODE_AUTO) {
            videoExtensionRendererMode = DolbyDefaultExtensionMode.getVideoDefaultMode();
        } else {
            videoExtensionRendererMode = dolbyExtensionRendererMode;
        }
        return this;
    }

    @Override
    protected void buildAudioRenderers(
            Context context,
            int extensionRendererMode,
            MediaCodecSelector mediaCodecSelector,
            boolean enableDecoderFallback,
            AudioSink audioSink,
            Handler eventHandler,
            AudioRendererEventListener eventListener,
            ArrayList<Renderer> out) {
        super.buildAudioRenderers(context, audioExtensionRendererMode, mediaCodecSelector,
                enableDecoderFallback, audioSink, eventHandler, eventListener, out);

        if (audioExtensionRendererMode == EXTENSION_RENDERER_MODE_OFF) {
            return;
        }
        int extensionRendererIndex = out.size();
        if (audioExtensionRendererMode == EXTENSION_RENDERER_MODE_PREFER) {
            extensionRendererIndex--;
        }

        try {
            // Full class names used for constructor args so the LINT rule triggers if any of them move.
            // LINT.IfChange
            Class<?> clazz = Class.forName("com.dolby.daa.LibDaaAudioRenderer");
            Constructor<?> constructor =
                    clazz.getConstructor(
                            android.os.Handler.class,
                            com.google.android.exoplayer2.audio.AudioRendererEventListener.class,
                            com.google.android.exoplayer2.audio.AudioSink.class);
            // LINT.ThenChange(../../../../../../../proguard-rules.txt)
            Renderer renderer =
                    (Renderer) constructor.newInstance(eventHandler, eventListener, audioSink);
            out.add(extensionRendererIndex, renderer);
            Log.i(TAG, "Loaded LibDaaAudioRenderer.");
        } catch (ClassNotFoundException e) {
            // Expected if the app was built without the extension.
            Log.e(TAG, "Unable to load LibDaaAudioRenderer.", e);
        } catch (Exception e) {
            Log.e(TAG, "Unable to load LibDaaAudioRenderer.", e);
            // The extension is present, but instantiation failed.
            throw new RuntimeException("Error instantiating DAA extension", e);
        }
    }

    @Override
    protected void buildVideoRenderers(
            Context context,
            int extensionRendererMode,
            MediaCodecSelector mediaCodecSelector,
            boolean enableDecoderFallback,
            Handler eventHandler,
            VideoRendererEventListener eventListener,
            long allowedVideoJoiningTimeMs,
            ArrayList<Renderer> out) {
        super.buildVideoRenderers(context, videoExtensionRendererMode, mediaCodecSelector,
                enableDecoderFallback, eventHandler, eventListener, allowedVideoJoiningTimeMs, out);

        if (videoExtensionRendererMode == EXTENSION_RENDERER_MODE_OFF) {
            return;
        }
        int extensionRendererIndex = out.size();
        if (videoExtensionRendererMode == EXTENSION_RENDERER_MODE_PREFER) {
            extensionRendererIndex--;
        }

        try {
            // Full class names used for constructor args so the LINT rule triggers if any of them move.
            // LINT.IfChange
            Class<?> clazz = Class.forName("com.dolby.dovi.MediaCodecDoviRenderer");
            Constructor<?> constructor =
                    clazz.getConstructor(
                            android.content.Context.class,
                            com.google.android.exoplayer2.mediacodec.MediaCodecSelector.class,
                            long.class,
                            boolean.class,
                            android.os.Handler.class,
                            com.google.android.exoplayer2.video.VideoRendererEventListener.class,
                            int.class);
            // LINT.ThenChange(../../../../../../../proguard-rules.txt)
            Renderer renderer =
                    (Renderer)
                            constructor.newInstance(
                                    context,
                                    MediaCodecSelector.DEFAULT,
                                    allowedVideoJoiningTimeMs,
                                    false,
                                    eventHandler,
                                    eventListener,
                                    MAX_DROPPED_VIDEO_FRAME_COUNT_TO_NOTIFY);
            out.add(extensionRendererIndex, renderer);
            Log.i(TAG, "Loaded MediaCodecDoviRenderer.");
        } catch (ClassNotFoundException e) {
            // Expected if the app was built without the extension.
            Log.e(TAG, "Unable to load MediaCodecDoviRenderer.", e);
        } catch (Exception e) {
            // The extension is present, but instantiation failed.
            throw new RuntimeException("Error instantiating DOVI extension", e);
        }
    }
}

