//
//  NSURL+m3u8.m
//  M3U8Kit
//
//  Created by Frank on 16/06/2017.
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

#import "NSURL+m3u8.h"
#import "M3U8PlaylistModel.h"

@implementation NSURL (m3u8)

- (NSURL *)m3u_realBaseURL {
    NSURL *baseURL = self.baseURL;
    if (!baseURL) {
        NSString *string = [self.absoluteString stringByReplacingOccurrencesOfString:self.lastPathComponent withString:@""];
        
        baseURL = [NSURL URLWithString:string];
    }
    
    return baseURL;
}

- (void)m3u_loadAsyncCompletion:(void (^)(M3U8PlaylistModel *model, NSError *error))completion {
    dispatch_async(dispatch_get_global_queue(QOS_CLASS_USER_INTERACTIVE, 0), ^{
        NSError *err = nil;
        NSString *str = [[NSString alloc] initWithContentsOfURL:self
                                                       encoding:NSUTF8StringEncoding error:&err];
        
        if (err) {
            completion(nil, err);
            return;
        }
        
        M3U8PlaylistModel *listModel = [[M3U8PlaylistModel alloc] initWithString:str
                                                                     originalURL:self baseURL:self.m3u_realBaseURL error:&err];
        if (err) {
            completion(nil, err);
            return;
        }
        
        completion(listModel, nil);
    });
}

@end
