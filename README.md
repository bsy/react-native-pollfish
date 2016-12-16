
# react-native-pollfish

## Getting started

`$ npm install react-native-pollfish --save`

### Mostly automatic installation

`$ react-native link react-native-pollfish`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-pollfish` and add `RNPollfish.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNPollfish.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import co.squaretwo.pollfish.RNPollfishPackage;` to the imports at the top of the file
  - Add `new RNPollfishPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-pollfish'
  	project(':react-native-pollfish').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-pollfish/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-pollfish')
  	```


## Usage
```javascript
import Pollfish from 'react-native-pollfish';

Pollfish.initialize("your api key", true, true)
```
