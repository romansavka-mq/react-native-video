package com.brentvatne.exoplayer;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.google.android.exoplayer2.DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
import static com.google.android.exoplayer2.DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.DefaultRenderersFactory.ExtensionRendererMode;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;

public class DolbyDefaultExtensionMode {

    private static final String TAG = DolbyDefaultExtensionMode.class.getSimpleName();

    private DolbyDefaultExtensionMode() {
    }

    @ExtensionRendererMode
    public static int getAudioDefaultMode() {
        // check the AC4 decoder on the device
        boolean dlbAC4Device = hasDecoderFor(MimeTypes.AUDIO_AC4);
        if (dlbAC4Device) {
            Log.i(TAG, "This is a Dolby licensed device with Dolby AC-4 and DDP decoders.");
            Log.i(TAG, "Player will bypass Dolby Audio library and use the existing decoders on the device.");
            return EXTENSION_RENDERER_MODE_OFF;
        } else {
            Log.i(TAG, "This is not a Dolby licensed device.");
            Log.i(TAG, "Player should use Dolby Audio library for decoding Dolby content.");
            return EXTENSION_RENDERER_MODE_PREFER;
        }
    }

    @ExtensionRendererMode
    public static int getVideoDefaultMode() {
        // check the DV decoder on the device
        boolean dlbDVDevice = hasDecoderFor(MimeTypes.VIDEO_DOLBY_VISION);
        if (dlbDVDevice) {
            Log.i(TAG, "This is a Dolby licensed device with DV decoder.");
            Log.i(TAG, "Player will bypass Dolby Video library and use the existing decoders on the device.");
            return EXTENSION_RENDERER_MODE_OFF;
        } else {
            Log.i(TAG, "This is not a Dolby licensed device.");
            Log.i(TAG, "Player should use Dolby Video library for decoding Dolby content.");
            return EXTENSION_RENDERER_MODE_PREFER;
        }
    }

    private static boolean hasDecoderFor(String mimeType) {
        MediaCodecInfo[] codecInfos = getCodecsList();
        for (MediaCodecInfo info : codecInfos) {
            if (info.isEncoder()) {
                continue;
            }
            for (String type : info.getSupportedTypes()) {
                if (type.equals(mimeType)) {
                    return true;
                }
            }
        }
        return false;
    }

    @NonNull
    private static MediaCodecInfo[] getCodecsList() {
        MediaCodecList list = null;
        if (SDK_INT >= LOLLIPOP) {
            list = new MediaCodecList(MediaCodecList.ALL_CODECS);
        }
        MediaCodecInfo[] codecInfos = new MediaCodecInfo[0];
        if (SDK_INT >= LOLLIPOP) {
            codecInfos = list.getCodecInfos();
        }
        return codecInfos;
    }
}
