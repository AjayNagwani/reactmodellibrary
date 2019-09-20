
# react-native-model-library

## Getting started

`$ npm install react-native-model-library --save`

### Mostly automatic installation

`$ react-native link react-native-model-library`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-model-library` and add `RNModelLibrary.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNModelLibrary.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNModelLibraryPackage;` to the imports at the top of the file
  - Add `new RNModelLibraryPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-model-library'
  	project(':react-native-model-library').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-model-library/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-model-library')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNModelLibrary.sln` in `node_modules/react-native-model-library/windows/RNModelLibrary.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Model.Library.RNModelLibrary;` to the usings at the top of the file
  - Add `new RNModelLibraryPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNModelLibrary from 'react-native-model-library';

// TODO: What to do with the module?
RNModelLibrary;
```
  