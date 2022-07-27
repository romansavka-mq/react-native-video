package com.brentvatne.exoplayer;

import android.content.Context;
import android.os.Handler;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class DolbyRendersFactory extends DefaultRenderersFactory {

    private static final String TAG = "DolbyRendersFactory";

    public DolbyRendersFactory(Context context) {
        super(context);
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
        super.buildAudioRenderers(context, extensionRendererMode, mediaCodecSelector,
                enableDecoderFallback, audioSink, eventHandler, eventListener, out);

        if (extensionRendererMode == EXTENSION_RENDERER_MODE_OFF) {
            return;
        }
        int extensionRendererIndex = out.size();
        if (extensionRendererMode == EXTENSION_RENDERER_MODE_PREFER) {
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
        super.buildVideoRenderers(context, extensionRendererMode, mediaCodecSelector,
                enableDecoderFallback, eventHandler, eventListener, allowedVideoJoiningTimeMs, out);

        if (extensionRendererMode == EXTENSION_RENDERER_MODE_OFF) {
            return;
        }
        int extensionRendererIndex = out.size();
        if (extensionRendererMode == EXTENSION_RENDERER_MODE_PREFER) {
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

