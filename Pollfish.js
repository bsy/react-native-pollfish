import { NativeModules, NativeEventEmitter } from 'react-native';

const RNPollfish = NativeModules.RNPollfish;
const PollfishEventEmitter = new NativeEventEmitter(RNPollfish);

const eventHandlers = {
  surveyReceived: new Map(),
  surveyCompleted: new Map(),
  userNotEligible: new Map(),
  surveyNotAvailable: new Map(),
  surveyOpened: new Map(),
  surveyClosed: new Map()
};

const addEventListener = (type, handler) => {
  switch (type) {
    case 'surveyReceived':
    case 'surveyCompleted':
    case 'userNotEligible':
    case 'surveyNotAvailable':
    case 'surveyOpened':
    case 'surveyClosed':
      eventHandlers[type].set(handler, PollfishEventEmitter.addListener(type, handler));
      break;
    default:
      console.log(`Event with type ${type} does not exist.`);
  }
}

const removeEventListener = (type, handler) => {
  if (!eventHandlers[type].has(handler)) {
    return;
  }
  eventHandlers[type].get(handler).remove();
  eventHandlers[type].delete(handler);
}

const removeAllListeners = () => {
  PollfishEventEmitter.removeAllListeners('surveyReceived');
  PollfishEventEmitter.removeAllListeners('surveyCompleted');
  PollfishEventEmitter.removeAllListeners('userNotEligible');
  PollfishEventEmitter.removeAllListeners('surveyNotAvailable');
  PollfishEventEmitter.removeAllListeners('surveyOpened');
  PollfishEventEmitter.removeAllListeners('surveyClosed');
};

module.exports = {
  ...RNPollfish,
  initialize: (key,  releaseMode, customMode, userId) => RNPollfish.initialize(key, releaseMode, customMode, userId),
  show: () => RNPollfish.show(),
  hide: () => RNPollfish.hide(),
  destroy: () => RNPollfish.destroy(),
  surveyAvailable: () => RNPollfish.surveyAvailable(),
  addEventListener,
  removeEventListener,
  removeAllListeners
};
