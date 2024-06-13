//
//  CurrentVideos.m
//  react-native-video
//
//  Created by marcin.dziennik on 9/8/23.
//

#import "CurrentVideos.h"
#import "WeakVideoRef.h"

@interface CurrentVideos ()
@property(strong) NSMutableDictionary<NSNumber*, WeakVideoRef*>* videos;
@end

@implementation CurrentVideos

+ (nonnull instancetype)shared {
  static CurrentVideos* instance = nil;
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    instance = [[self alloc] init];
  });
  return instance;
}

- (instancetype)init {
  if (self = [super init]) {
    _videos = [[NSMutableDictionary alloc] init];
  }

  return self;
}

- (void)add:(nonnull RCTVideo*)video forTag:(nonnull NSNumber*)tag {
  [self cleanup];
  WeakVideoRef* ref = [[WeakVideoRef alloc] init];
  ref.video = video;
  [_videos setObject:ref forKey:tag];
}

- (nullable RCTVideo*)videoForTag:(nonnull NSNumber*)tag {
  [self cleanup];
  return [[_videos objectForKey:tag] video];
}

- (void)cleanup {
  for (NSNumber* key in [_videos allKeys]) {
    if (_videos[key].video == nil) {
      [_videos removeObjectForKey:key];
    }
  }
}

@end
