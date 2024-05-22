# react-native-video
🎬 `<Video>` component for React Native

This repository is fork of [thewidlarzgroup.github.io/react-native-video/](https://thewidlarzgroup.github.io/react-native-video/).

All modifications are described in `CHANGELOG.md` using version names with `+dolbyxp` suffix.

# Banches

`main-oss` - fork source, synchronized periodically to refrect state of source `master`.

`main-dxp` - modified fork, synchronized periodically to contain oss changes. Main branch of repository. 

## Documentation
documentation is available at [miquido.github.io/react-native-video/](https://miquido.github.io/react-native-video/).

**Warning:** Be aware that it might contain links to original docs.

## Instalation

`yarn add 'git+https://github.com/miquido/react-native-video.git#6.0.0-dolbyxp.1.0`

## Usage

```javascript
// Load the module

import Video, {VideoRef} from 'react-native-video';

// Within your render function, assuming you have a file called
// "background.mp4" in your project. You can include multiple videos
// on a single screen if you like.

const VideoPlayer = () => {
 const videoRef = useRef<VideoRef>(null);
 const background = require('./background.mp4');

 return (
   <Video 
    // Can be a URL or a local file.
    source={background}
    // Store reference  
    ref={videoRef}
    // Callback when remote video is buffering                                      
    onBuffer={onBuffer}
    // Callback when video cannot be loaded              
    onError={onError}               
    style={styles.backgroundVideo}
   />
 )
}

// Later on in your styles..
var styles = StyleSheet.create({
  backgroundVideo: {
    position: 'absolute',
    top: 0,
    left: 0,
    bottom: 0,
    right: 0,
  },
});
```