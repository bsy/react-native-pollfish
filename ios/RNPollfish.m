
#import "RNPollfish.h"

@implementation RNPollfish


@synthesize bridge = _bridge;


- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

#pragma mark exported methods

// Initialize Pollfish
RCT_EXPORT_METHOD(initialize :(NSString *)apiKey :(BOOL *)debugMode  :(BOOL *)autoMode :(NSString *)userId)
{
    NSLog(@"initialize Pollfish");
    [Pollfish initAtPosition: PollFishPositionMiddleRight
                 withPadding: 0
             andDeveloperKey: apiKey
               andDebuggable: debugMode
               andCustomMode: autoMode
               andRequestUUID: userId];
}



#pragma mark delgate events

// WIP

@end
