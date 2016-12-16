
#import "RNPollfish.h"

@implementation RNPollfish


@synthesize bridge = _bridge;


- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

#pragma mark exported methods

// Request the video before we need to display it
RCT_EXPORT_METHOD(initialize :(NSString *)apiKey :(BOOL *)debugMode  :(BOOL *)autoMode)
{
    NSLog(@"initialize Pollfish");
    [Pollfish initAtPosition: PollFishPositionMiddleRight
                 withPadding: 0
             andDeveloperKey: apiKey
               andDebuggable: debugMode
               andCustomMode: autoMode];
}



#pragma mark delgate events


@end
