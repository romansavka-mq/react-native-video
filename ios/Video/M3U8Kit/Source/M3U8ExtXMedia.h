//
//  M3U8ExtXMedia.h
//  M3U8Kit
//
//  Created by Sun Jin on 3/25/14.
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

/*
 
 /// EXT-X-MEDIA
 
 @format    #EXT-X-MEDIA:<attribute-list> ,  attibute-list: ATTR=<value>,...
 @example   #EXT-X-MEDIA:TYPE=AUDIO,GROUP-ID="600k",LANGUAGE="eng",NAME="Audio",AUTOSELECT=YES,DEFAULT=YES,URI="/talks/769/audio/600k.m3u8?sponsor=Ripple",BANDWIDTH=614400
 
#define M3U8_EXT_X_MEDIA                    @"#EXT-X-MEDIA:"
//  EXT-X-MEDIA attributes
#define M3U8_EXT_X_MEDIA_TYPE               @"TYPE" // The value is enumerated-string; valid strings are AUDIO, VIDEO, SUBTITLES and CLOSED-CAPTIONS.
#define M3U8_EXT_X_MEDIA_URI                @"URI"  // The value is a quoted-string containing a URI that identifies the Playlist file.
#define M3U8_EXT_X_MEDIA_GROUP_ID           @"GROUP-ID" // The value is a quoted-string identifying a mutually-exclusive group of renditions.
#define M3U8_EXT_X_MEDIA_LANGUAGE           @"LANGUAGE" // The value is a quoted-string containing an RFC 5646 [RFC5646] language tag that identifies the primary language used in the rendition.
#define M3U8_EXT_X_MEDIA_ASSOC_LANGUAGE     @"ASSOC-LANGUAGE"   // The value is a quoted-string containing an RFC 5646 [RFC5646](http://tools.ietf.org/html/rfc5646) language tag that identifies a language that is associated with the rendition.
#define M3U8_EXT_X_MEDIA_NAME               @"NAME" // The value is a quoted-string containing a human-readable description of the rendition.
#define M3U8_EXT_X_MEDIA_DEFAULT            @"DEFAULT" // The value is an enumerated-string; valid strings are YES and NO.
#define M3U8_EXT_X_MEDIA_AUTOSELECT         @"AUTOSELECT" // The value is an enumerated-string; valid strings are YES and NO.
#define M3U8_EXT_X_MEDIA_FORCED             @"FORCED"   // The value is an enumerated-string; valid strings are YES and NO.
#define M3U8_EXT_X_MEDIA_INSTREAM_ID        @"INSTREAM-ID" // The value is a quoted-string that specifies a rendition within the segments in the Media Playlist.
#define M3U8_EXT_X_MEDIA_CHARACTERISTICS    @"CHARACTERISTICS" // The value is a quoted-string containing one or more Uniform Type Identifiers [UTI] separated by comma (,) characters.
 
 */

@interface M3U8ExtXMedia : NSObject

- (instancetype)initWithDictionary:(NSDictionary *)dictionary;

- (NSString *)type;
- (NSURL *)URI;
- (NSString *)groupId;
- (NSString *)language;
- (NSString *)assocLanguage;
- (NSString *)name;
- (BOOL)isDefault;
- (BOOL)autoSelect;
- (BOOL)forced;
- (NSString *)instreamId;
- (NSString *)characteristics;
- (NSInteger)bandwidth;

- (NSURL *)m3u8URL;   // the absolute url of media playlist file
- (NSString *)m3u8PlainString;

@end
