//
//  M3U8ExtXKey.m
//  M3U8Kit
//
//  Created by Pierre Perrin on 01/02/2019.
//  Copyright © 2019 M3U8Kit. All rights reserved.
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

#import "M3U8ExtXKey.h"
#import "M3U8TagsAndAttributes.h"

@interface M3U8ExtXKey ()
@property(nonatomic, strong) NSDictionary* dictionary;
@end

@implementation M3U8ExtXKey

- (instancetype)initWithDictionary:(NSDictionary*)dictionary {
  if (self = [super init]) {
    self.dictionary = dictionary;
  }
  return self;
}

- (NSString*)method {
  return self.dictionary[M3U8_EXT_X_KEY_METHOD];
}

- (NSString*)url {
  return self.dictionary[M3U8_EXT_X_KEY_URI];
}

- (NSString*)keyFormat {
  return self.dictionary[M3U8_EXT_X_KEY_KEYFORMAT];
}

- (NSString*)iV {
  return self.dictionary[M3U8_EXT_X_KEY_IV];
}

- (NSString*)description {
  return self.dictionary.description;
}

@end
