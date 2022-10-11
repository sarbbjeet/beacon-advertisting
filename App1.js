/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {useEffect, useState} from 'react';
import notifee from '@notifee/react-native';

import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
  NativeModules,
  DeviceEventEmitter,
  AppRegistry,
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

const {CalendarModule} = NativeModules;
/* $FlowFixMe[missing-local-annot] The type annotation(s) required by Flow's
 * LTI update could not be added via codemod */

const App = () => {
  const [headless, setHeadless] = useState(false);
  const isDarkMode = useColorScheme() === 'dark';
  CalendarModule.createCalendarEvent('university', 'near Tenny', (err, res) => {
    if (err) {
      console.log(res);
      console.error(err);
    } else {
      console.log(res);
    }
  });
  const broadcastReceiver = async data => {
    console.log('data is receiver', data);
  };
  useEffect(() => {
    // DeviceEventEmitter.addListener('eventA', response => {
    //   console.log('event response', response);
    // });
    if (!headless) {
      setHeadless(true);
      AppRegistry.registerHeadlessTask(
        'broadcastEvent',
        () => broadcastReceiver,
      );
    }
  }, []);
  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <Header />
        <View></View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
});

export default App;
