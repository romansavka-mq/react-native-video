//
//  NSArray+m3u8.m
//  M3U8Kit
//
//  Created by Frank on 2022/7/12.
//  Copyright © 2022 M3U8Kit. All rights reserved.
//
// The MIT License (MIT)
//
// Copyright (c) 2015 Sun Jin <jeansunvf@gmail.com>
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

#import "NSArray+m3u8.h"
#import "NSString+m3u8.h"

@implementation NSArray (m3u8)

- (NSMutableDictionary*)m3u_attributesFromAssignment {
  return [self m3u_attributesFromAssignmentByMark:nil];
}

- (NSMutableDictionary*)m3u_attributesFromAssignmentByMark:(NSString*)mark {
  NSMutableDictionary* dict = [NSMutableDictionary dictionary];

  NSString* lastkey = nil;
  for (NSString* keyValue in self) {
    NSRange equalMarkRange = [keyValue rangeOfString:@"="];
    // if equal mark is not found, it means this value is previous value left. eg: CODECS=\"avc1.42c01e,mp4a.40.2\"
    if (equalMarkRange.location == NSNotFound) {
      if (!mark)
        continue;
      if (!lastkey)
        continue;
      NSString* lastValue = dict[lastkey];
      NSString* supplement = [lastValue stringByAppendingFormat:@"%@%@", mark, keyValue.m3u_stringByTrimmingQuoteMark];
      dict[lastkey] = supplement;
      continue;
    }
    NSString* key = [keyValue substringToIndex:equalMarkRange.location].m3u_stringByTrimmingQuoteMark;
    NSString* value = [keyValue substringFromIndex:equalMarkRange.location + 1].m3u_stringByTrimmingQuoteMark;

    dict[key] = value;
    lastkey = key;
  }

  return dict;
}

@end
