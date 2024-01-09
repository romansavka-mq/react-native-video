//
//  M3U8ExtXMedia.m
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

#import "M3U8ExtXMedia.h"
#import "M3U8TagsAndAttributes.h"

@interface M3U8ExtXMedia()
@property (nonatomic, strong) NSDictionary *dictionary;
@end

@implementation M3U8ExtXMedia

- (instancetype)initWithDictionary:(NSDictionary *)dictionary {
    if (self = [super init]) {
        self.dictionary = dictionary;
    }
    return self;
}

- (NSURL *)baseURL {
    return self.dictionary[M3U8_BASE_URL];
}

- (NSURL *)URL {
    return self.dictionary[M3U8_URL];
}

- (NSString *)type {
    return self.dictionary[M3U8_EXT_X_MEDIA_TYPE];
}

- (NSURL *)URI {
    NSString *originalUrl = self.dictionary[M3U8_EXT_X_MEDIA_URI];
    NSString *urlString = [originalUrl stringByReplacingOccurrencesOfString:@" " withString:@"%20"];
    return [NSURL URLWithString:urlString];
}

- (NSString *)groupId {
    return self.dictionary[M3U8_EXT_X_MEDIA_GROUP_ID];
}

- (NSString *)channels {
    return self.dictionary[M3U8_EXT_X_MEDIA_CHANNELS];
}

- (NSString *)language {
    return [self.dictionary[M3U8_EXT_X_MEDIA_LANGUAGE] lowercaseString];
}

- (NSString *)assocLanguage {
    return self.dictionary[M3U8_EXT_X_MEDIA_ASSOC_LANGUAGE];
}

- (NSString *)name {
    return self.dictionary[M3U8_EXT_X_MEDIA_NAME];
}

- (BOOL)isDefault {
    return [self.dictionary[M3U8_EXT_X_MEDIA_DEFAULT] boolValue];
}

- (BOOL)autoSelect {
    return [self.dictionary[M3U8_EXT_X_MEDIA_AUTOSELECT] boolValue];
}

- (BOOL)forced {
    return [self.dictionary[M3U8_EXT_X_MEDIA_FORCED] boolValue];
}

- (NSString *)instreamId {
    return self.dictionary[M3U8_EXT_X_MEDIA_INSTREAM_ID];
}

- (NSString *)characteristics {
    return self.dictionary[M3U8_EXT_X_MEDIA_CHARACTERISTICS];
}

- (NSInteger)bandwidth {
    return [self.dictionary[M3U8_EXT_X_MEDIA_BANDWIDTH] integerValue];
}

- (NSURL *)m3u8URL {
    if (self.URI.scheme) {
        return self.URI;
    }

    NSString *originalUrl = self.URI.absoluteString;
    NSString *urlString = [originalUrl stringByReplacingOccurrencesOfString:@" " withString:@"%20"];
    return [NSURL URLWithString:urlString relativeToURL:[self baseURL]];
}

- (NSString *)description {
    return [NSString stringWithString:self.dictionary.description];
}

/*
 #EXT-X-MEDIA:TYPE=AUDIO,GROUP-ID="600k",LANGUAGE="eng",NAME="Audio",AUTOSELECT=YES,DEFAULT=YES,URI="main_media_7.m3u8",BANDWIDTH=614400
 */
- (NSString *)m3u8PlainString {
    NSMutableString *str = [NSMutableString string];
    [str appendString:M3U8_EXT_X_MEDIA];
    [str appendString:[NSString stringWithFormat:@"TYPE=%@", self.type]];
    [str appendString:[NSString stringWithFormat:@",GROUP-ID=\"%@\"", self.groupId]];
    [str appendString:[NSString stringWithFormat:@",LANGUAGE=\"%@\"", self.language]];
    [str appendString:[NSString stringWithFormat:@",NAME=\"%@\"", self.name]];
    [str appendString:[NSString stringWithFormat:@",AUTOSELECT=%@", self.autoSelect?@"YES":@"NO"]];
    [str appendString:[NSString stringWithFormat:@",DEFAULT=%@", self.isDefault?@"YES":@"NO"]];
    
    NSString *fStr = self.dictionary[M3U8_EXT_X_MEDIA_FORCED];
    if (fStr.length > 0) {
        [str appendString:[NSString stringWithFormat:@",FORCED=%@", fStr]];
    }
    
    [str appendString:[NSString stringWithFormat:@",URI=\"%@\"", self.URI.absoluteString]];
    
    NSString *bStr = self.dictionary[M3U8_EXT_X_MEDIA_BANDWIDTH];
    if (bStr.length > 0) {
        [str appendString:[NSString stringWithFormat:@",BANDWIDTH=%@", bStr]];
    }
    
    return str;
}

@end








