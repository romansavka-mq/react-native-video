//
//  M3U8MediaPlaylist.h
//  M3U8Kit
//
//  Created by Sun Jin on 3/26/14.
//  Copyright (c) 2014 Jin Sun. All rights reserved.
//
//The MIT License (MIT)
//
//Copyright (c) 2015 Sun Jin <jeansunvf@gmail.com>
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.

#import <Foundation/Foundation.h>
#import "M3U8SegmentInfoList.h"

typedef enum {
    M3U8MediaPlaylistTypeMedia = 0,     // The main media stream playlist.
    M3U8MediaPlaylistTypeSubtitle = 1,  // EXT-X-SUBTITLES TYPE=SUBTITLES
    M3U8MediaPlaylistTypeAudio = 2,     // EXT-X-MEDIA TYPE=AUDIO
    M3U8MediaPlaylistTypeVideo = 3      // EXT-X-MEDIA TYPE=VIDEO
} M3U8MediaPlaylistType;

@interface M3U8MediaPlaylist : NSObject

@property (nonatomic, strong) NSString *name;

@property (readonly, nonatomic, strong) NSString *version;

@property (readonly, nonatomic, copy) NSString *originalText;
@property (readonly, nonatomic, copy) NSURL *baseURL;

@property (readonly, nonatomic, copy) NSURL *originalURL;

@property (readonly, nonatomic, strong) M3U8SegmentInfoList *segmentList;

/** live or replay */
@property (assign, readonly, nonatomic) BOOL isLive;

@property (nonatomic) M3U8MediaPlaylistType type;   // -1 by default

- (instancetype)initWithContent:(NSString *)string type:(M3U8MediaPlaylistType)type baseURL:(NSURL *)baseURL;
- (instancetype)initWithContentOfURL:(NSURL *)URL type:(M3U8MediaPlaylistType)type error:(NSError **)error;

- (NSArray *)allSegmentURLs;

@end
