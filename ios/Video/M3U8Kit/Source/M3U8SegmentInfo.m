//
//  M3U8SegmentInfo.m
//  M3U8Kit
//
//  Created by Oneday on 13-1-11.
//  Copyright (c) 2013年 0day. All rights reserved.
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

#import "M3U8SegmentInfo.h"
#import "M3U8TagsAndAttributes.h"
#import "M3U8ExtXKey.h"
#import "M3U8ExtXByteRange.h"

@interface M3U8SegmentInfo()
@property (nonatomic, strong) NSDictionary *dictionary;

@end

@implementation M3U8SegmentInfo

@synthesize xKey = _xKey;

- (instancetype)init {
    return [self initWithDictionary:nil xKey:nil];
}

- (instancetype)initWithDictionary:(NSDictionary *)dictionary {
    return [self initWithDictionary:dictionary xKey:nil];
}

- (instancetype)initWithDictionary:(NSDictionary *)dictionary xKey:(M3U8ExtXKey *)key {
    return [self initWithDictionary:dictionary xKey:key byteRange:nil];
}

- (instancetype)initWithDictionary:(NSDictionary *)dictionary xKey:(M3U8ExtXKey *)key byteRange:(M3U8ExtXByteRange *)byteRange{
    if (self = [super init]) {
        _dictionary = dictionary;
        _xKey = key;
        _byteRange = byteRange;
    }
    return self;
}

- (NSURL *)baseURL {
    return self.dictionary[M3U8_BASE_URL];
}

- (NSURL *)URL {
    return self.dictionary[M3U8_URL];
}

- (NSURL *)mediaURL {
    if (self.URI.scheme) {
        return self.URI;
    }
    
    return [NSURL URLWithString:self.URI.absoluteString relativeToURL:[self baseURL]];
}

- (NSTimeInterval)duration {
    return [self.dictionary[M3U8_EXTINF_DURATION] doubleValue];
}

- (NSString *)title {
    return self.dictionary[M3U8_EXTINF_TITLE];
}

- (NSURL *)URI {
    NSString *originalUrl = self.dictionary[M3U8_EXTINF_URI];
    NSString *urlString = [originalUrl stringByReplacingOccurrencesOfString:@" " withString:@"%20"];
    return [NSURL URLWithString:urlString];
}

- (NSDictionary<NSString *,NSString *> *)additionalParameters {
    return self.dictionary[M3U8_EXTINF_ADDITIONAL_PARAMETERS];
}

- (NSString *)description {
    NSMutableDictionary *dict = [self.dictionary mutableCopy];
    [dict addEntriesFromDictionary:[self.xKey valueForKey:@"dictionary"]];
    return dict.description;
}

@end
