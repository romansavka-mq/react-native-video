//
//  M3U8ExtXMediaList.h
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
#import "M3U8ExtXMedia.h"

@interface M3U8ExtXMediaList : NSObject

@property (nonatomic, assign ,readonly) NSUInteger count;

- (void)addExtXMedia:(M3U8ExtXMedia *)extXMedia;
- (M3U8ExtXMedia *)xMediaAtIndex:(NSUInteger)index;
- (M3U8ExtXMedia *)firstExtXMedia;
- (M3U8ExtXMedia *)lastExtXMedia;

- (M3U8ExtXMediaList *)audioList;
- (M3U8ExtXMedia *)suitableAudio;

- (M3U8ExtXMediaList *)videoList;
- (M3U8ExtXMedia *)suitableVideo;

- (M3U8ExtXMediaList *)subtitleList;
- (M3U8ExtXMedia *)suitableSubtitle;

@end
