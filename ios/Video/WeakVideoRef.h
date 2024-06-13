//
//  WeakVideoRef.h
//  react-native-video
//
//  Created by marcin.dziennik on 9/8/23.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@class RCTVideo;
@interface WeakVideoRef : NSObject
@property(weak) RCTVideo* video;
@end

NS_ASSUME_NONNULL_END
