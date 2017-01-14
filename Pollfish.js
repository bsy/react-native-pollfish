import {
  NativeModules,
  DeviceEventEmitter,
} from 'react-native';

const Pollfish = NativeModules.RNPollfish;

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
      eventHandlers[type].set(handler, DeviceEventEmitter.addListener(type, handler));
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
  DeviceEventEmitter.removeAllListeners('surveyReceived');
  DeviceEventEmitter.removeAllListeners('surveyCompleted');
  DeviceEventEmitter.removeAllListeners('userNotEligible');
  DeviceEventEmitter.removeAllListeners('surveyNotAvailable');
  DeviceEventEmitter.removeAllListeners('surveyOpened');
  DeviceEventEmitter.removeAllListeners('surveyClosed');
};

module.exports = {
  ...Pollfish,
  initialize: (key,  releaseMode, customMode, userId) => Pollfish.initialize(key, releaseMode, customMode, userId),
  show: () => Pollfish.show(),
  hide: () => Pollfish.hide(),
  destroy: () => Pollfish.destroy(),
  surveyAvailable: () => Pollfish.surveyAvailable(),
  addEventListener,
  removeEventListener,
  removeAllListeners
};
