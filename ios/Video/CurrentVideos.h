//
//  CurrentVideos.h
//  react-native-video
//
//  Created by marcin.dziennik on 9/8/23.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@class RCTVideo;
@interface CurrentVideos : NSObject
+ (instancetype)shared;
- (void)add:(RCTVideo*)video forTag:(NSNumber*)tag;
- (nullable RCTVideo*)videoForTag:(NSNumber*)tag;
@end

NS_ASSUME_NONNULL_END
